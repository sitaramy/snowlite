package com.sapient.snowlite.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.sapient.snowlite.userpref.meta.TaskList;

public class Util {

	public static TaskList readTaskList(InputStream in) {
		TaskList taskList = null;

		try {

			JAXBContext jc = JAXBContext.newInstance("com.sapient.snowlite.userpref.meta");
			Unmarshaller jaxbUnmarshaller = jc.createUnmarshaller();
			Source source = new StreamSource(in);
			JAXBElement<TaskList> o = jaxbUnmarshaller.unmarshal(source, TaskList.class);
			taskList = o.getValue();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return taskList;
	}
	
	public static void saveTaskList(TaskList.Task task) {
		InputStream in = null;
		OutputStream outStream = null;
		
		try {
			in = new FileInputStream(new File("src/main/resources/userPreferences.xml"));
			TaskList tList = readTaskList(in);
			tList.getTask().add(task);
			
			JAXBContext jc = JAXBContext.newInstance(TaskList.class);
			Marshaller jaxbMarshaller = jc.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			
			File targetFile = new File("src/main/resources/userPreferences.xml");
			outStream = new FileOutputStream(targetFile);
			jaxbMarshaller.marshal(tList, outStream);
		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try {
				if (outStream != null) {
					outStream.flush();
					outStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static String getOptionName(String url) {
		for(UrlMapper urlmap : UrlMapper.values()) {
			if(urlmap.url.equals(url)) {
				return urlmap.option; 
			}
		}
		
		return "";
	}
	
	public static XMLGregorianCalendar dateToXml(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = dateFormat.format(date);
		try {
			XMLGregorianCalendar xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(strDate);
			return xmlDate;
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}
}
