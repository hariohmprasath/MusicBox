package com.personal.music.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by hrajagopal on 5/15/15.
 */
public class JAXBUtils {
    public static String convertObjectToString(Object object, Class jaxBClass) {
        if (object != null && jaxBClass != null) {
            try{
                StringWriter writer = new StringWriter();
                JAXBContext jaxbContext = JAXBContext.newInstance(jaxBClass);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

                // output pretty printed
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                jaxbMarshaller.marshal(object, writer);
                return writer.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Object convertStringToObject(String xmlString, Class jaxBClass){
        if(xmlString!=null && jaxBClass!=null){
            try{
                JAXBContext jaxbContext = JAXBContext.newInstance(jaxBClass);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

                StringReader reader = new StringReader(xmlString);
                return unmarshaller.unmarshal(reader);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
