package com.service;

import com.domain.Students;
import com.utils.Pagination;

public interface  StudentService {

	//查询
	public abstract Pagination<Students> selectStudents(int pageNo, int pageSize);
	
	//查询ByID
	public abstract Students selectById(int id);
	//增加
	public abstract boolean addStudents(Students students);
	
	//修改
	public abstract void updateStudents(Students students);
	
	//删除
	public abstract void deleteStudents(String id);
	
	//分页
	
	
}
