package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.CarModel;
public class CarModelListHandler extends DefaultHandler {
	private List<CarModel> carModelList = null;
	private CarModel carModel;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (carModel != null) { 
            String valueString = new String(ch, start, length); 
            if ("modelId".equals(tempString)) 
            	carModel.setModelId(new Integer(valueString).intValue());
            else if ("modelName".equals(tempString)) 
            	carModel.setModelName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("CarModel".equals(localName)&&carModel!=null){
			carModelList.add(carModel);
			carModel = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		carModelList = new ArrayList<CarModel>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("CarModel".equals(localName)) {
            carModel = new CarModel(); 
        }
        tempString = localName; 
	}

	public List<CarModel> getCarModelList() {
		return this.carModelList;
	}
}
