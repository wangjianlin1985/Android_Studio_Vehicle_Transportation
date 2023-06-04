package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.CarDAO;
import com.chengxusheji.domain.Car;
import com.chengxusheji.dao.CarModelDAO;
import com.chengxusheji.domain.CarModel;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CarAction extends BaseAction {

    /*�������Ҫ��ѯ������: ����*/
    private String carNo;
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public String getCarNo() {
        return this.carNo;
    }

    /*�������Ҫ��ѯ������: ����*/
    private CarModel carModel;
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
    public CarModel getCarModel() {
        return this.carModel;
    }

    /*�������Ҫ��ѯ������: Ʒ��*/
    private String pinpai;
    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }
    public String getPinpai() {
        return this.pinpai;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String youxing;
    public void setYouxing(String youxing) {
        this.youxing = youxing;
    }
    public String getYouxing() {
        return this.youxing;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int carId;
    public void setCarId(int carId) {
        this.carId = carId;
    }
    public int getCarId() {
        return carId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource CarModelDAO carModelDAO;
    @Resource CarDAO carDAO;

    /*��������Car����*/
    private Car car;
    public void setCar(Car car) {
        this.car = car;
    }
    public Car getCar() {
        return this.car;
    }

    /*��ת�����Car��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�CarModel��Ϣ*/
        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        return "add_view";
    }

    /*���Car��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCar() {
        ActionContext ctx = ActionContext.getContext();
        try {
            CarModel carModel = carModelDAO.GetCarModelByModelId(car.getCarModel().getModelId());
            car.setCarModel(carModel);
            carDAO.AddCar(car);
            ctx.put("message",  java.net.URLEncoder.encode("Car��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Car���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCar��Ϣ*/
    public String QueryCar() {
        if(currentPage == 0) currentPage = 1;
        if(carNo == null) carNo = "";
        if(pinpai == null) pinpai = "";
        if(youxing == null) youxing = "";
        List<Car> carList = carDAO.QueryCarInfo(carNo, carModel, pinpai, youxing, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        carDAO.CalculateTotalPageAndRecordNumber(carNo, carModel, pinpai, youxing);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = carDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = carDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("carList",  carList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("carNo", carNo);
        ctx.put("carModel", carModel);
        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        ctx.put("pinpai", pinpai);
        ctx.put("youxing", youxing);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCarOutputToExcel() { 
        if(carNo == null) carNo = "";
        if(pinpai == null) pinpai = "";
        if(youxing == null) youxing = "";
        List<Car> carList = carDAO.QueryCarInfo(carNo,carModel,pinpai,youxing);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Car��Ϣ��¼"; 
        String[] headers = { "����id","����","����","Ʒ��","����","������","����","�����(����)","ά�޴���"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<carList.size();i++) {
        	Car car = carList.get(i); 
        	dataset.add(new String[]{car.getCarId() + "",car.getCarNo(),car.getCarModel().getModelName(),
car.getPinpai(),car.getYouxing(),car.getHaoyouliang(),car.getChexian(),car.getZonglicheng(),car.getWxcs()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Car.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯCar��Ϣ*/
    public String FrontQueryCar() {
        if(currentPage == 0) currentPage = 1;
        if(carNo == null) carNo = "";
        if(pinpai == null) pinpai = "";
        if(youxing == null) youxing = "";
        List<Car> carList = carDAO.QueryCarInfo(carNo, carModel, pinpai, youxing, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        carDAO.CalculateTotalPageAndRecordNumber(carNo, carModel, pinpai, youxing);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = carDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = carDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("carList",  carList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("carNo", carNo);
        ctx.put("carModel", carModel);
        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        ctx.put("pinpai", pinpai);
        ctx.put("youxing", youxing);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Car��Ϣ*/
    public String ModifyCarQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������carId��ȡCar����*/
        Car car = carDAO.GetCarByCarId(carId);

        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        ctx.put("car",  car);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Car��Ϣ*/
    public String FrontShowCarQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������carId��ȡCar����*/
        Car car = carDAO.GetCarByCarId(carId);

        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        ctx.put("car",  car);
        return "front_show_view";
    }

    /*�����޸�Car��Ϣ*/
    public String ModifyCar() {
        ActionContext ctx = ActionContext.getContext();
        try {
            CarModel carModel = carModelDAO.GetCarModelByModelId(car.getCarModel().getModelId());
            car.setCarModel(carModel);
            carDAO.UpdateCar(car);
            ctx.put("message",  java.net.URLEncoder.encode("Car��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Car��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Car��Ϣ*/
    public String DeleteCar() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            carDAO.DeleteCar(carId);
            ctx.put("message",  java.net.URLEncoder.encode("Carɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Carɾ��ʧ��!"));
            return "error";
        }
    }

}
