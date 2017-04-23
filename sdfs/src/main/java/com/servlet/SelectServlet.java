package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domain.Students;
import com.service.StudentService;
import com.service.Impl.StudentServiceImpl;
import com.utils.Pagination;



/**
 * Servlet implementation class SelectServlet
 */
@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {
	
	private StudentService service = new StudentServiceImpl();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//分页
		int pageSize=4;//每一页的数据量
		int pageNo = -1;
		String pageNoText = request.getParameter("pageNo");//pageNo:当前页数
		if (pageNoText == null) {
			pageNo=1;   //总页数为空  默认为第一页
		} else {
			//不然就为当前页数
			pageNo = Integer.parseInt(pageNoText);
		}
		
		/*if(pageNo >1){
			pageNo = (pageNo-1)*3;
			pageSize = pageNo +2;
		}*/
		
		/*pageSize = pageNo;*/
		
		
		System.out.println("servlet: " + "pageNo: " + pageNo + " pageSize: " + pageSize );
		
		Pagination<Students>studentPage = service.selectStudents(pageNo, pageSize);
		
		request.setAttribute("studentPage", studentPage);
		
		request.getRequestDispatcher("studentList.jsp").forward(request, response);

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		doGet(request, response);
	}

}
