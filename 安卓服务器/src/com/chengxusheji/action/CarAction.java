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

    /*界面层需要查询的属性: 车牌*/
    private String carNo;
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public String getCarNo() {
        return this.carNo;
    }

    /*界面层需要查询的属性: 车型*/
    private CarModel carModel;
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
    public CarModel getCarModel() {
        return this.carModel;
    }

    /*界面层需要查询的属性: 品牌*/
    private String pinpai;
    public void setPinpai(String pinpai) {
        this.pinpai = pinpai;
    }
    public String getPinpai() {
        return this.pinpai;
    }

    /*界面层需要查询的属性: 油型*/
    private String youxing;
    public void setYouxing(String youxing) {
        this.youxing = youxing;
    }
    public String getYouxing() {
        return this.youxing;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource CarModelDAO carModelDAO;
    @Resource CarDAO carDAO;

    /*待操作的Car对象*/
    private Car car;
    public void setCar(Car car) {
        this.car = car;
    }
    public Car getCar() {
        return this.car;
    }

    /*跳转到添加Car视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的CarModel信息*/
        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        return "add_view";
    }

    /*添加Car信息*/
    @SuppressWarnings("deprecation")
    public String AddCar() {
        ActionContext ctx = ActionContext.getContext();
        try {
            CarModel carModel = carModelDAO.GetCarModelByModelId(car.getCarModel().getModelId());
            car.setCarModel(carModel);
            carDAO.AddCar(car);
            ctx.put("message",  java.net.URLEncoder.encode("Car添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Car添加失败!"));
            return "error";
        }
    }

    /*查询Car信息*/
    public String QueryCar() {
        if(currentPage == 0) currentPage = 1;
        if(carNo == null) carNo = "";
        if(pinpai == null) pinpai = "";
        if(youxing == null) youxing = "";
        List<Car> carList = carDAO.QueryCarInfo(carNo, carModel, pinpai, youxing, currentPage);
        /*计算总的页数和总的记录数*/
        carDAO.CalculateTotalPageAndRecordNumber(carNo, carModel, pinpai, youxing);
        /*获取到总的页码数目*/
        totalPage = carDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryCarOutputToExcel() { 
        if(carNo == null) carNo = "";
        if(pinpai == null) pinpai = "";
        if(youxing == null) youxing = "";
        List<Car> carList = carDAO.QueryCarInfo(carNo,carModel,pinpai,youxing);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Car信息记录"; 
        String[] headers = { "车辆id","车牌","车型","品牌","油型","耗油量","车险","总里程(公里)","维修次数"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Car.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Car信息*/
    public String FrontQueryCar() {
        if(currentPage == 0) currentPage = 1;
        if(carNo == null) carNo = "";
        if(pinpai == null) pinpai = "";
        if(youxing == null) youxing = "";
        List<Car> carList = carDAO.QueryCarInfo(carNo, carModel, pinpai, youxing, currentPage);
        /*计算总的页数和总的记录数*/
        carDAO.CalculateTotalPageAndRecordNumber(carNo, carModel, pinpai, youxing);
        /*获取到总的页码数目*/
        totalPage = carDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Car信息*/
    public String ModifyCarQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键carId获取Car对象*/
        Car car = carDAO.GetCarByCarId(carId);

        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        ctx.put("car",  car);
        return "modify_view";
    }

    /*查询要修改的Car信息*/
    public String FrontShowCarQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键carId获取Car对象*/
        Car car = carDAO.GetCarByCarId(carId);

        List<CarModel> carModelList = carModelDAO.QueryAllCarModelInfo();
        ctx.put("carModelList", carModelList);
        ctx.put("car",  car);
        return "front_show_view";
    }

    /*更新修改Car信息*/
    public String ModifyCar() {
        ActionContext ctx = ActionContext.getContext();
        try {
            CarModel carModel = carModelDAO.GetCarModelByModelId(car.getCarModel().getModelId());
            car.setCarModel(carModel);
            carDAO.UpdateCar(car);
            ctx.put("message",  java.net.URLEncoder.encode("Car信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Car信息更新失败!"));
            return "error";
       }
   }

    /*删除Car信息*/
    public String DeleteCar() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            carDAO.DeleteCar(carId);
            ctx.put("message",  java.net.URLEncoder.encode("Car删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Car删除失败!"));
            return "error";
        }
    }

}
