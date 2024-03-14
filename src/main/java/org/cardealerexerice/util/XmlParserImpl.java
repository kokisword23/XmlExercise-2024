package org.cardealerexerice.util;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import org.springframework.stereotype.Component;

import java.io.File;

import static jakarta.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT;

@Component
public class XmlParserImpl implements XmlParser{
    @Override
    public <E> E parse(Class<E> clazz, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);

        Unmarshaller unmarshaller = context.createUnmarshaller();

        E object = (E) unmarshaller.unmarshal(new File(path));
        return object;
    }

    @Override
    public <E> void exportToFile(Class<E> clazz, E object, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(clazz);

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(object, new File(path));
    }

    @Override
    public <E> void exportToFile(E object, String path) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(object.getClass());

        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(object, new File(path));
    }
}
