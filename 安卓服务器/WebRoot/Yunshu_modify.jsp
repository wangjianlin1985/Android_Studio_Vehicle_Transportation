<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Yunshu" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="com.chengxusheji.domain.Car" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    //��ȡ���е�Car��Ϣ
    List<Car> carList = (List<Car>)request.getAttribute("carList");
    Yunshu yunshu = (Yunshu)request.getAttribute("yunshu");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸����䵥</TITLE>
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
/*��֤��*/
function checkForm() {
    var yshw = document.getElementById("yunshu.yshw").value;
    if(yshw=="") {
        alert('�������������!');
        return false;
    }
    var zl = document.getElementById("yunshu.zl").value;
    if(zl=="") {
        alert('����������(��)!');
        return false;
    }
    var xysj = document.getElementById("yunshu.xysj").value;
    if(xysj=="") {
        alert('��������Ҫʱ��!');
        return false;
    }
    var qsd = document.getElementById("yunshu.qsd").value;
    if(qsd=="") {
        alert('��������ʼ��!');
        return false;
    }
    var mudidi = document.getElementById("yunshu.mudidi").value;
    if(mudidi=="") {
        alert('������Ŀ�ĵ�!');
        return false;
    }
    var gonglishu = document.getElementById("yunshu.gonglishu").value;
    if(gonglishu=="") {
        alert('�����빫����!');
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
    <TD align="left" vAlign=top ><s:form action="Yunshu/Yunshu_ModifyYunshu.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼id:</td>
    <td width=70%><input id="yunshu.yunshuId" name="yunshu.yunshuId" type="text" value="<%=yunshu.getYunshuId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�ݺ�:</td>
    <td width=70%>
      <select name="yunshu.userObj.jiahao">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getJiahao().equals(yunshu.getUserObj().getJiahao()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getJiahao() %>' <%=selected %>><%=userInfo.getJiahao() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����:</td>
    <td width=70%>
      <select name="yunshu.carObj.carId">
      <%
        for(Car car:carList) {
          String selected = "";
          if(car.getCarId() == yunshu.getCarObj().getCarId())
            selected = "selected";
      %>
          <option value='<%=car.getCarId() %>' <%=selected %>><%=car.getCarNo() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><input id="yunshu.yshw" name="yunshu.yshw" type="text" size="20" value='<%=yunshu.getYshw() %>'/></td>
  </tr>

  <tr>
    <td width=30%>����(��):</td>
    <td width=70%><input id="yunshu.zl" name="yunshu.zl" type="text" size="20" value='<%=yunshu.getZl() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��Ҫʱ��:</td>
    <td width=70%><input id="yunshu.xysj" name="yunshu.xysj" type="text" size="20" value='<%=yunshu.getXysj() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ʼ��:</td>
    <td width=70%><input id="yunshu.qsd" name="yunshu.qsd" type="text" size="20" value='<%=yunshu.getQsd() %>'/></td>
  </tr>

  <tr>
    <td width=30%>Ŀ�ĵ�:</td>
    <td width=70%><input id="yunshu.mudidi" name="yunshu.mudidi" type="text" size="20" value='<%=yunshu.getMudidi() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="yunshu.gonglishu" name="yunshu.gonglishu" type="text" size="20" value='<%=yunshu.getGonglishu() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���䱸ע:</td>
    <td width=70%><textarea id="yunshu.yunshuMemo" name="yunshu.yunshuMemo" rows=5 cols=50><%=yunshu.getYunshuMemo() %></textarea></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
