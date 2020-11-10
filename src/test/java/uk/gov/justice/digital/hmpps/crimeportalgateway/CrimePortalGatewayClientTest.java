package uk.gov.justice.digital.hmpps.crimeportalgateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ws.client.core.WebServiceTemplate;
import uk.gov.justice.magistrates.external.externaldocumentrequest.Acknowledgement;
import uk.gov.justice.magistrates.external.externaldocumentrequest.ExternalDocumentRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CrimePortalGatewayClientTest {

    @Mock
    private WebServiceTemplate webServiceTemplate;

    @InjectMocks
    private CrimePortalGatewayClient crimeClient;

    @Test
    void testGetAcknowledgement() {

        ExternalDocumentRequest request = new ExternalDocumentRequest();
        Acknowledgement ack = new Acknowledgement();
        when(webServiceTemplate.marshalSendAndReceive(request)).thenReturn(ack);

        Acknowledgement acknowledgement = crimeClient.getAck(request);

        assertThat(acknowledgement).isSameAs(ack);
    }

}
