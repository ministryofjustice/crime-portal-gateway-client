package uk.gov.justice.digital.hmpps.crimeportalgateway;

import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.w3c.dom.Element;
import uk.gov.justice.magistrates.external.externaldocumentrequest.ExternalDocumentRequest;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import(TestConfiguration.class)
class MessageParserTest {

    @Autowired
    private MessageParser messageParser;

    @Test
    void whenParseFile_thenReturnExternalDocumentRequest() {
        Optional<ExternalDocumentRequest> optionalRequest = messageParser.loadFromFile("src/main/resources/requests/external-document-request.xml");

        assertThat(optionalRequest.isPresent()).isTrue();
        ExternalDocumentRequest externalDocumentRequest = optionalRequest.get();
        Element documentsElement = externalDocumentRequest.getDocuments().getAny();
        assertThat(documentsElement.getElementsByTagName("document")).isNotNull();
    }

    @Test
    void whenParseInvalidFile_thenReturnEmptyOptional() {
        Optional<ExternalDocumentRequest> optionalRequest = messageParser.loadFromFile("src/test/resources/requests/external-document-request-invalid.xml");

        assertThat(optionalRequest.isEmpty()).isTrue();
    }


    @TestConfiguration
    static class MyTestConfig {
        @Bean
        public MessageParser messageParser() throws JAXBException {
            return new MessageParser(JAXBContext.newInstance(ExternalDocumentRequest.class));
        }
    }

}
