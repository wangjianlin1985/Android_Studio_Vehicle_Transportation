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
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.dao.JzTypeDAO;
import com.chengxusheji.domain.JzType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class UserInfoAction extends BaseAction {

    /*界面层需要查询的属性: 驾号*/
    private String jiahao;
    public void setJiahao(String jiahao) {
        this.jiahao = jiahao;
    }
    public String getJiahao() {
        return this.jiahao;
    }

    /*界面层需要查询的属性: 姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 驾照类型*/
    private JzType jzTypeObj;
    public void setJzTypeObj(JzType jzTypeObj) {
        this.jzTypeObj = jzTypeObj;
    }
    public JzType getJzTypeObj() {
        return this.jzTypeObj;
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
    @Resource UserInfoDAO userInfoDAO;

    /*待操作的UserInfo对象*/
    private UserInfo userInfo;
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    /*跳转到添加UserInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的JzType信息*/
        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        return "add_view";
    }

    /*添加UserInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证驾号是否已经存在*/
        String jiahao = userInfo.getJiahao();
        UserInfo db_userInfo = userInfoDAO.GetUserInfoByJiahao(jiahao);
        if(null != db_userInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该驾号已经存在!"));
            return "error";
        }
        try {
            JzType jzTypeObj = jzTypeDAO.GetJzTypeByTypeId(userInfo.getJzTypeObj().getTypeId());
            userInfo.setJzTypeObj(jzTypeObj);
            userInfoDAO.AddUserInfo(userInfo);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo添加失败!"));
            return "error";
        }
    }

    /*查询UserInfo信息*/
    public String QueryUserInfo() {
        if(currentPage == 0) currentPage = 1;
        if(jiahao == null) jiahao = "";
        if(name == null) name = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(jiahao, name, telephone, jzTypeObj, currentPage);
        /*计算总的页数和总的记录数*/
        userInfoDAO.CalculateTotalPageAndRecordNumber(jiahao, name, telephone, jzTypeObj);
        /*获取到总的页码数目*/
        totalPage = userInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = userInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userInfoList",  userInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jiahao", jiahao);
        ctx.put("name", name);
        ctx.put("telephone", telephone);
        ctx.put("jzTypeObj", jzTypeObj);
        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryUserInfoOutputToExcel() { 
        if(jiahao == null) jiahao = "";
        if(name == null) name = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(jiahao,name,telephone,jzTypeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "UserInfo信息记录"; 
        String[] headers = { "驾号","姓名","性别","电话","驾照类型","驾龄"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<userInfoList.size();i++) {
        	UserInfo userInfo = userInfoList.get(i); 
        	dataset.add(new String[]{userInfo.getJiahao(),userInfo.getName(),userInfo.getSex(),userInfo.getTelephone(),userInfo.getJzTypeObj().getTypeName(),
userInfo.getJialing()});
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
			response.setHeader("Content-disposition","attachment; filename="+"UserInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询UserInfo信息*/
    public String FrontQueryUserInfo() {
        if(currentPage == 0) currentPage = 1;
        if(jiahao == null) jiahao = "";
        if(name == null) name = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(jiahao, name, telephone, jzTypeObj, currentPage);
        /*计算总的页数和总的记录数*/
        userInfoDAO.CalculateTotalPageAndRecordNumber(jiahao, name, telephone, jzTypeObj);
        /*获取到总的页码数目*/
        totalPage = userInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = userInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("userInfoList",  userInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("jiahao", jiahao);
        ctx.put("name", name);
        ctx.put("telephone", telephone);
        ctx.put("jzTypeObj", jzTypeObj);
        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        return "front_query_view";
    }

    /*查询要修改的UserInfo信息*/
    public String ModifyUserInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jiahao获取UserInfo对象*/
        UserInfo userInfo = userInfoDAO.GetUserInfoByJiahao(jiahao);

        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        ctx.put("userInfo",  userInfo);
        return "modify_view";
    }

    /*查询要修改的UserInfo信息*/
    public String FrontShowUserInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键jiahao获取UserInfo对象*/
        UserInfo userInfo = userInfoDAO.GetUserInfoByJiahao(jiahao);

        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        ctx.put("userInfo",  userInfo);
        return "front_show_view";
    }

    /*更新修改UserInfo信息*/
    public String ModifyUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            JzType jzTypeObj = jzTypeDAO.GetJzTypeByTypeId(userInfo.getJzTypeObj().getTypeId());
            userInfo.setJzTypeObj(jzTypeObj);
            userInfoDAO.UpdateUserInfo(userInfo);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除UserInfo信息*/
    public String DeleteUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            userInfoDAO.DeleteUserInfo(jiahao);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo删除失败!"));
            return "error";
        }
    }

}
