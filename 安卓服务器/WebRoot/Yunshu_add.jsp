<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
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
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加运输单</TITLE> 
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
    var yshw = document.getElementById("yunshu.yshw").value;
    if(yshw=="") {
        alert('请输入运输货物!');
        return false;
    }
    var zl = document.getElementById("yunshu.zl").value;
    if(zl=="") {
        alert('请输入重量(吨)!');
        return false;
    }
    var xysj = document.getElementById("yunshu.xysj").value;
    if(xysj=="") {
        alert('请输入需要时间!');
        return false;
    }
    var qsd = document.getElementById("yunshu.qsd").value;
    if(qsd=="") {
        alert('请输入起始地!');
        return false;
    }
    var mudidi = document.getElementById("yunshu.mudidi").value;
    if(mudidi=="") {
        alert('请输入目的地!');
        return false;
    }
    var gonglishu = document.getElementById("yunshu.gonglishu").value;
    if(gonglishu=="") {
        alert('请输入公里数!');
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
    <TD align="left" vAlign=top >
    <s:form action="Yunshu/Yunshu_AddYunshu.action" method="post" id="yunshuAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>驾号:</td>
    <td width=70%>
      <select name="yunshu.userObj.jiahao">
      <%
        for(UserInfo userInfo:userInfoList) {
      %>
          <option value='<%=userInfo.getJiahao() %>'><%=userInfo.getJiahao() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>车牌:</td>
    <td width=70%>
      <select name="yunshu.carObj.carId">
      <%
        for(Car car:carList) {
      %>
          <option value='<%=car.getCarId() %>'><%=car.getCarNo() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>运输货物:</td>
    <td width=70%><input id="yunshu.yshw" name="yunshu.yshw" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>重量(吨):</td>
    <td width=70%><input id="yunshu.zl" name="yunshu.zl" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>需要时间:</td>
    <td width=70%><input id="yunshu.xysj" name="yunshu.xysj" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>起始地:</td>
    <td width=70%><input id="yunshu.qsd" name="yunshu.qsd" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>目的地:</td>
    <td width=70%><input id="yunshu.mudidi" name="yunshu.mudidi" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>公里数:</td>
    <td width=70%><input id="yunshu.gonglishu" name="yunshu.gonglishu" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>运输备注:</td>
    <td width=70%><textarea id="yunshu.yunshuMemo" name="yunshu.yunshuMemo" rows="5" cols="50"></textarea></td>
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
