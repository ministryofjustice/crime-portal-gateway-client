package uk.gov.justice.digital.hmpps.crimeportalgateway;

import org.springframework.ws.client.core.WebServiceTemplate;
import uk.gov.justice.magistrates.external.externaldocumentrequest.Acknowledgement;
import uk.gov.justice.magistrates.external.externaldocumentrequest.ExternalDocumentRequest;

public class CrimePortalGatewayClient {

    private final WebServiceTemplate webServiceTemplate;

    public CrimePortalGatewayClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public Acknowledgement getAck(ExternalDocumentRequest request){
        return (Acknowledgement) webServiceTemplate.marshalSendAndReceive(request);
    }

}
