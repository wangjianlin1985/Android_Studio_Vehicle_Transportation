<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Yunshu" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Car" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�Car��Ϣ
    List<Car> carList = (List<Car>)request.getAttribute("carList");
    Yunshu yunshu = (Yunshu)request.getAttribute("yunshu");

%>
<HTML><HEAD><TITLE>�鿴���䵥</TITLE>
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
    <td width=30%>��¼id:</td>
    <td width=70%><%=yunshu.getYunshuId() %></td>
  </tr>

  <tr>
    <td width=30%>�ݺ�:</td>
    <td width=70%>
      <%=yunshu.getUserObj().getJiahao() %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <%=yunshu.getCarObj().getCarNo() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=yunshu.getYshw() %></td>
  </tr>

  <tr>
    <td width=30%>����(��):</td>
    <td width=70%><%=yunshu.getZl() %></td>
  </tr>

  <tr>
    <td width=30%>��Ҫʱ��:</td>
    <td width=70%><%=yunshu.getXysj() %></td>
  </tr>

  <tr>
    <td width=30%>��ʼ��:</td>
    <td width=70%><%=yunshu.getQsd() %></td>
  </tr>

  <tr>
    <td width=30%>Ŀ�ĵ�:</td>
    <td width=70%><%=yunshu.getMudidi() %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><%=yunshu.getGonglishu() %></td>
  </tr>

  <tr>
    <td width=30%>���䱸ע:</td>
    <td width=70%><%=yunshu.getYunshuMemo() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
