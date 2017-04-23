<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
 
<head>
<jsp:include page="common.jsp" />

<title></title>
</head>

<!--[if IE]><style type="text/css"> body {word-wrap: break-word;}</style><![endif]-->

<body>
<div class="page-container">

	<jsp:include page="head.jsp" />


<div class="main">
 
<div class="main-navigation">

<div class="round-border-topright"></div>
<h1 class="first">添加学员信息<br /></h1>
<p>填写学生信息保存到数据库<br /></p>
 


 
</div>
 
<div class="main-content">
 
<h1 class="pagetitle">添加学员信息</h1>

 

 
<div class="column1-unit">
<div class="contactform">
            <form method="post" action="${pageContext.request.contextPath}/UpdateServlet">
              <fieldset><legend>&nbsp;基本信息&nbsp;</legend>
                <p><label for="id" class="left">ID</label>
                   <input type="text" name="id" id="id" class="field" value="${ ByidStudent.id}" tabindex="1" />
  					  <samp style="color: red ;margin-left: 20px">${errorMessage}</samp>
                   
                   </p>
                
                <p><label for="name" class="left">姓名</label>
                   <input type="text" name="name" id="name" class="field" value="${ ByidStudent.name}" tabindex="1" /></p>
                
                <p><label for="birthday" class="left">出生年月</label>
                   <input type="date" name="birthday" id="birthday" class="field" value="${ ByidStudent.birthday}" tabindex="1" /></p>
                   
                   <p><label for="description" class="left">备注</label>
                   <input type="text" name="description" id="description" class="field" value="${ ByidStudent.description}" tabindex="1" /></p>
                  
                   <p><label for="avgscore" class="left">平均分</label>
                   <input type="text" name="avgscore" id="avgscore" class="field" value="${ ByidStudent.avgscore}" tabindex="1" />
                     <samp style="color: red ;margin-left: 20px">${errorMessage}</samp>
                   
                   </p>
                   
                  
                   
			
			
                <p><input type="submit" name="submit" id="submit" class="button" value="提交" tabindex="6" /></p>
              </fieldset>
              
            </form>
          </div>
</div> 
 
</div>
</div>
 

<div class="footer">
<p>Copyright &copy; 2016 员工管理系统--五组--强强联合 | Go Go Go....</p>
<p class="credits"></p>
</div> 
</div> 
 
</body>
</html>



