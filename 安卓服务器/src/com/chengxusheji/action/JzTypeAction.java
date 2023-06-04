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

    private int typeId;
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
    public int getTypeId() {
        return typeId;
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

    /*��������JzType����*/
    private JzType jzType;
    public void setJzType(JzType jzType) {
        this.jzType = jzType;
    }
    public JzType getJzType() {
        return this.jzType;
    }

    /*��ת�����JzType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���JzType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddJzType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jzTypeDAO.AddJzType(jzType);
            ctx.put("message",  java.net.URLEncoder.encode("JzType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JzType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯJzType��Ϣ*/
    public String QueryJzType() {
        if(currentPage == 0) currentPage = 1;
        List<JzType> jzTypeList = jzTypeDAO.QueryJzTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jzTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jzTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jzTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jzTypeList",  jzTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryJzTypeOutputToExcel() { 
        List<JzType> jzTypeList = jzTypeDAO.QueryJzTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "JzType��Ϣ��¼"; 
        String[] headers = { "����id","��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"JzType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯJzType��Ϣ*/
    public String FrontQueryJzType() {
        if(currentPage == 0) currentPage = 1;
        List<JzType> jzTypeList = jzTypeDAO.QueryJzTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        jzTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = jzTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = jzTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("jzTypeList",  jzTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�JzType��Ϣ*/
    public String ModifyJzTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡJzType����*/
        JzType jzType = jzTypeDAO.GetJzTypeByTypeId(typeId);

        ctx.put("jzType",  jzType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�JzType��Ϣ*/
    public String FrontShowJzTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������typeId��ȡJzType����*/
        JzType jzType = jzTypeDAO.GetJzTypeByTypeId(typeId);

        ctx.put("jzType",  jzType);
        return "front_show_view";
    }

    /*�����޸�JzType��Ϣ*/
    public String ModifyJzType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            jzTypeDAO.UpdateJzType(jzType);
            ctx.put("message",  java.net.URLEncoder.encode("JzType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JzType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��JzType��Ϣ*/
    public String DeleteJzType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            jzTypeDAO.DeleteJzType(typeId);
            ctx.put("message",  java.net.URLEncoder.encode("JzTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("JzTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
