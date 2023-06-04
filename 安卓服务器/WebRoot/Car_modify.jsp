<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Car" %>
<%@ page import="com.chengxusheji.domain.CarModel" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的CarModel信息
    List<CarModel> carModelList = (List<CarModel>)request.getAttribute("carModelList");
    Car car = (Car)request.getAttribute("car");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改车辆</TITLE>
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
    var carNo = document.getElementById("car.carNo").value;
    if(carNo=="") {
        alert('请输入车牌!');
        return false;
    }
    var pinpai = document.getElementById("car.pinpai").value;
    if(pinpai=="") {
        alert('请输入品牌!');
        return false;
    }
    var youxing = document.getElementById("car.youxing").value;
    if(youxing=="") {
        alert('请输入油型!');
        return false;
    }
    var haoyouliang = document.getElementById("car.haoyouliang").value;
    if(haoyouliang=="") {
        alert('请输入耗油量!');
        return false;
    }
    var chexian = document.getElementById("car.chexian").value;
    if(chexian=="") {
        alert('请输入车险!');
        return false;
    }
    var zonglicheng = document.getElementById("car.zonglicheng").value;
    if(zonglicheng=="") {
        alert('请输入总里程(公里)!');
        return false;
    }
    var wxcs = document.getElementById("car.wxcs").value;
    if(wxcs=="") {
        alert('请输入维修次数!');
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
    <TD align="left" vAlign=top ><s:form action="Car/Car_ModifyCar.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>车辆id:</td>
    <td width=70%><input id="car.carId" name="car.carId" type="text" value="<%=car.getCarId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>车牌:</td>
    <td width=70%><input id="car.carNo" name="car.carNo" type="text" size="20" value='<%=car.getCarNo() %>'/></td>
  </tr>

  <tr>
    <td width=30%>车型:</td>
    <td width=70%>
      <select name="car.carModel.modelId">
      <%
        for(CarModel carModel:carModelList) {
          String selected = "";
          if(carModel.getModelId() == car.getCarModel().getModelId())
            selected = "selected";
      %>
          <option value='<%=carModel.getModelId() %>' <%=selected %>><%=carModel.getModelName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>品牌:</td>
    <td width=70%><input id="car.pinpai" name="car.pinpai" type="text" size="20" value='<%=car.getPinpai() %>'/></td>
  </tr>

  <tr>
    <td width=30%>油型:</td>
    <td width=70%><input id="car.youxing" name="car.youxing" type="text" size="20" value='<%=car.getYouxing() %>'/></td>
  </tr>

  <tr>
    <td width=30%>耗油量:</td>
    <td width=70%><input id="car.haoyouliang" name="car.haoyouliang" type="text" size="20" value='<%=car.getHaoyouliang() %>'/></td>
  </tr>

  <tr>
    <td width=30%>车险:</td>
    <td width=70%><input id="car.chexian" name="car.chexian" type="text" size="30" value='<%=car.getChexian() %>'/></td>
  </tr>

  <tr>
    <td width=30%>总里程(公里):</td>
    <td width=70%><input id="car.zonglicheng" name="car.zonglicheng" type="text" size="30" value='<%=car.getZonglicheng() %>'/></td>
  </tr>

  <tr>
    <td width=30%>维修次数:</td>
    <td width=70%><input id="car.wxcs" name="car.wxcs" type="text" size="20" value='<%=car.getWxcs() %>'/></td>
  </tr>

  <tr>
    <td width=30%>车辆备注:</td>
    <td width=70%><textarea id="car.carMemo" name="car.carMemo" rows=5 cols=50><%=car.getCarMemo() %></textarea></td>
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
