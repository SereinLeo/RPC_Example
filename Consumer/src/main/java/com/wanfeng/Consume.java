package com.wanfeng;

import com.wanfeng.proxy.ProxyFactory;

/**
 * Date: 2023-04-10 0010 17:54
 * Author: WanFeng
 * Description:
 */
//调用方
public class Consume {
	public static void main(String[] args) {
		//通过接口获取代理对象
		HelloService service = ProxyFactory.getProxy(HelloService.class);

		//代理对象调用方法
		String result = service.sayHello("wanfeng");

		System.out.println(result);


	}
}
