package uk.gov.justice.digital.hmpps.crimeportalgateway;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.gov.justice.magistrates.external.externaldocumentrequest.AckType;

@SpringBootApplication
public class CrimePortalGatewayClientApplication implements CommandLineRunner {

    @Autowired
    private CrimePortalGatewayClient crimePortalGatewayClient;

    @Autowired
    private MessageParser messageParser;

    private static final Logger LOG = LoggerFactory.getLogger(CrimePortalGatewayClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrimePortalGatewayClientApplication.class, args);
    }

    @Override
    public void run(String... args) {

        messageParser.loadFromFile("src/main/resources/requests/external-document-request.xml")
            .map(externalDocumentRequest -> crimePortalGatewayClient.getAck(externalDocumentRequest))
            .ifPresentOrElse(ack -> LOG.info("Received ack {} with comment {}",
                ack, Optional.ofNullable(ack.getAckType()).map(AckType::getMessageComment).orElse("[not set]"))
                , () -> LOG.error("No ack received"));
    }
}
