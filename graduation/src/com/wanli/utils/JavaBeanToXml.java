package com.wanli.utils;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 * JavaBean转xml
 * @author wanli
 *
 */
public class JavaBeanToXml {

	/**
     * java对象转换为xml文件
     * @param xmlPath: xml文件路径
     * @param load:    java对象.Class
     * @return:        xml文件的String
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
