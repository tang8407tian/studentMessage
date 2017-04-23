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
	
	
	
	//查询分页排序
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
		//使用sortedset排序、分页   hash保存对象内容
		//将一个或多个 member 元素及其 score 值加入到有序集 key 当中。
		// ZADD key score member [[score member] [score member] ...]
		
		/**
		 * zset：分页、排序
		 * ZADD key score member [[score,member]…]根据score值的大小对元素进行排序
		 * ZREVRANGE key start stop返回指定区间内的成员，可以用来做分页
		 * ZREM key member根据key移除指定的成员
		 */
		List< Students > studentslist = new ArrayList<Students>();

		//当前页码
		long  startpage = Long.parseLong(pageNo);
		
		//每一页的起始编号
		long start = startpage;
		//每一页的结束编号
		long end = Long.parseLong(pageSize) -1;
		
		//每一页之间的间隔数 ：每页显示多少数据
		int number= 4;
		/*
		//当不位首页时
		if(startpage >= 1){
			
			 start = startpage * number;
			 end = start + number -1;
		}
		//当最后一页时
		if(end >= jedis.zcard("stu")){
			end = -1;
		}*/
		
		//当为第一页时
		if (startpage ==  1) {
			start = 0;
			end = start + number -1;
		}
		
		//页数大于1时
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
			//格式化时间  string-->date
			//Date birth = DateUtil.getDate2(birthday);
			Students seStudent = new Students(ids, name, birthday, description, avgscore);
			
/*			System.out.println(id +"  "+name);
*/			
			studentslist.add(seStudent);
		}
		
		//统计总数
		long total = jedis.zcard("stu");
		int totalRecord =(int)total;
		
		
		int starts = Integer.parseInt(pageNo);
		int ends = Integer.parseInt(pageSize);
		//
		Pagination<Students> studnetPage = new Pagination<Students>(studentslist, totalRecord, starts, ends);
		
		
		return studnetPage;
		
		
		
		
	}

	
	//添加
	@Override
	public boolean addStudent(Students students) {
		/**
		 * sortedset:ZADD key      score      member [[score,member]…]
		 * 			zadd  stu  creatTime  学生ID
		 * hash存储方式：  HMSET KEY_NAME FIELD1 VALUE1 ...FIELDN VALUEN 
		 *             hmset   id     name   name值   birthday birthday值
		 * hash获取：        HGET KEY_NAME FIELD_NAME 
		 * 如获取name值    hget  id号（id）     属性名(name)   
		 */
		//获取系统当前时间
		Long creatTime = System.currentTimeMillis();
		
		 /*Date Time = new Date();
		 String creatTime = new String();*/
		
		 String  id = students.getId() +"";
		 
		 
		 String name = "name";//姓名
		 String birthday = "birthday";//出生年月
		 String description;//备注
		 String  avgscore;//平均分
		 
		 
		//格式化时间
		 //String birth =DateUtil.dateToBarString(students.getBirthday());
		 //System.out.println("date: " + birth);
		 
		 
		 //判断ID是否存在
		 if (jedis.exists(id)) {
				return false;
			} else {
				HashMap<String,String> hashMap = new HashMap<String,String>();
				 hashMap.put("name", students.getName());
				 hashMap.put("birthday", students.getBirthday() );
				 hashMap.put("description", students.getDescription());
				 hashMap.put("avgscore", students.getAvgscore()+ "");
				
				 
				 System.out.println("daoAdd : " +students.getBirthday() );
				 
				 //把学生ID储存到zset中根据创建时间排序
				 jedis.zadd("stu" , creatTime, id);
				 
				 //根据ID把学生信息添加到hash中
				jedis.hmset(id, hashMap);
				
				return true;
			}
		 
		
		
	
	
	}
	
	
	//修改
	@Override
	public void updateStudent(Students students) {
		int id = students.getId();
		String ids = id + "";
		String name = students.getName();
		String   birthday = students.getBirthday();
		String description = students.getDescription();
		String avgscore = students.getAvgscore();
		//格式化时间  date--》string
		//String birth =DateUtil.dateToBarString(birthday);
		jedis.hset(ids, "id", ids);
		jedis.hset(ids, "name", name);
		jedis.hset(ids, "birthday",birthday);
		jedis.hset(ids, "description", description);
		jedis.hset(ids, "avgscore", avgscore);
		
		//System.out.println(	"***********updateDao");
		
	}
	
	
	//删除
	@Override
	public void deleteStudent(int id) {

		String ids = id + "";
		//根据ID删除zset里面内容    zerm key member
		jedis.zrem("stu", ids);	
		//根据ID删除hash里面内容
		jedis.del(ids);
	}
	
	
	//分页查询(作废)
	@Override
	public Students select(long start, long end) {
		/**
		 * zset：分页、排序
		 * ZADD key score member [[score,member]…]根据score值的大小对元素进行排序
		 * ZREVRANGE key start stop返回指定区间内的成员，可以用来做分页
		 * ZREM key member根据key移除指定的成员
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

	//根据ID查询数据
	@Override
	public Students selectById(int id) {
		//通过传入的ID在hash里查询数据
		
			String ids = id+"";
			Students Student=null;
			if( !ids.isEmpty()){
				//获取数据
				String name = jedis.hget( ids, "name");
				String birthday  = jedis.hget(ids , "birthday");
				String description = jedis.hget(ids, "description");
				String avgscore = jedis.hget(ids, "avgscore");
				
				int ido = Integer.parseInt(ids);
				//格式化时间  string-->date
				//Date birth = DateUtil.getDate2(birthday);
				 Student = new Students(ido, name, birthday, description, avgscore);
				
			
				 System.out.println( "name" + name + "  studnetName : " +Student.getName() );
		}
		
			
		return Student ;
	}


	

	

}
