<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<!-- 页面停留刷新 -->
<%-- <meta http-equiv="Refresh" content="1;url=${pageContext.request.contextPath}/listemp.do">
 --%></head>
<body>
------你选的ID是：${IDMessage}
<form id="form1" runat="server"> <h3><div id='div1'></div>  <p> </p>  </h3>  </form> 
	
    
   
</body>
</html>
<script type="text/javascript">  
//设定倒数秒数  
var t = 5;  
//显示倒数秒数  
function showTime(){  
    t -= 1;  
    document.getElementById('div1').innerHTML=  "空-ID或Avg不是数字----还剩："+ t + "返回列表页面";  
    if(t==0){  
        location.href='index.jsp';  
    }  
    //每秒执行一次,showTime()  
    setTimeout("showTime()",1000); 
  
}  
  
  
//执行showTime()  
showTime();  
</script> 