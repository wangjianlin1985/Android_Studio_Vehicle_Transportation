<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.JzType" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的JzType信息
    List<JzType> jzTypeList = (List<JzType>)request.getAttribute("jzTypeList");
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改用户</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var jiahao = document.getElementById("userInfo.jiahao").value;
    if(jiahao=="") {
        alert('请输入驾号!');
        return false;
    }
    var password = document.getElementById("userInfo.password").value;
    if(password=="") {
        alert('请输入登录密码!');
        return false;
    }
    var name = document.getElementById("userInfo.name").value;
    if(name=="") {
        alert('请输入姓名!');
        return false;
    }
    var sex = document.getElementById("userInfo.sex").value;
    if(sex=="") {
        alert('请输入性别!');
        return false;
    }
    var telephone = document.getElementById("userInfo.telephone").value;
    if(telephone=="") {
        alert('请输入电话!');
        return false;
    }
    var jialing = document.getElementById("userInfo.jialing").value;
    if(jialing=="") {
        alert('请输入驾龄!');
        return false;
    }
    var address = document.getElementById("userInfo.address").value;
    if(address=="") {
        alert('请输入家庭地址!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="UserInfo/UserInfo_ModifyUserInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>驾号:</td>
    <td width=70%><input id="userInfo.jiahao" name="userInfo.jiahao" type="text" value="<%=userInfo.getJiahao() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><input id="userInfo.password" name="userInfo.password" type="text" size="20" value='<%=userInfo.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><input id="userInfo.name" name="userInfo.name" type="text" size="20" value='<%=userInfo.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><input id="userInfo.sex" name="userInfo.sex" type="text" size="4" value='<%=userInfo.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>电话:</td>
    <td width=70%><input id="userInfo.telephone" name="userInfo.telephone" type="text" size="20" value='<%=userInfo.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>驾照类型:</td>
    <td width=70%>
      <select name="userInfo.jzTypeObj.typeId">
      <%
        for(JzType jzType:jzTypeList) {
          String selected = "";
          if(jzType.getTypeId() == userInfo.getJzTypeObj().getTypeId())
            selected = "selected";
      %>
          <option value='<%=jzType.getTypeId() %>' <%=selected %>><%=jzType.getTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>驾龄:</td>
    <td width=70%><input id="userInfo.jialing" name="userInfo.jialing" type="text" size="20" value='<%=userInfo.getJialing() %>'/></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><input id="userInfo.address" name="userInfo.address" type="text" size="80" value='<%=userInfo.getAddress() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
