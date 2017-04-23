package com.servlet;

import java.io.IOException;
import java.util.Date;

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
 * Servlet implementation class UpdateServlet
 */
@WebServlet("/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	
	private StudentService service = new StudentServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");//post 乱码问题
		response.setContentType("text/html;charset=utf-8"); 
		String errormessage = null;
		
		String  ids = request.getParameter("id");
		
		String avgscore = request.getParameter("avgscore");
		
		//id
		boolean idNumber = ids.matches("[0-9]+");
		//avg
		boolean avg = avgscore.matches("[0-9]+");
		System.out.println("update---idNumber:" +idNumber+"avg:" + avg);
		if (idNumber == false || avg == false || ids.isEmpty()) {
			errormessage = ids;
			
			request.setAttribute("IDMessage", errormessage);
			request.getRequestDispatcher("error.jsp").forward(request, response);
		} else {
			
			int id = Integer.parseInt(ids.trim()) ;
			
			String name = request.getParameter("name");
			
			String birthday = request.getParameter("birthday");
			//格式化时间  string --> date
			//Date birth =  DateUtil.getDate2(birthday);
			
			
			String description = request.getParameter("description");
			
		
			Students students = new Students(id, name, birthday, description, avgscore);
		
			service.updateStudents(students);
			
			System.out.println("studnetsName : " + students.getName());
			
			request.getRequestDispatcher("index.jsp").forward(request, response);

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
