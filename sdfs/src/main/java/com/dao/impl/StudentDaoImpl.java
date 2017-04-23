package com.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.dao.StudentDao;
import com.domain.Students;
import com.redis.Redis;
import com.sun.xml.internal.bind.v2.model.core.ID;
import com.sun.xml.internal.bind.v2.runtime.Name;
import com.utils.DateUtil;
import com.utils.Pagination;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

public class StudentDaoImpl implements StudentDao {
	static Redis redis = new Redis();
	static Jedis jedis = redis.getJedis();
	
	
	
	//��ѯ��ҳ����
	@Override
	public Pagination<Students> selectStudentMessage(String  pageNo, String  pageSize) {
		
		System.out.println("pageNo:" + pageNo + "pageSize: " + pageSize);
		
		/*
		jedis.hgetAll(id);
		Set<String > keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			
			String key = it.next();
			if(jedis.type(key).equals("hash")){
				
				String name = jedis.hget( key, "name");
				String birthday  = jedis.hget(key + "", "birthday");
				String description = jedis.hget(key + "", "description");
				String avgscore = jedis.hget(key + "", "avgscore");
				
				int id = Integer.parseInt(key);
				Students seStudent = new Students(id, name, birthday, description, avgscore);
				
				List< Students > students = new ArrayList<Students>();
				students.add(seStudent);
			}
		}
		*/
		//ʹ��sortedset���򡢷�ҳ   hash�����������
		//��һ������ member Ԫ�ؼ��� score ֵ���뵽���� key ���С�
		// ZADD key score member [[score member] [score member] ...]
		
		/**
		 * zset����ҳ������
		 * ZADD key score member [[score,member]��]����scoreֵ�Ĵ�С��Ԫ�ؽ�������
		 * ZREVRANGE key start stop����ָ�������ڵĳ�Ա��������������ҳ
		 * ZREM key member����key�Ƴ�ָ���ĳ�Ա
		 */
		List< Students > studentslist = new ArrayList<Students>();

		//��ǰҳ��
		long  startpage = Long.parseLong(pageNo);
		
		//ÿһҳ����ʼ���
		long start = startpage;
		//ÿһҳ�Ľ������
		long end = Long.parseLong(pageSize) -1;
		
		//ÿһҳ֮��ļ���� ��ÿҳ��ʾ��������
		int number= 4;
		/*
		//����λ��ҳʱ
		if(startpage >= 1){
			
			 start = startpage * number;
			 end = start + number -1;
		}
		//�����һҳʱ
		if(end >= jedis.zcard("stu")){
			end = -1;
		}*/
		
		//��Ϊ��һҳʱ
		if (startpage ==  1) {
			start = 0;
			end = start + number -1;
		}
		
		//ҳ������1ʱ
		if(startpage >1){
			start = (startpage-1)*number;
			end = start + number -1 ;
		}
		//System.out.println("dao: "+ "start: " + start + " end: " + end);

		Set<String> set = jedis.zrange("stu", start, end);
		
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String id = it.next();
			String name = jedis.hget( id, "name");
			String birthday  = jedis.hget(id , "birthday");
			String description = jedis.hget(id, "description");
			String avgscore = jedis.hget(id, "avgscore");
			
			int ids = Integer.parseInt(id);
			//��ʽ��ʱ��  string-->date
			//Date birth = DateUtil.getDate2(birthday);
			Students seStudent = new Students(ids, name, birthday, description, avgscore);
			
/*			System.out.println(id +"  "+name);
*/			
			studentslist.add(seStudent);
		}
		
