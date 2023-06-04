package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Car;
import com.mobileclient.util.HttpUtil;

/*车辆管理业务逻辑层*/
public class CarService {
	/* 添加车辆 */
	public String AddCar(Car car) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("carId", car.getCarId() + "");
		params.put("carNo", car.getCarNo());
		params.put("carModel", car.getCarModel() + "");
		params.put("pinpai", car.getPinpai());
		params.put("youxing", car.getYouxing());
		params.put("haoyouliang", car.getHaoyouliang());
		params.put("chexian", car.getChexian());
		params.put("zonglicheng", car.getZonglicheng());
		params.put("wxcs", car.getWxcs());
		params.put("carMemo", car.getCarMemo());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询车辆 */
	public List<Car> QueryCar(Car queryConditionCar) throws Exception {
		String urlString = HttpUtil.BASE_URL + "CarServlet?action=query";
		if(queryConditionCar != null) {
			urlString += "&carNo=" + URLEncoder.encode(queryConditionCar.getCarNo(), "UTF-8") + "";
			urlString += "&carModel=" + queryConditionCar.getCarModel();
			urlString += "&pinpai=" + URLEncoder.encode(queryConditionCar.getPinpai(), "UTF-8") + "";
			urlString += "&youxing=" + URLEncoder.encode(queryConditionCar.getYouxing(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		CarListHandler carListHander = new CarListHandler();
		xr.setContentHandler(carListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Car> carList = carListHander.getCarList();
		return carList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Car> carList = new ArrayList<Car>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Car car = new Car();
				car.setCarId(object.getInt("carId"));
				car.setCarNo(object.getString("carNo"));
				car.setCarModel(object.getInt("carModel"));
				car.setPinpai(object.getString("pinpai"));
				car.setYouxing(object.getString("youxing"));
				car.setHaoyouliang(object.getString("haoyouliang"));
				car.setChexian(object.getString("chexian"));
				car.setZonglicheng(object.getString("zonglicheng"));
				car.setWxcs(object.getString("wxcs"));
				car.setCarMemo(object.getString("carMemo"));
				carList.add(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return carList;
	}

	/* 更新车辆 */
	public String UpdateCar(Car car) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("carId", car.getCarId() + "");
		params.put("carNo", car.getCarNo());
		params.put("carModel", car.getCarModel() + "");
		params.put("pinpai", car.getPinpai());
		params.put("youxing", car.getYouxing());
		params.put("haoyouliang", car.getHaoyouliang());
		params.put("chexian", car.getChexian());
		params.put("zonglicheng", car.getZonglicheng());
		params.put("wxcs", car.getWxcs());
		params.put("carMemo", car.getCarMemo());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除车辆 */
	public String DeleteCar(int carId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("carId", carId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "车辆信息删除失败!";
		}
	}

	/* 根据车辆id获取车辆对象 */
	public Car GetCar(int carId)  {
		List<Car> carList = new ArrayList<Car>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("carId", carId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "CarServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Car car = new Car();
				car.setCarId(object.getInt("carId"));
				car.setCarNo(object.getString("carNo"));
				car.setCarModel(object.getInt("carModel"));
				car.setPinpai(object.getString("pinpai"));
				car.setYouxing(object.getString("youxing"));
				car.setHaoyouliang(object.getString("haoyouliang"));
				car.setChexian(object.getString("chexian"));
				car.setZonglicheng(object.getString("zonglicheng"));
				car.setWxcs(object.getString("wxcs"));
				car.setCarMemo(object.getString("carMemo"));
				carList.add(car);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = carList.size();
		if(size>0) return carList.get(0); 
		else return null; 
	}
}
