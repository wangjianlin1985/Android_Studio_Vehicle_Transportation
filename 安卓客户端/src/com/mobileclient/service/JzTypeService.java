package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.JzType;
import com.mobileclient.util.HttpUtil;

/*�������͹���ҵ���߼���*/
public class JzTypeService {
	/* ��Ӽ������� */
	public String AddJzType(JzType jzType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", jzType.getTypeId() + "");
		params.put("typeName", jzType.getTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JzTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�������� */
	public List<JzType> QueryJzType(JzType queryConditionJzType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "JzTypeServlet?action=query";
		if(queryConditionJzType != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		JzTypeListHandler jzTypeListHander = new JzTypeListHandler();
		xr.setContentHandler(jzTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<JzType> jzTypeList = jzTypeListHander.getJzTypeList();
		return jzTypeList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<JzType> jzTypeList = new ArrayList<JzType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				JzType jzType = new JzType();
				jzType.setTypeId(object.getInt("typeId"));
				jzType.setTypeName(object.getString("typeName"));
				jzTypeList.add(jzType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jzTypeList;
	}

	/* ���¼������� */
	public String UpdateJzType(JzType jzType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", jzType.getTypeId() + "");
		params.put("typeName", jzType.getTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JzTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���������� */
	public String DeleteJzType(int typeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JzTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "����������Ϣɾ��ʧ��!";
		}
	}

	/* ��������id��ȡ�������Ͷ��� */
	public JzType GetJzType(int typeId)  {
		List<JzType> jzTypeList = new ArrayList<JzType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("typeId", typeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "JzTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				JzType jzType = new JzType();
				jzType.setTypeId(object.getInt("typeId"));
				jzType.setTypeName(object.getString("typeName"));
				jzTypeList.add(jzType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = jzTypeList.size();
		if(size>0) return jzTypeList.get(0); 
		else return null; 
	}
}
