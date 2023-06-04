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
import com.chengxusheji.dao.JzTypeDAO;
import com.chengxusheji.domain.JzType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class JzTypeAction extends BaseAction {

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

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
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
    @Resource JzTypeDAO jzTypeDAO;

    /*待操作的JzType对象*/
    private JzType jzType;
    public void setJzType(JzType jzType) {
        this.jzType = jzType;
    }
    public JzType getJzType() {
        return this.jzType;
    }

    /*跳转到添加JzType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加JzType信息*/
    @SuppressWarnings("deprecation")
    public String AddJzType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jzTypeDAO.AddJzType(jzType);
            ctx.put("message",  java.net.URLEncoder.encode("JzType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JzType添加失败!"));
            return "error";
        }
    }

    /*查询JzType信息*/
    public String QueryJzType() {
        if(currentPage == 0) currentPage = 1;
        List<JzType> jzTypeList = jzTypeDAO.QueryJzTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        jzTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = jzTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jzTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jzTypeList",  jzTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryJzTypeOutputToExcel() { 
        List<JzType> jzTypeList = jzTypeDAO.QueryJzTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "JzType信息记录"; 
        String[] headers = { "类型id","类型名称"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<jzTypeList.size();i++) {
        	JzType jzType = jzTypeList.get(i); 
        	dataset.add(new String[]{jzType.getTypeId() + "",jzType.getTypeName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"JzType.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询JzType信息*/
    public String FrontQueryJzType() {
        if(currentPage == 0) currentPage = 1;
        List<JzType> jzTypeList = jzTypeDAO.QueryJzTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        jzTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = jzTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = jzTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jzTypeList",  jzTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的JzType信息*/
    public String ModifyJzTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取JzType对象*/
        JzType jzType = jzTypeDAO.GetJzTypeByTypeId(typeId);

        ctx.put("jzType",  jzType);
        return "modify_view";
    }

    /*查询要修改的JzType信息*/
    public String FrontShowJzTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键typeId获取JzType对象*/
        JzType jzType = jzTypeDAO.GetJzTypeByTypeId(typeId);

        ctx.put("jzType",  jzType);
        return "front_show_view";
    }

    /*更新修改JzType信息*/
    public String ModifyJzType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jzTypeDAO.UpdateJzType(jzType);
            ctx.put("message",  java.net.URLEncoder.encode("JzType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JzType信息更新失败!"));
            return "error";
       }
   }

    /*删除JzType信息*/
    public String DeleteJzType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jzTypeDAO.DeleteJzType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("JzType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JzType删除失败!"));
            return "error";
        }
    }

}
