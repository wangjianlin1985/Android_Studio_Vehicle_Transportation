package com.mobileclient.handler;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.mobileclient.domain.UserInfo;
public class UserInfoListHandler extends DefaultHandler {
	private List<UserInfo> userInfoList = null;
	private UserInfo userInfo;
	private String tempString;
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		if (userInfo != null) { 
            String valueString = new String(ch, start, length); 
            if ("jiahao".equals(tempString)) 
            	userInfo.setJiahao(valueString); 
            else if ("password".equals(tempString)) 
            	userInfo.setPassword(valueString); 
            else if ("name".equals(tempString)) 
            	userInfo.setName(valueString); 
            else if ("sex".equals(tempString)) 
            	userInfo.setSex(valueString); 
            else if ("telephone".equals(tempString)) 
            	userInfo.setTelephone(valueString); 
            else if ("jzTypeObj".equals(tempString)) 
            	userInfo.setJzTypeObj(new Integer(valueString).intValue());
            else if ("jialing".equals(tempString)) 
            	userInfo.setJialing(valueString); 
            else if ("address".equals(tempString)) 
            	userInfo.setAddress(valueString); 
        } 
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("UserInfo".equals(localName)&&userInfo!=null){
			userInfoList.add(userInfo);
			userInfo = null; 
		}
		tempString = null;
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		userInfoList = new ArrayList<UserInfo>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
        if ("UserInfo".equals(localName)) {
            userInfo = new UserInfo(); 
        }
        tempString = localName; 
	}

	public List<UserInfo> getUserInfoList() {
		return this.userInfoList;
	}
}
