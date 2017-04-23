package com.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Students;
import com.service.StudentService;
import com.service.Impl.StudentServiceImpl;
import com.utils.DateUtil;

/**
 * Servlet implementation class AddServlet
 */
@WebServlet("/AddServlet")
public class AddServlet extends HttpServlet {
	
	private StudentService service = new StudentServiceImpl();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");//post 乱码问题
		response.setContentType("text/html;charset=utf-8"); 
		
		String message = null;
		boolean flage = true;
		
		String  ids = request.getParameter("id");
		
		String avgscore = request.getParameter("avgscore");

		//System.out.println("addservlet dis:111" + ids.matches("[0-9]+"));
		//id
		boolean idNumber = ids.matches("[0-9]+");
		//avg
		boolean avg = avgscore.matches("[0-9]+");
		if (idNumber == false || avg == false || ids.isEmpty()) {
			
			message = "空-ID或Avg不是数字";
			
			request.setAttribute("erroMessage", message);
			request.getRequestDispatcher("addStudent.jsp").forward(request, response);
		}else {
			int id = Integer.parseInt(ids.trim()) ;
			
			String name = request.getParameter("name");
			
			String birthday = request.getParameter("birthday");
			
			Date births = DateUtil.strToDate(birthday, DateUtil.DATE_FORMAT_BAR);
			
			String description = request.getParameter("description");
			
			
			System.out.println("addservlet dis::" + ids.matches("[0-9]+"));

			Students students = new Students(id, name, birthday, description, avgscore);
		
			//System.out.println("addsertvlet: " + birthday);
			flage = service.addStudents(students);
			
			//ids.matches("[0-9]+");
			//判断ID是否重复   切为数字
			if (flage == true  ) {
				
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				
				System.out.println("flage " + flage);
				message = "学生ID已存在";
				
				request.setAttribute("erroMessage", message);
				request.getRequestDispatcher("addStudent.jsp").forward(request, response);
			}
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public boolean isNumber(int id){
		Pattern.compile("[0-9]*");
		
		return true;
	}
}
