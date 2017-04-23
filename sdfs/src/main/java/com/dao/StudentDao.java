package com.dao;

import com.domain.Students;
import com.utils.Pagination;



public interface StudentDao {
	
	
	//��ҳ��ѯ
	public Students select(long start, long end);
	

	
	//չʾ��Ϣ
	public Pagination<Students> selectStudentMessage(String pageNo, String pageSize);
	
	public Students selectById(int id);
	
	//����
	public boolean addStudent(Students students);
	
	//�޸�
	public void updateStudent(Students students);
	
	//ɾ��
	public void deleteStudent(int id);
	
}
