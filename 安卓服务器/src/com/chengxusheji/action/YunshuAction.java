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
import com.chengxusheji.dao.YunshuDAO;
import com.chengxusheji.domain.Yunshu;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.CarDAO;
import com.chengxusheji.domain.Car;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class YunshuAction extends BaseAction {

    /*�������Ҫ��ѯ������: �ݺ�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ����*/
    private Car carObj;
    public void setCarObj(Car carObj) {
        this.carObj = carObj;
    }
    public Car getCarObj() {
        return this.carObj;
    }

    /*�������Ҫ��ѯ������: �������*/
    private String yshw;
    public void setYshw(String yshw) {
        this.yshw = yshw;
    }
    public String getYshw() {
        return this.yshw;
    }

    /*�������Ҫ��ѯ������: ��ʼ��*/
    private String qsd;
    public void setQsd(String qsd) {
        this.qsd = qsd;
    }
    public String getQsd() {
        return this.qsd;
    }

    /*�������Ҫ��ѯ������: Ŀ�ĵ�*/
    private String mudidi;
    public void setMudidi(String mudidi) {
        this.mudidi = mudidi;
    }
    public String getMudidi() {
        return this.mudidi;
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

    private int yunshuId;
    public void setYunshuId(int yunshuId) {
        this.yunshuId = yunshuId;
    }
    public int getYunshuId() {
        return yunshuId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource CarDAO carDAO;
    @Resource YunshuDAO yunshuDAO;

    /*��������Yunshu����*/
    private Yunshu yunshu;
    public void setYunshu(Yunshu yunshu) {
        this.yunshu = yunshu;
    }
    public Yunshu getYunshu() {
        return this.yunshu;
    }

    /*��ת�����Yunshu��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*��ѯ���е�Car��Ϣ*/
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        return "add_view";
    }

    /*���Yunshu��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddYunshu() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByJiahao(yunshu.getUserObj().getJiahao());
            yunshu.setUserObj(userObj);
            Car carObj = carDAO.GetCarByCarId(yunshu.getCarObj().getCarId());
            yunshu.setCarObj(carObj);
            yunshuDAO.AddYunshu(yunshu);
            ctx.put("message",  java.net.URLEncoder.encode("Yunshu��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Yunshu���ʧ��!"));
            return "error";
        }
    }

    /*��ѯYunshu��Ϣ*/
    public String QueryYunshu() {
        if(currentPage == 0) currentPage = 1;
        if(yshw == null) yshw = "";
        if(qsd == null) qsd = "";
        if(mudidi == null) mudidi = "";
        List<Yunshu> yunshuList = yunshuDAO.QueryYunshuInfo(userObj, carObj, yshw, qsd, mudidi, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        yunshuDAO.CalculateTotalPageAndRecordNumber(userObj, carObj, yshw, qsd, mudidi);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = yunshuDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = yunshuDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yunshuList",  yunshuList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("carObj", carObj);
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        ctx.put("yshw", yshw);
        ctx.put("qsd", qsd);
        ctx.put("mudidi", mudidi);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryYunshuOutputToExcel() { 
        if(yshw == null) yshw = "";
        if(qsd == null) qsd = "";
        if(mudidi == null) mudidi = "";
        List<Yunshu> yunshuList = yunshuDAO.QueryYunshuInfo(userObj,carObj,yshw,qsd,mudidi);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Yunshu��Ϣ��¼"; 
        String[] headers = { "��¼id","�ݺ�","����","�������","����(��)","��Ҫʱ��","��ʼ��","Ŀ�ĵ�","������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<yunshuList.size();i++) {
        	Yunshu yunshu = yunshuList.get(i); 
        	dataset.add(new String[]{yunshu.getYunshuId() + "",yunshu.getUserObj().getJiahao(),
yunshu.getCarObj().getCarNo(),
yunshu.getYshw(),yunshu.getZl(),yunshu.getXysj(),yunshu.getQsd(),yunshu.getMudidi(),yunshu.getGonglishu()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Yunshu.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯYunshu��Ϣ*/
    public String FrontQueryYunshu() {
        if(currentPage == 0) currentPage = 1;
        if(yshw == null) yshw = "";
        if(qsd == null) qsd = "";
        if(mudidi == null) mudidi = "";
        List<Yunshu> yunshuList = yunshuDAO.QueryYunshuInfo(userObj, carObj, yshw, qsd, mudidi, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        yunshuDAO.CalculateTotalPageAndRecordNumber(userObj, carObj, yshw, qsd, mudidi);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = yunshuDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = yunshuDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("yunshuList",  yunshuList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("carObj", carObj);
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        ctx.put("yshw", yshw);
        ctx.put("qsd", qsd);
        ctx.put("mudidi", mudidi);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Yunshu��Ϣ*/
    public String ModifyYunshuQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������yunshuId��ȡYunshu����*/
        Yunshu yunshu = yunshuDAO.GetYunshuByYunshuId(yunshuId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        ctx.put("yunshu",  yunshu);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Yunshu��Ϣ*/
    public String FrontShowYunshuQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������yunshuId��ȡYunshu����*/
        Yunshu yunshu = yunshuDAO.GetYunshuByYunshuId(yunshuId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        ctx.put("yunshu",  yunshu);
        return "front_show_view";
    }

    /*�����޸�Yunshu��Ϣ*/
    public String ModifyYunshu() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByJiahao(yunshu.getUserObj().getJiahao());
            yunshu.setUserObj(userObj);
            Car carObj = carDAO.GetCarByCarId(yunshu.getCarObj().getCarId());
            yunshu.setCarObj(carObj);
            yunshuDAO.UpdateYunshu(yunshu);
            ctx.put("message",  java.net.URLEncoder.encode("Yunshu��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Yunshu��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Yunshu��Ϣ*/
    public String DeleteYunshu() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            yunshuDAO.DeleteYunshu(yunshuId);
            ctx.put("message",  java.net.URLEncoder.encode("Yunshuɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Yunshuɾ��ʧ��!"));
            return "error";
        }
    }

}
