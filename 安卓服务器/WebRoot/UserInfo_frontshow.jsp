<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.JzType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的JzType信息
    List<JzType> jzTypeList = (List<JzType>)request.getAttribute("jzTypeList");
    UserInfo userInfo = (UserInfo)request.getAttribute("userInfo");

%>
<HTML><HEAD><TITLE>查看用户</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>驾号:</td>
    <td width=70%><%=userInfo.getJiahao() %></td>
  </tr>

  <tr>
    <td width=30%>登录密码:</td>
    <td width=70%><%=userInfo.getPassword() %></td>
  </tr>

  <tr>
    <td width=30%>姓名:</td>
    <td width=70%><%=userInfo.getName() %></td>
  </tr>

  <tr>
    <td width=30%>性别:</td>
    <td width=70%><%=userInfo.getSex() %></td>
  </tr>

  <tr>
    <td width=30%>电话:</td>
    <td width=70%><%=userInfo.getTelephone() %></td>
  </tr>

  <tr>
    <td width=30%>驾照类型:</td>
    <td width=70%>
      <%=userInfo.getJzTypeObj().getTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>驾龄:</td>
    <td width=70%><%=userInfo.getJialing() %></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><%=userInfo.getAddress() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
