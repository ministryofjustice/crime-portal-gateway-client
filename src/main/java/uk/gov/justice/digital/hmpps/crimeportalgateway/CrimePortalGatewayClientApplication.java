package uk.gov.justice.digital.hmpps.crimeportalgateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uk.gov.justice.magistrates.external.externaldocumentrequest.Acknowledgement;
import uk.gov.justice.magistrates.external.externaldocumentrequest.Document;
import uk.gov.justice.magistrates.external.externaldocumentrequest.Documents;
import uk.gov.justice.magistrates.external.externaldocumentrequest.ExternalDocumentRequest;

@SpringBootApplication
public class CrimePortalGatewayClientApplication implements CommandLineRunner {

    @Autowired
    private CrimePortalGatewayClient crimePortalGatewayClient;

    private static final Logger LOG = LoggerFactory.getLogger(CrimePortalGatewayClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(CrimePortalGatewayClientApplication.class, args);
    }

    @Override
    public void run(String... args) {
        ExternalDocumentRequest request = new ExternalDocumentRequest();
        Documents documents = new Documents();
        documents.setJobNumber("1");
        documents.getDocument().add(0, new Document());
        request.setDocuments(documents);

        Acknowledgement ack = crimePortalGatewayClient.getAck(request);
        LOG.info("For Job number {}, got message status {}", "1", ack.getAckType().getMessageStatus());
    }
}
