package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.Yunshu;
public class YunshuListHandler extends DefaultHandler {
	private List<Yunshu> yunshuList = null;
	private Yunshu yunshu;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (yunshu != null) { 
            String valueString = new String(ch, start, length); 
            if ("yunshuId".equals(tempString)) 
            	yunshu.setYunshuId(new Integer(valueString).intValue());
            else if ("userObj".equals(tempString)) 
            	yunshu.setUserObj(valueString); 
            else if ("carObj".equals(tempString)) 
            	yunshu.setCarObj(new Integer(valueString).intValue());
            else if ("yshw".equals(tempString)) 
            	yunshu.setYshw(valueString); 
            else if ("zl".equals(tempString)) 
            	yunshu.setZl(valueString); 
            else if ("xysj".equals(tempString)) 
            	yunshu.setXysj(valueString); 
            else if ("qsd".equals(tempString)) 
            	yunshu.setQsd(valueString); 
            else if ("mudidi".equals(tempString)) 
            	yunshu.setMudidi(valueString); 
            else if ("gonglishu".equals(tempString)) 
            	yunshu.setGonglishu(valueString); 
            else if ("yunshuMemo".equals(tempString)) 
            	yunshu.setYunshuMemo(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("Yunshu".equals(localName)&&yunshu!=null){
			yunshuList.add(yunshu);
			yunshu = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		yunshuList = new ArrayList<Yunshu>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("Yunshu".equals(localName)) {
            yunshu = new Yunshu(); 
        }
        tempString = localName; 
	}

	public List<Yunshu> getYunshuList() {
		return this.yunshuList;
	}
}
