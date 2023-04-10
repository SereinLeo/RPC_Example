package com.wanfeng;

/**
 * Date: 2023-04-10 0010 16:23
 * Author: WanFeng
 * Description:
 */
public class HelloServiceImpl implements HelloService{
	@Override
	public String sayHello(String name) {
		return "hello, "+name;
	}
}
