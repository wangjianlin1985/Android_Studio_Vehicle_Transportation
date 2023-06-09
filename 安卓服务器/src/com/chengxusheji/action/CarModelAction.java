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
import com.chengxusheji.dao.CarModelDAO;
import com.chengxusheji.domain.CarModel;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CarModelAction extends BaseAction {

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

    private int modelId;
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
    public int getModelId() {
        return modelId;
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

    /*待操作的CarModel对象*/
    private CarModel carModel;
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
    public CarModel getCarModel() {
        return this.carModel;
    }

    /*跳转到添加CarModel视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加CarModel信息*/
    @SuppressWarnings("deprecation")
    public String AddCarModel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            carModelDAO.AddCarModel(carModel);
            ctx.put("message",  java.net.URLEncoder.encode("CarModel添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CarModel添加失败!"));
            return "error";
        }
    }

    /*查询CarModel信息*/
    public String QueryCarModel() {
        if(currentPage == 0) currentPage = 1;
        List<CarModel> carModelList = carModelDAO.QueryCarModelInfo(currentPage);
        /*计算总的页数和总的记录数*/
        carModelDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = carModelDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = carModelDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("carModelList",  carModelList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryCarModelOutputToExcel() { 
        List<CarModel> carModelList = carModelDAO.QueryCarModelInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CarModel信息记录"; 
        String[] headers = { "车型id","车型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<carModelList.size();i++) {
        	CarModel carModel = carModelList.get(i); 
        	dataset.add(new String[]{carModel.getModelId() + "",carModel.getModelName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"CarModel.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询CarModel信息*/
    public String FrontQueryCarModel() {
        if(currentPage == 0) currentPage = 1;
        List<CarModel> carModelList = carModelDAO.QueryCarModelInfo(currentPage);
        /*计算总的页数和总的记录数*/
        carModelDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = carModelDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = carModelDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("carModelList",  carModelList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的CarModel信息*/
    public String ModifyCarModelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键modelId获取CarModel对象*/
        CarModel carModel = carModelDAO.GetCarModelByModelId(modelId);

        ctx.put("carModel",  carModel);
        return "modify_view";
    }

    /*查询要修改的CarModel信息*/
    public String FrontShowCarModelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键modelId获取CarModel对象*/
        CarModel carModel = carModelDAO.GetCarModelByModelId(modelId);

        ctx.put("carModel",  carModel);
        return "front_show_view";
    }

    /*更新修改CarModel信息*/
    public String ModifyCarModel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            carModelDAO.UpdateCarModel(carModel);
            ctx.put("message",  java.net.URLEncoder.encode("CarModel信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CarModel信息更新失败!"));
            return "error";
       }
   }

    /*删除CarModel信息*/
    public String DeleteCarModel() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            carModelDAO.DeleteCarModel(modelId);
            ctx.put("message",  java.net.URLEncoder.encode("CarModel删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CarModel删除失败!"));
            return "error";
        }
    }

}
