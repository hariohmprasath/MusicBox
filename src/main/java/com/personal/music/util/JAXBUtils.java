package com.personal.music.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
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
}
