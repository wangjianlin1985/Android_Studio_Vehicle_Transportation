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

    /*�������Ҫ��ѯ������: �ݺ�*/
    private String jiahao;
    public void setJiahao(String jiahao) {
        this.jiahao = jiahao;
    }
    public String getJiahao() {
        return this.jiahao;
    }

    /*�������Ҫ��ѯ������: ����*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*�������Ҫ��ѯ������: �绰*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private JzType jzTypeObj;
    public void setJzTypeObj(JzType jzTypeObj) {
        this.jzTypeObj = jzTypeObj;
    }
    public JzType getJzTypeObj() {
        return this.jzTypeObj;
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource JzTypeDAO jzTypeDAO;
    @Resource UserInfoDAO userInfoDAO;

    /*��������UserInfo����*/
    private UserInfo userInfo;
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfo getUserInfo() {
        return this.userInfo;
    }

    /*��ת�����UserInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�JzType��Ϣ*/
        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        return "add_view";
    }

    /*���UserInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�ݺ��Ƿ��Ѿ�����*/
        String jiahao = userInfo.getJiahao();
        UserInfo db_userInfo = userInfoDAO.GetUserInfoByJiahao(jiahao);
        if(null != db_userInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�üݺ��Ѿ�����!"));
            return "error";
        }
        try {
            JzType jzTypeObj = jzTypeDAO.GetJzTypeByTypeId(userInfo.getJzTypeObj().getTypeId());
            userInfo.setJzTypeObj(jzTypeObj);
            userInfoDAO.AddUserInfo(userInfo);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯUserInfo��Ϣ*/
    public String QueryUserInfo() {
        if(currentPage == 0) currentPage = 1;
        if(jiahao == null) jiahao = "";
        if(name == null) name = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(jiahao, name, telephone, jzTypeObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        userInfoDAO.CalculateTotalPageAndRecordNumber(jiahao, name, telephone, jzTypeObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = userInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryUserInfoOutputToExcel() { 
        if(jiahao == null) jiahao = "";
        if(name == null) name = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(jiahao,name,telephone,jzTypeObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "UserInfo��Ϣ��¼"; 
        String[] headers = { "�ݺ�","����","�Ա�","�绰","��������","����"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"UserInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯUserInfo��Ϣ*/
    public String FrontQueryUserInfo() {
        if(currentPage == 0) currentPage = 1;
        if(jiahao == null) jiahao = "";
        if(name == null) name = "";
        if(telephone == null) telephone = "";
        List<UserInfo> userInfoList = userInfoDAO.QueryUserInfoInfo(jiahao, name, telephone, jzTypeObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        userInfoDAO.CalculateTotalPageAndRecordNumber(jiahao, name, telephone, jzTypeObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = userInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�UserInfo��Ϣ*/
    public String ModifyUserInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jiahao��ȡUserInfo����*/
        UserInfo userInfo = userInfoDAO.GetUserInfoByJiahao(jiahao);

        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        ctx.put("userInfo",  userInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�UserInfo��Ϣ*/
    public String FrontShowUserInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������jiahao��ȡUserInfo����*/
        UserInfo userInfo = userInfoDAO.GetUserInfoByJiahao(jiahao);

        List<JzType> jzTypeList = jzTypeDAO.QueryAllJzTypeInfo();
        ctx.put("jzTypeList", jzTypeList);
        ctx.put("userInfo",  userInfo);
        return "front_show_view";
    }

    /*�����޸�UserInfo��Ϣ*/
    public String ModifyUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            JzType jzTypeObj = jzTypeDAO.GetJzTypeByTypeId(userInfo.getJzTypeObj().getTypeId());
            userInfo.setJzTypeObj(jzTypeObj);
            userInfoDAO.UpdateUserInfo(userInfo);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��UserInfo��Ϣ*/
    public String DeleteUserInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            userInfoDAO.DeleteUserInfo(jiahao);
            ctx.put("message",  java.net.URLEncoder.encode("UserInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("UserInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
