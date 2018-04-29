package com.wanli.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * JavaBeanתxml
 * @author wanli
 *
 */
public class JavaBeanToXml {

	/**
     * java����ת��Ϊxml�ļ�
     * @param xmlPath: xml�ļ�·��
     * @param load:    java����.Class
     * @return:        xml�ļ���String
     * @throws JAXBException    
     */
    public static String beanToXml(Object obj,Class<?> load) {
        JAXBContext context = null;
        StringWriter writer = null;
		try {
			context = JAXBContext.newInstance(load);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "GBK");
			writer = new StringWriter();
			marshaller.marshal(obj,writer);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
        return writer.toString();
    }
	
}
