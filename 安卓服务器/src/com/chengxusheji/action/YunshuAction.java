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

    /*界面层需要查询的属性: 驾号*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 车牌*/
    private Car carObj;
    public void setCarObj(Car carObj) {
        this.carObj = carObj;
    }
    public Car getCarObj() {
        return this.carObj;
    }

    /*界面层需要查询的属性: 运输货物*/
    private String yshw;
    public void setYshw(String yshw) {
        this.yshw = yshw;
    }
    public String getYshw() {
        return this.yshw;
    }

    /*界面层需要查询的属性: 起始地*/
    private String qsd;
    public void setQsd(String qsd) {
        this.qsd = qsd;
    }
    public String getQsd() {
        return this.qsd;
    }

    /*界面层需要查询的属性: 目的地*/
    private String mudidi;
    public void setMudidi(String mudidi) {
        this.mudidi = mudidi;
    }
    public String getMudidi() {
        return this.mudidi;
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

    private int yunshuId;
    public void setYunshuId(int yunshuId) {
        this.yunshuId = yunshuId;
    }
    public int getYunshuId() {
        return yunshuId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource CarDAO carDAO;
    @Resource YunshuDAO yunshuDAO;

    /*待操作的Yunshu对象*/
    private Yunshu yunshu;
    public void setYunshu(Yunshu yunshu) {
        this.yunshu = yunshu;
    }
    public Yunshu getYunshu() {
        return this.yunshu;
    }

    /*跳转到添加Yunshu视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        /*查询所有的Car信息*/
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        return "add_view";
    }

    /*添加Yunshu信息*/
    @SuppressWarnings("deprecation")
    public String AddYunshu() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByJiahao(yunshu.getUserObj().getJiahao());
            yunshu.setUserObj(userObj);
            Car carObj = carDAO.GetCarByCarId(yunshu.getCarObj().getCarId());
            yunshu.setCarObj(carObj);
            yunshuDAO.AddYunshu(yunshu);
            ctx.put("message",  java.net.URLEncoder.encode("Yunshu添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Yunshu添加失败!"));
            return "error";
        }
    }

    /*查询Yunshu信息*/
    public String QueryYunshu() {
        if(currentPage == 0) currentPage = 1;
        if(yshw == null) yshw = "";
        if(qsd == null) qsd = "";
        if(mudidi == null) mudidi = "";
        List<Yunshu> yunshuList = yunshuDAO.QueryYunshuInfo(userObj, carObj, yshw, qsd, mudidi, currentPage);
        /*计算总的页数和总的记录数*/
        yunshuDAO.CalculateTotalPageAndRecordNumber(userObj, carObj, yshw, qsd, mudidi);
        /*获取到总的页码数目*/
        totalPage = yunshuDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryYunshuOutputToExcel() { 
        if(yshw == null) yshw = "";
        if(qsd == null) qsd = "";
        if(mudidi == null) mudidi = "";
        List<Yunshu> yunshuList = yunshuDAO.QueryYunshuInfo(userObj,carObj,yshw,qsd,mudidi);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Yunshu信息记录"; 
        String[] headers = { "记录id","驾号","车牌","运输货物","重量(吨)","需要时间","起始地","目的地","公里数"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Yunshu.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Yunshu信息*/
    public String FrontQueryYunshu() {
        if(currentPage == 0) currentPage = 1;
        if(yshw == null) yshw = "";
        if(qsd == null) qsd = "";
        if(mudidi == null) mudidi = "";
        List<Yunshu> yunshuList = yunshuDAO.QueryYunshuInfo(userObj, carObj, yshw, qsd, mudidi, currentPage);
        /*计算总的页数和总的记录数*/
        yunshuDAO.CalculateTotalPageAndRecordNumber(userObj, carObj, yshw, qsd, mudidi);
        /*获取到总的页码数目*/
        totalPage = yunshuDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Yunshu信息*/
    public String ModifyYunshuQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键yunshuId获取Yunshu对象*/
        Yunshu yunshu = yunshuDAO.GetYunshuByYunshuId(yunshuId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        ctx.put("yunshu",  yunshu);
        return "modify_view";
    }

    /*查询要修改的Yunshu信息*/
    public String FrontShowYunshuQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键yunshuId获取Yunshu对象*/
        Yunshu yunshu = yunshuDAO.GetYunshuByYunshuId(yunshuId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        List<Car> carList = carDAO.QueryAllCarInfo();
        ctx.put("carList", carList);
        ctx.put("yunshu",  yunshu);
        return "front_show_view";
    }

    /*更新修改Yunshu信息*/
    public String ModifyYunshu() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByJiahao(yunshu.getUserObj().getJiahao());
            yunshu.setUserObj(userObj);
            Car carObj = carDAO.GetCarByCarId(yunshu.getCarObj().getCarId());
            yunshu.setCarObj(carObj);
            yunshuDAO.UpdateYunshu(yunshu);
            ctx.put("message",  java.net.URLEncoder.encode("Yunshu信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Yunshu信息更新失败!"));
            return "error";
       }
   }

    /*删除Yunshu信息*/
    public String DeleteYunshu() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            yunshuDAO.DeleteYunshu(yunshuId);
            ctx.put("message",  java.net.URLEncoder.encode("Yunshu删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Yunshu删除失败!"));
            return "error";
        }
    }

}
