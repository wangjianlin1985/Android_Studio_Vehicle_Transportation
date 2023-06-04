package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.JzType;
public class JzTypeListHandler extends DefaultHandler {
	private List<JzType> jzTypeList = null;
	private JzType jzType;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (jzType != null) { 
            String valueString = new String(ch, start, length); 
            if ("typeId".equals(tempString)) 
            	jzType.setTypeId(new Integer(valueString).intValue());
            else if ("typeName".equals(tempString)) 
            	jzType.setTypeName(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("JzType".equals(localName)&&jzType!=null){
			jzTypeList.add(jzType);
			jzType = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		jzTypeList = new ArrayList<JzType>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("JzType".equals(localName)) {
            jzType = new JzType(); 
        }
        tempString = localName; 
	}

	public List<JzType> getJzTypeList() {
		return this.jzTypeList;
	}
}
