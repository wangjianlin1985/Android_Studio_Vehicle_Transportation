<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Yunshu" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Car" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //获取所有的Car信息
    List<Car> carList = (List<Car>)request.getAttribute("carList");
    Yunshu yunshu = (Yunshu)request.getAttribute("yunshu");

%>
<HTML><HEAD><TITLE>查看运输单</TITLE>
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
    <td width=30%>记录id:</td>
    <td width=70%><%=yunshu.getYunshuId() %></td>
  </tr>

  <tr>
    <td width=30%>驾号:</td>
    <td width=70%>
      <%=yunshu.getUserObj().getJiahao() %>
    </td>
  </tr>

  <tr>
    <td width=30%>车牌:</td>
    <td width=70%>
      <%=yunshu.getCarObj().getCarNo() %>
    </td>
  </tr>

  <tr>
    <td width=30%>运输货物:</td>
    <td width=70%><%=yunshu.getYshw() %></td>
  </tr>

  <tr>
    <td width=30%>重量(吨):</td>
    <td width=70%><%=yunshu.getZl() %></td>
  </tr>

  <tr>
    <td width=30%>需要时间:</td>
    <td width=70%><%=yunshu.getXysj() %></td>
  </tr>

  <tr>
    <td width=30%>起始地:</td>
    <td width=70%><%=yunshu.getQsd() %></td>
  </tr>

  <tr>
    <td width=30%>目的地:</td>
    <td width=70%><%=yunshu.getMudidi() %></td>
  </tr>

  <tr>
    <td width=30%>公里数:</td>
    <td width=70%><%=yunshu.getGonglishu() %></td>
  </tr>

  <tr>
    <td width=30%>运输备注:</td>
    <td width=70%><%=yunshu.getYunshuMemo() %></td>
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
