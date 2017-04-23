<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	
<!-- //引入标签 -->	
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cc" %>	<!-- //引入标签 -->
	
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">

<head>
<jsp:include page="common.jsp" />
<style type="">
a:HOVER {
	cursor: pointer;
}

</style>



<title></title>
</head>

<!--[if IE]><style type="text/css"> body {word-wrap: break-word;}</style><![endif]-->

<body>
	<div class="page-container">

	<jsp:include page="head.jsp" />
			<div class="header-breadcrumbs">
				<ul>
					<li><a href="#">**</a></li>
					<li><a href="#">学生信息</a></li>
					<li>学生信息</li>
				</ul>


			</div>
		</div>


		<div class="main">

			<div class="main-navigation">

				<div class="round-border-topright"></div>
				<h1 class="first">
					列表说明<br />
				</h1>
				<p>********</p>




			</div>

			<div class="main-content">

				<h1 class="pagetitle">学生信息</h1>




				<div class="column1-unit">
				
				
				<table>
						<tr>
							<td>
							<a href="addStudent.jsp">添加学生</a>
						
							</td>	
						</tr>
					
					</table>
				
					<table>
						<tr>
							<th class="top" scope="col">ID<br /></th>
							<th class="top" scope="col">姓名</th>
							<th class="top" scope="col">出生年月</th>
							<th class="top" scope="col">备注</th>
							<th class="top" scope="col">平均分</th>
							
							<th class="top" scope="col">操作</th>
						</tr>
						
						


						<cc:forEach items="${studentPage.records}" var="student">
							<tr>
								<td>${student.id}</td>
								<th scope="row">${student.name}</th>
								<td>${student.birthday}</td>
								<td>${student.description}<br /></td>
								<td>${student.avgscore}</td>
								<td>
								 <a href="SelectByIdServlet?id=${student.id}">更改</a>/
								<a href="DeleteServlet?id=${student.id}">删除</a> 
								</td>
							</tr>
							
						</cc:forEach>


					</table>
					
					
<%-- 					<p class="caption"><a href="${pageContext.request.contextPath}/Jsp/add.jsp">添加员工</a></p>
 --%>					
					<p class="caption">
					<a href="${pageContext.request.contextPath}/SelectServlet?pageNo=1">首页</a>|
					<cc:if test="${studentPage.firstPage == false}">
					
					<a href="${pageContext.request.contextPath}/SelectServlet?pageNo=${studentPage.prePage}">上一页</a>|
					</cc:if>
					
					
					<%-- <span> 共    ${empPage.totalPage} 页</span> --%>
					<span>  ${studentPage.pageNo}/${studentPage.totalPage} 页</span>
					|
					
					<cc:if test="${studentPage.lastPage == false}">
					
					<a href="${pageContext.request.contextPath}/SelectServlet?pageNo=${studentPage.nextPage}">下一页</a>|
					</cc:if>
					
					
					<a href="${pageContext.request.contextPath}/SelectServlet?pageNo=${studentPage.totalPage}">尾页</a>
					
					</p>
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



