package uk.gov.justice.digital.hmpps.crimeportalgateway;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.justice.magistrates.external.externaldocumentrequest.ExternalDocumentRequest;

@Component
public class MessageParser {

    private final JAXBContext jaxbContext;

    public MessageParser(@Autowired JAXBContext jaxbContext) {
        super();
        this.jaxbContext = jaxbContext;
    }

    public Optional<ExternalDocumentRequest> loadFromFile(final String filePath) {

        try {
            final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            final String string = Files.readString(Paths.get(filePath));
            return Optional.ofNullable((ExternalDocumentRequest) unmarshaller.unmarshal(new StringReader(string)));
        }
        catch (JAXBException | IOException ex) {
            return Optional.empty();
        }
    }
}
