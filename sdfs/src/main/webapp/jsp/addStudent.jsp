<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
 
<head>

<title></title>
</head>

<!--[if IE]><style type="text/css"> body {word-wrap: break-word;}</style><![endif]-->

<body>
<div class="page-container">



<div class="main">
 
<div class="main-navigation">

<div class="round-border-topright"></div>
<h1 class="first">添加员工<br /></h1>
<p>填写员工信息保存到数据库<br /></p>
 


 
</div>
 
<div class="main-content">
 
<h1 class="pagetitle">添加员工</h1>

 

 
<div class="column1-unit">
<div class="contactform">
            <form method="post" action="${pageContext.request.contextPath}/addemp.do">
              <fieldset><legend>&nbsp;基本信息&nbsp;</legend>
                <p><label for="id" class="left">ID</label>
                   <input type="text" name="id" id="id" class="field" value="" tabindex="1" /></p>
                
                <p><label for="name" class="left">姓名</label>
                   <input type="text" name="name" id="name" class="field" value="" tabindex="1" /></p>
                
                <p><label for="birthday" class="left">出生年月</label>
                   <input type="date" name="birthday" id="birthday" class="field" value="" tabindex="1" /></p>
                   
                   <p><label for="description" class="left">备注</label>
                   <input type="text" name="description" id="description" class="field" value="" tabindex="1" /></p>
                  
                   <p><label for="avgscore" class="left">平均分</label>
                   <input type="text" name="avgscore" id="avgscore" class="field" value="" tabindex="1" /></p>
                   
                  
                   
			
			
                <p><input type="submit" name="submit" id="submit" class="button" value="添加" tabindex="6" /></p>
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



