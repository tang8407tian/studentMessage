package com.service;

import com.domain.Students;
import com.utils.Pagination;

public interface  StudentService {

	//��ѯ
	public abstract Pagination<Students> selectStudents(int pageNo, int pageSize);
	
	//��ѯByID
	public abstract Students selectById(int id);
	//����
	public abstract boolean addStudents(Students students);
	
	//�޸�
	public abstract void updateStudents(Students students);
	
	//ɾ��
	public abstract void deleteStudents(String id);
	
	//��ҳ
	
	
}