		//ͳ������
		long total = jedis.zcard("stu");
		int totalRecord =(int)total;
		
		
		int starts = Integer.parseInt(pageNo);
		int ends = Integer.parseInt(pageSize);
		//
		Pagination<Students> studnetPage = new Pagination<Students>(studentslist, totalRecord, starts, ends);
		
		
		return studnetPage;
		
		
		
		
	}

	
	//���
	@Override
	public boolean addStudent(Students students) {
		/**
		 * sortedset:ZADD key      score      member [[score,member]��]
		 * 			zadd  stu  creatTime  ѧ��ID
		 * hash�洢��ʽ��  HMSET KEY_NAME FIELD1 VALUE1 ...FIELDN VALUEN 
		 *             hmset   id     name   nameֵ   birthday birthdayֵ
		 * hash��ȡ��        HGET KEY_NAME FIELD_NAME 
		 * ���ȡnameֵ    hget  id�ţ�id��     ������(name)   
		 */
		//��ȡϵͳ��ǰʱ��
		Long creatTime = System.currentTimeMillis();
		
		 /*Date Time = new Date();
		 String creatTime = new String();*/
		
		 String  id = students.getId() +"";
		 
		 
		 String name = "name";//����
		 String birthday = "birthday";//��������
		 String description;//��ע
		 String  avgscore;//ƽ����
		 
		 
		//��ʽ��ʱ��
		 //String birth =DateUtil.dateToBarString(students.getBirthday());
		 //System.out.println("date: " + birth);
		 
		 
		 //�ж�ID�Ƿ����
		 if (jedis.exists(id)) {
				return false;
			} else {
				HashMap<String,String> hashMap = new HashMap<String,String>();
				 hashMap.put("name", students.getName());
				 hashMap.put("birthday", students.getBirthday() );
				 hashMap.put("description", students.getDescription());
				 hashMap.put("avgscore", students.getAvgscore()+ "");
				
				 
				 System.out.println("daoAdd : " +students.getBirthday() );
				 
				 //��ѧ��ID���浽zset�и��ݴ���ʱ������
				 jedis.zadd("stu" , creatTime, id);
				 
				 //����ID��ѧ����Ϣ��ӵ�hash��
				jedis.hmset(id, hashMap);
				
				return true;
			}
		 
		
		
	
	
	}
	
	
	//�޸�
	@Override
	public void updateStudent(Students students) {
		int id = students.getId();
		String ids = id + "";
		String name = students.getName();
		String   birthday = students.getBirthday();
		String description = students.getDescription();
		String avgscore = students.getAvgscore();
		//��ʽ��ʱ��  date--��string
		//String birth =DateUtil.dateToBarString(birthday);
		jedis.hset(ids, "id", ids);
		jedis.hset(ids, "name", name);
		jedis.hset(ids, "birthday",birthday);
		jedis.hset(ids, "description", description);
		jedis.hset(ids, "avgscore", avgscore);
		
		//System.out.println(	"***********updateDao");
		
	}
	
	
	//ɾ��
	@Override
	public void deleteStudent(int id) {

		String ids = id + "";
		//����IDɾ��zset��������    zerm key member
		jedis.zrem("stu", ids);	
		//����IDɾ��hash��������
		jedis.del(ids);
	}
	
	
	//��ҳ��ѯ(����)
	@Override
	public Students select(long start, long end) {
		/**
		 * zset����ҳ������
		 * ZADD key score member [[score,member]��]����scoreֵ�Ĵ�С��Ԫ�ؽ�������
		 * ZREVRANGE key start stop����ָ�������ڵĳ�Ա��������������ҳ
		 * ZREM key member����key�Ƴ�ָ���ĳ�Ա
		 */
		
		Set<String> set = jedis.zrange("stu", start, end);
		
		Iterator<String> it = set.iterator();
		while(it.hasNext()){
			String id = it.next();
			String name = jedis.hget( id, "name");
			String birthday  = jedis.hget(id , "birthday");
			String description = jedis.hget(id, "description");
			String avgscore = jedis.hget(id, "avgscore");
			
			int ids = Integer.parseInt(id);
/*			Students seStudent = new Students(ids, name, birthday, description, avgscore);
*/			
			List< Students > students = new ArrayList<Students>();
			/*students.add(seStudent);*/
		}
		
		
		
		
		
		return null;
	}

	//����ID��ѯ����
	@Override
	public Students selectById(int id) {
		//ͨ�������ID��hash���ѯ����
		
			String ids = id+"";
			Students Student=null;
			if( !ids.isEmpty()){
				//��ȡ����
				String name = jedis.hget( ids, "name");
				String birthday  = jedis.hget(ids , "birthday");
				String description = jedis.hget(ids, "description");
				String avgscore = jedis.hget(ids, "avgscore");
				
				int ido = Integer.parseInt(ids);
				//��ʽ��ʱ��  string-->date
				//Date birth = DateUtil.getDate2(birthday);
				 Student = new Students(ido, name, birthday, description, avgscore);
				
			
				 System.out.println( "name" + name + "  studnetName : " +Student.getName() );
		}
		
			
		return Student ;
	}


	

	

}
