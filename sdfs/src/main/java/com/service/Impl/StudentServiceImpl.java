package com.service.Impl;

import com.dao.StudentDao;
import com.dao.impl.StudentDaoImpl;
import com.domain.Students;
import com.service.StudentService;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.utils.Pagination;

public class StudentServiceImpl  implements StudentService{
	
	
	private  StudentDao students = new StudentDaoImpl();
	//��ѯ
	@Override
	public Pagination<Students> selectStudents(int pageNo, int pageSize) {
		String pageNub = pageNo + "";
		String pagesize = pageSize + "";
		
		Pagination<Students> stu = null;
		
		stu = students.selectStudentMessage(pageNub, pagesize);
		
		System.out.println();
		return stu;
	}
	
	
	//���
	@Override
	public boolean addStudents(Students student) {
		
		return  students.addStudent(student);
	}

	//�޸�
	@Override
	public void updateStudents(Students student) {
		students.updateStudent(student);
	}
	
	
	//ɾ��
	@Override
	public void deleteStudents(String  id) {
		int ids = Integer.parseInt(id); 
		students.deleteStudent(ids);
	}


	@Override
	public Students selectById(int id) {
		
		return students.selectById(id);
		 
	}

}
