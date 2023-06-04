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

/*车型管理业务逻辑层*/
public class CarModelService {
	/* 添加车型 */
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

	/* 查询车型 */
	public List<CarModel> QueryCarModel(CarModel queryConditionCarModel) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CarModelServlet?action=query";
		if(queryConditionCarModel != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
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
		//第2种是基于json数据格式解析，我们采用的是第2种
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

	/* 更新车型 */
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

	/* 删除车型 */
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
			return "车型信息删除失败!";
		}
	}

	/* 根据车型id获取车型对象 */
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
