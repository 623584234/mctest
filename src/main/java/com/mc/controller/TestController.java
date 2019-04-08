package com.mc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mc.dao.TestDao;

/*
 * 测试controller
 */

@Controller
@RequestMapping("/")
public class TestController  {
	@GetMapping("/testInterceptor1")
	public void testInterceptor1() {
		System.out.println("----------test1");
	}
	
	@GetMapping("/testInterceptor2")
	public void testInterceptor2() {
		System.out.println("----------test2");
	}
	public static void main(String[] args) {
//		Object a[]= {1,2,3,"4",1};
//		List<?> list =Arrays.asLsist(a);
		
		//重写函数式接口
//		TestDao testDao =(a)->{
//			Object ob[]= {1,2,3,"4",1};
//			List<?> list =Arrays.asList(ob);
//			list.forEach(System.out::println);
//		};
//		
//		testDao.test2();
//		testDao.test("2");
		
		Map<String,Object> map = new LinkedHashMap<>();
		map.put("5", "1");
		map.put("2", "2");
		map.put("3", "3");
		map.put("a4", "4");
		map.put("a4", "4");
	
		List<Entry<String, Object>> list1 = new ArrayList<>(map.entrySet());
//		list1.forEach(p->System.out.println(p.getKey()+":"+p.getValue()));	
//		两句结合= new ArrayList<>(map.entrySet()).forEach((k)->System.out.println(k.getKey()+":"+k.getValue()));
		
		//慎用Arrays.asList.该方法返回定长的 List，不支持 add 和 remove 操作,输出结果=System.out.println(list1);
//		Arrays.asList(map.entrySet()).forEach(System.out::println);
		
		//例子：过滤list中的map中的数据
//		Predicate<Entry<String, Object>> strFilter = (p)->!p.getKey().equals("2");
//		list1.stream()
//		.filter(strFilter)
//		.filter(p->!p.getValue().equals("4"))
//		.forEach(System.out::println);
		
		
		//例子：过滤去重赋值list的值
//		List<String> list =new ArrayList<String>(Arrays.asList("c","b","a","c"));
//		Predicate<String> preFilter1 = p->!p.equals("a");
//		Predicate<String> preFilter2 = p->!p.equals("b");
//		Predicate<String> preFilter3 = p->p.equals("c");
//		//or ,and ,negate:   1.and：相当于inner join  2.or：相当于 full join 3.negate：取反
//		list = list.stream()//结果:a,b,c,c                 //a,b						//a
//			.filter(preFilter1.or(preFilter2).and(preFilter3.negate()).and(Predicate.isEqual("a")))
//			.distinct()
//			.collect(Collectors.toList());
//		//假设没有list= ，就可直接.forEach
//		list.forEach(System.out::println);
		
		
		//HasSet去重无序,TreeSet去重按a-z或0-9自然排序,LinkedHashSet去重有序
		Set<String> set = new HashSet<>();
		set.add("哈哈");
		set.add("表演");
		set.add("得到");
		set.add("吃饭0");
		set.add("吃饭2");
		set.add("吃饭9");
		

//		list1.forEach(k->System.out.println(k.getKey()+":"+k.getValue()));
//		list.forEach(System.out::println);
		
		
/**
* -------------------------------------- Map --------------------------------------
*/			

//		map.forEach((k,v)->System.out.println(k+":"+v+"---"));
		
		//用for循环方式获取map的值（没有key）
//		for(Object value :map.values()) {
//			System.out.println(value);
//		}
		
		//用keySet获取map键值对（较慢,内部循环了两次:一次转为Iterator类型，一次HashMap中取出key对应的value值）
		//entrySet只遍历了一次，他将key和value全部放入entry中。
//		for(String key :map.keySet()) {
//			System.out.println(key+":"+map.get(key));
//		}
		
		//用for循环获取map键值对
//		for(Entry<String, Object> en:map.entrySet()) {
//			System.out.println(en.getKey()+":"+en.getValue());
//		}
		
		//用lambda表达式获取map键值对1
//		map.forEach((k,v)->System.out.println(k+":"+v));
		
		//用lambda表达式获取map键值对2
//		Set<Entry<String, Object>> en =map.entrySet();
//		en.forEach(e->System.out.println(e.getKey()+":"+e.getValue()));
	
		/*	返回类型：
			values()返回的是V值集合，是一个list集合对象；
			keySet()返回的是K值集合，是一个Set集合对象；
			entrySet()返回的是K-V值组合集合。
		*/
/**
 * -------------------------------------- list --------------------------------------
 */
		
		//用lambda表达式获取list的值
//		list1.forEach(System.out::println);
		
		//用lambda表达式获取list中的map键值对
//		list1.forEach(e->System.out.println(e.getKey()+":"+e.getValue()));
		
//		for (Entry<String, Object> entry : list1) {
//			System.out.println(entry.getKey()+":"+entry.getValue());
//		}
		
/**
* -------------------------------------- Set --------------------------------------
*/
				
		//用lambda表达式获取set键值对
//		set.forEach(System.out::println);
				
		//用for循环获取set键值对
//		for(String setKey:set) {
//			System.out.println(setKey);
//		}
		
/*		
  Set和Map的联系:
 		HashSet构造函数就是new Map.
		源码中Set的add方法其实就是在Map中添加key，使用一个全局Object对象作为value.
		因此:在Map中,所有的key就是一个Set集合.	
*/
		
/*
  print,printf,println区别:
		int a =1;
		System.out.println(a);
		System.out.print(a+"\n");
		System.out.printf("%d",a);
*/
		
/*	
	函数式接口：Java8引用lambda表达式去重写接口。(日常没什么必要用)
		前提：接口都有@FunctionalInterface,该接口只能有一个抽象方法（即默认方法），可有多个default方法，否则报错。
		重写例子：
			dao层：
				public interface TestDao {
					void test(String a);
				}
			service层:
				TestDao testDao =(a)->{System.out.println(a+",")};
			controller层:	
				@Autowire
				TestDao testDao;
				testDao.test("aaaaa");
*/		
		
	}
}
