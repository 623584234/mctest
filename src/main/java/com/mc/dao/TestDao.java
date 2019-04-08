package com.mc.dao;

public interface TestDao {

	void test(String a);
	
	default void test2() {
		System.out.println("test2");
	}
	default void test3() {
		System.out.println("test2");
	}
}
