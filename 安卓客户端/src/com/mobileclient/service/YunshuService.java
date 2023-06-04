package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Yunshu;
import com.mobileclient.util.HttpUtil;

/*运输单管理业务逻辑层*/
public class YunshuService {
	/* 添加运输单 */
	public String AddYunshu(Yunshu yunshu) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("yunshuId", yunshu.getYunshuId() + "");
		params.put("userObj", yunshu.getUserObj());
		params.put("carObj", yunshu.getCarObj() + "");
		params.put("yshw", yunshu.getYshw());
		params.put("zl", yunshu.getZl());
		params.put("xysj", yunshu.getXysj());
		params.put("qsd", yunshu.getQsd());
		params.put("mudidi", yunshu.getMudidi());
		params.put("gonglishu", yunshu.getGonglishu());
		params.put("yunshuMemo", yunshu.getYunshuMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YunshuServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询运输单 */
	public List<Yunshu> QueryYunshu(Yunshu queryConditionYunshu) throws Exception {
		String urlString = HttpUtil.BASE_URL + "YunshuServlet?action=query";
		if(queryConditionYunshu != null) {
			urlString += "&userObj=" + URLEncoder.encode(queryConditionYunshu.getUserObj(), "UTF-8") + "";
			urlString += "&carObj=" + queryConditionYunshu.getCarObj();
			urlString += "&yshw=" + URLEncoder.encode(queryConditionYunshu.getYshw(), "UTF-8") + "";
			urlString += "&qsd=" + URLEncoder.encode(queryConditionYunshu.getQsd(), "UTF-8") + "";
			urlString += "&mudidi=" + URLEncoder.encode(queryConditionYunshu.getMudidi(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		YunshuListHandler yunshuListHander = new YunshuListHandler();
		xr.setContentHandler(yunshuListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Yunshu> yunshuList = yunshuListHander.getYunshuList();
		return yunshuList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Yunshu> yunshuList = new ArrayList<Yunshu>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Yunshu yunshu = new Yunshu();
				yunshu.setYunshuId(object.getInt("yunshuId"));
				yunshu.setUserObj(object.getString("userObj"));
				yunshu.setCarObj(object.getInt("carObj"));
				yunshu.setYshw(object.getString("yshw"));
				yunshu.setZl(object.getString("zl"));
				yunshu.setXysj(object.getString("xysj"));
				yunshu.setQsd(object.getString("qsd"));
				yunshu.setMudidi(object.getString("mudidi"));
				yunshu.setGonglishu(object.getString("gonglishu"));
				yunshu.setYunshuMemo(object.getString("yunshuMemo"));
				yunshuList.add(yunshu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return yunshuList;
	}

	/* 更新运输单 */
	public String UpdateYunshu(Yunshu yunshu) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("yunshuId", yunshu.getYunshuId() + "");
		params.put("userObj", yunshu.getUserObj());
		params.put("carObj", yunshu.getCarObj() + "");
		params.put("yshw", yunshu.getYshw());
		params.put("zl", yunshu.getZl());
		params.put("xysj", yunshu.getXysj());
		params.put("qsd", yunshu.getQsd());
		params.put("mudidi", yunshu.getMudidi());
		params.put("gonglishu", yunshu.getGonglishu());
		params.put("yunshuMemo", yunshu.getYunshuMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YunshuServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除运输单 */
	public String DeleteYunshu(int yunshuId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("yunshuId", yunshuId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YunshuServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "运输单信息删除失败!";
		}
	}

	/* 根据记录id获取运输单对象 */
	public Yunshu GetYunshu(int yunshuId)  {
		List<Yunshu> yunshuList = new ArrayList<Yunshu>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("yunshuId", yunshuId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "YunshuServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Yunshu yunshu = new Yunshu();
				yunshu.setYunshuId(object.getInt("yunshuId"));
				yunshu.setUserObj(object.getString("userObj"));
				yunshu.setCarObj(object.getInt("carObj"));
				yunshu.setYshw(object.getString("yshw"));
				yunshu.setZl(object.getString("zl"));
				yunshu.setXysj(object.getString("xysj"));
				yunshu.setQsd(object.getString("qsd"));
				yunshu.setMudidi(object.getString("mudidi"));
				yunshu.setGonglishu(object.getString("gonglishu"));
				yunshu.setYunshuMemo(object.getString("yunshuMemo"));
				yunshuList.add(yunshu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = yunshuList.size();
		if(size>0) return yunshuList.get(0); 
		else return null; 
	}
}
