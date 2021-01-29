package uk.gov.justice.digital.hmpps.crimeportalgateway;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapMessage;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class ActionInterceptor implements ClientInterceptor {

    @Value("${soap.action}")
    private String soapAction;

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        final SoapMessage soapMessage = (SoapMessage) messageContext.getRequest();
        soapMessage.setSoapAction(soapAction);
        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex) throws WebServiceClientException {

    }
}
