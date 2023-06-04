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

    private int modelId;
    public void setModelId(int modelId) {
        this.modelId = modelId;
    }
    public int getModelId() {
        return modelId;
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

    /*��������CarModel����*/
    private CarModel carModel;
    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }
    public CarModel getCarModel() {
        return this.carModel;
    }

    /*��ת�����CarModel��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���CarModel��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCarModel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            carModelDAO.AddCarModel(carModel);
            ctx.put("message",  java.net.URLEncoder.encode("CarModel��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CarModel���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCarModel��Ϣ*/
    public String QueryCarModel() {
        if(currentPage == 0) currentPage = 1;
        List<CarModel> carModelList = carModelDAO.QueryCarModelInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        carModelDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = carModelDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = carModelDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("carModelList",  carModelList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCarModelOutputToExcel() { 
        List<CarModel> carModelList = carModelDAO.QueryCarModelInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CarModel��Ϣ��¼"; 
        String[] headers = { "����id","��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"CarModel.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCarModel��Ϣ*/
    public String FrontQueryCarModel() {
        if(currentPage == 0) currentPage = 1;
        List<CarModel> carModelList = carModelDAO.QueryCarModelInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        carModelDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = carModelDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = carModelDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("carModelList",  carModelList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CarModel��Ϣ*/
    public String ModifyCarModelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������modelId��ȡCarModel����*/
        CarModel carModel = carModelDAO.GetCarModelByModelId(modelId);

        ctx.put("carModel",  carModel);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CarModel��Ϣ*/
    public String FrontShowCarModelQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������modelId��ȡCarModel����*/
        CarModel carModel = carModelDAO.GetCarModelByModelId(modelId);

        ctx.put("carModel",  carModel);
        return "front_show_view";
    }

    /*�����޸�CarModel��Ϣ*/
    public String ModifyCarModel() {
        ActionContext ctx = ActionContext.getContext();
        try {
            carModelDAO.UpdateCarModel(carModel);
            ctx.put("message",  java.net.URLEncoder.encode("CarModel��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CarModel��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CarModel��Ϣ*/
    public String DeleteCarModel() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            carModelDAO.DeleteCarModel(modelId);
            ctx.put("message",  java.net.URLEncoder.encode("CarModelɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CarModelɾ��ʧ��!"));
            return "error";
        }
    }

}
