package com.interblocks.iwallet.notifyr.core.jaxb;

import com.interblocks.iwallet.notifyr.config.props.JAXBProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.xml.transform.StringSource;

import javax.xml.bind.*;
import java.io.StringWriter;

@Slf4j
@Component
public class JAXBParser {
    private final JAXBProperties jaxbProperties;
    private JAXBContext jaxbContext;

    @Autowired
    public JAXBParser(JAXBProperties jaxbProperties) throws JAXBException {
        this.jaxbProperties = jaxbProperties;
        this.jaxbContext = JAXBContext.newInstance(this.jaxbProperties.getClasses());
    }

    /**
     * Generate an object from XML String using JAXB Unmarshaller
     *
     * @param xmlString string input
     * @param tClass    initializing class
     * @param <T>       Object to create a new object from
     * @return Object instance.
     */
    public <T> T unmarshal(String xmlString, Class<T> tClass) {
        log.info("Unmarshalling XML to Class: {}", tClass.getName());
        log.debug("Unmarshalling XML String: \n{} to Class: {}", xmlString, tClass.getName());
        try {
            Unmarshaller unmarshaller = this.jaxbContext.createUnmarshaller();
            JAXBElement<T> jaxbElement = unmarshaller.unmarshal(new StringSource(xmlString), tClass);
            return jaxbElement.getValue();
        } catch (Exception e) {
            log.error("Exception on unmarshalling: {}", e.getMessage());
            throw new IllegalStateException(e);
        }
    }

    /**
     * Creates an Object instance from XML String
     *
     * @param objectInstance Object instance
     * @return XML string from object
     */
    public String marshall(Object objectInstance) {
        log.info("Marshalling Object instance: {}", objectInstance.getClass().getName());
        try {

            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(objectInstance, stringWriter);

            String xmlOutput = stringWriter.toString();

            log.info("Marshalled object");
            log.debug("Marshalled object to XML String: \n{}", xmlOutput);

            return xmlOutput;

        } catch (JAXBException e) {
            throw new IllegalStateException(e);
        }
    }
}
