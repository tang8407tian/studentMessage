package com.dao;

import com.domain.Students;
import com.utils.Pagination;



public interface StudentDao {
	
	
	//分页查询
	public Students select(long start, long end);
	

	
	//展示信息
	public Pagination<Students> selectStudentMessage(String pageNo, String pageSize);
	
	public Students selectById(int id);
	
	//增加
	public boolean addStudent(Students students);
	
	//修改
	public void updateStudent(Students students);
	
	//删除
	public void deleteStudent(int id);
	
}
