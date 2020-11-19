package uk.gov.justice.digital.hmpps.crimeportalgateway;

import java.io.IOException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.support.CryptoFactoryBean;
import uk.gov.justice.magistrates.external.externaldocumentrequest.AckType;
import uk.gov.justice.magistrates.external.externaldocumentrequest.Acknowledgement;
import uk.gov.justice.magistrates.external.externaldocumentrequest.ExternalDocumentRequest;

@Configuration
public class SoapCrimeClientConfig {

    @Value("${soap.default-uri}")
    private String defaultUri;

    @Value("${keystore-password}")
    private String keystorePassword;

    @Value("${trusted-cert-alias-name}")
    private String trustedCertAliasName;

    @Value("${private-key-alias-name}")
    private String privateKeyAliasName;

    @Value("${ws-sec.actions}")
    private String actions;

    @Value("${ws-sec.request-encryption-parts}")
    private String requestEncryptionParts;

    @Bean
    public MessageFactory messageFactory() throws SOAPException {
        return MessageFactory.newInstance(SOAPConstants.SOAP_1_2_PROTOCOL);
    }

    @Bean
    public WebServiceMessageFactory webServiceMessageFactory(@Autowired MessageFactory messageFactory) {
        return new SaajSoapMessageFactory(messageFactory);
    }


    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() throws Exception {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();

        // set security actions
        securityInterceptor.setSecurementActions(actions);

        // sign the request
        securityInterceptor.setSecurementUsername(privateKeyAliasName);
        securityInterceptor.setSecurementPassword(keystorePassword);
        securityInterceptor.setSecurementSignatureCrypto(getCryptoFactoryBean().getObject());

        // encrypt the request
        securityInterceptor.setSecurementEncryptionUser(trustedCertAliasName);
        securityInterceptor.setSecurementEncryptionCrypto(getCryptoFactoryBean().getObject());
        securityInterceptor.setSecurementEncryptionParts(requestEncryptionParts);

        return securityInterceptor;
    }

    @Bean
    public CryptoFactoryBean getCryptoFactoryBean() throws IOException {
        CryptoFactoryBean cryptoFactoryBean = new CryptoFactoryBean();
        cryptoFactoryBean.setKeyStorePassword(keystorePassword);
        cryptoFactoryBean.setKeyStoreLocation(new ClassPathResource("client.jks"));
        return cryptoFactoryBean;
    }

    @Bean
    public Jaxb2Marshaller getMarshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setPackagesToScan("uk.gov.justice.magistrates.external.externaldocumentrequest");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(@Autowired WebServiceMessageFactory webServiceMessageFactory,
                                                @Autowired Jaxb2Marshaller jaxb2Marshaller) throws Exception {
        WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
        webServiceTemplate.setMessageFactory(webServiceMessageFactory);
        webServiceTemplate.setMarshaller(jaxb2Marshaller);
        webServiceTemplate.setUnmarshaller(jaxb2Marshaller);
        webServiceTemplate.setDefaultUri(defaultUri);
        webServiceTemplate.setInterceptors(new ClientInterceptor[]{securityInterceptor()});
        return webServiceTemplate;
    }

    @Bean
    public CrimePortalGatewayClient crimePortalGatewayClient(@Autowired WebServiceTemplate webServiceTemplate) {
        return new CrimePortalGatewayClient(webServiceTemplate);
    }

}
