package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.UserInfo;
import com.mobileclient.util.HttpUtil;

/*�û�����ҵ���߼���*/
public class UserInfoService {
	/* ����û� */
	public String AddUserInfo(UserInfo userInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jiahao", userInfo.getJiahao());
		params.put("password", userInfo.getPassword());
		params.put("name", userInfo.getName());
		params.put("sex", userInfo.getSex());
		params.put("telephone", userInfo.getTelephone());
		params.put("jzTypeObj", userInfo.getJzTypeObj() + "");
		params.put("jialing", userInfo.getJialing());
		params.put("address", userInfo.getAddress());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ�û� */
	public List<UserInfo> QueryUserInfo(UserInfo queryConditionUserInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "UserInfoServlet?action=query";
		if(queryConditionUserInfo != null) {
			urlString += "&jiahao=" + URLEncoder.encode(queryConditionUserInfo.getJiahao(), "UTF-8") + "";
			urlString += "&name=" + URLEncoder.encode(queryConditionUserInfo.getName(), "UTF-8") + "";
			urlString += "&telephone=" + URLEncoder.encode(queryConditionUserInfo.getTelephone(), "UTF-8") + "";
			urlString += "&jzTypeObj=" + queryConditionUserInfo.getJzTypeObj();
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		UserInfoListHandler userInfoListHander = new UserInfoListHandler();
		xr.setContentHandler(userInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<UserInfo> userInfoList = userInfoListHander.getUserInfoList();
		return userInfoList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserInfo userInfo = new UserInfo();
				userInfo.setJiahao(object.getString("jiahao"));
				userInfo.setPassword(object.getString("password"));
				userInfo.setName(object.getString("name"));
				userInfo.setSex(object.getString("sex"));
				userInfo.setTelephone(object.getString("telephone"));
				userInfo.setJzTypeObj(object.getInt("jzTypeObj"));
				userInfo.setJialing(object.getString("jialing"));
				userInfo.setAddress(object.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfoList;
	}

	/* �����û� */
	public String UpdateUserInfo(UserInfo userInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jiahao", userInfo.getJiahao());
		params.put("password", userInfo.getPassword());
		params.put("name", userInfo.getName());
		params.put("sex", userInfo.getSex());
		params.put("telephone", userInfo.getTelephone());
		params.put("jzTypeObj", userInfo.getJzTypeObj() + "");
		params.put("jialing", userInfo.getJialing());
		params.put("address", userInfo.getAddress());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ���û� */
	public String DeleteUserInfo(String jiahao) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jiahao", jiahao);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "�û���Ϣɾ��ʧ��!";
		}
	}

	/* ���ݼݺŻ�ȡ�û����� */
	public UserInfo GetUserInfo(String jiahao)  {
		List<UserInfo> userInfoList = new ArrayList<UserInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("jiahao", jiahao);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "UserInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				UserInfo userInfo = new UserInfo();
				userInfo.setJiahao(object.getString("jiahao"));
				userInfo.setPassword(object.getString("password"));
				userInfo.setName(object.getString("name"));
				userInfo.setSex(object.getString("sex"));
				userInfo.setTelephone(object.getString("telephone"));
				userInfo.setJzTypeObj(object.getInt("jzTypeObj"));
				userInfo.setJialing(object.getString("jialing"));
				userInfo.setAddress(object.getString("address"));
				userInfoList.add(userInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = userInfoList.size();
		if(size>0) return userInfoList.get(0); 
		else return null; 
	}
}
