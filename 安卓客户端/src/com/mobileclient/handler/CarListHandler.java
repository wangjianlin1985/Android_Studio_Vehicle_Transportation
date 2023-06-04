package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Car;
public class CarListHandler extends DefaultHandler {
	private List<Car> carList = null;
	private Car car;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (car != null) { 
            String valueString = new String(ch, start, length); 
            if ("carId".equals(tempString)) 
            	car.setCarId(new Integer(valueString).intValue());
            else if ("carNo".equals(tempString)) 
            	car.setCarNo(valueString); 
            else if ("carModel".equals(tempString)) 
            	car.setCarModel(new Integer(valueString).intValue());
            else if ("pinpai".equals(tempString)) 
            	car.setPinpai(valueString); 
            else if ("youxing".equals(tempString)) 
            	car.setYouxing(valueString); 
            else if ("haoyouliang".equals(tempString)) 
            	car.setHaoyouliang(valueString); 
            else if ("chexian".equals(tempString)) 
            	car.setChexian(valueString); 
            else if ("zonglicheng".equals(tempString)) 
            	car.setZonglicheng(valueString); 
            else if ("wxcs".equals(tempString)) 
            	car.setWxcs(valueString); 
            else if ("carMemo".equals(tempString)) 
            	car.setCarMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Car".equals(localName)&&car!=null){
			carList.add(car);
			car = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		carList = new ArrayList<Car>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Car".equals(localName)) {
            car = new Car(); 
        }
        tempString = localName; 
	}

	public List<Car> getCarList() {
		return this.carList;
	}
}
