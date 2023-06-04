package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.CarModel;
import com.mobileclient.util.HttpUtil;

/*���͹���ҵ���߼���*/
public class CarModelService {
	/* ��ӳ��� */
	public String AddCarModel(CarModel carModel) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("modelId", carModel.getModelId() + "");
		params.put("modelName", carModel.getModelName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarModelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ��ѯ���� */
	public List<CarModel> QueryCarModel(CarModel queryConditionCarModel) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CarModelServlet?action=query";
		if(queryConditionCarModel != null) {
		}

		/* 2�����ݽ�����������һ������SAXParser����xml�ļ���ʽ
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CarModelListHandler carModelListHander = new CarModelListHandler();
		xr.setContentHandler(carModelListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<CarModel> carModelList = carModelListHander.getCarModelList();
		return carModelList;*/
		//��2���ǻ���json���ݸ�ʽ���������ǲ��õ��ǵ�2��
		List<CarModel> carModelList = new ArrayList<CarModel>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CarModel carModel = new CarModel();
				carModel.setModelId(object.getInt("modelId"));
				carModel.setModelName(object.getString("modelName"));
				carModelList.add(carModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return carModelList;
	}

	/* ���³��� */
	public String UpdateCarModel(CarModel carModel) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("modelId", carModel.getModelId() + "");
		params.put("modelName", carModel.getModelName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarModelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* ɾ������ */
	public String DeleteCarModel(int modelId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("modelId", modelId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarModelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "������Ϣɾ��ʧ��!";
		}
	}

	/* ���ݳ���id��ȡ���Ͷ��� */
	public CarModel GetCarModel(int modelId)  {
		List<CarModel> carModelList = new ArrayList<CarModel>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("modelId", modelId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarModelServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				CarModel carModel = new CarModel();
				carModel.setModelId(object.getInt("modelId"));
				carModel.setModelName(object.getString("modelName"));
				carModelList.add(carModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = carModelList.size();
		if(size>0) return carModelList.get(0); 
		else return null; 
	}
}
