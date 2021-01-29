package uk.gov.justice.digital.hmpps.crimeportalgateway;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionInterceptorTest {

    private static final String SOAP_ACTION = "externalDocument";
    @Mock
    private SoapMessage soapMessage;

    @Mock
    private MessageContext messageContext;

    private final ActionInterceptor actionInterceptor = new ActionInterceptor(SOAP_ACTION);

    @Test
    void whenHandleRequest_thenAddSoapAction() {
        when(messageContext.getRequest()).thenReturn(soapMessage);

        assertThat(actionInterceptor.handleRequest(messageContext)).isTrue();

        verify(messageContext).getRequest();
        verify(soapMessage).setSoapAction(SOAP_ACTION);
        verifyNoMoreInteractions(messageContext, soapMessage);
    }

    @Test
    void whenHandleResponse_thenReturnTrue() {
        assertThat(actionInterceptor.handleResponse(messageContext)).isTrue();
    }

    @Test
    void whenHandleFault_thenReturnTrue() {
        assertThat(actionInterceptor.handleFault(messageContext)).isTrue();
    }

}
