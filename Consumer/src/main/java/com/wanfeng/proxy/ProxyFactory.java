package com.wanfeng.proxy;

import com.wanfeng.HelloService;
import com.wanfeng.common.Invocation;
import com.wanfeng.protocol.HttpClient;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Date: 2023-04-10 0010 19:46
 * Author: WanFeng
 * Description:
 */

//获取代理对象
public class ProxyFactory {
	//通过接口类型返回代理类对象
	public static <T> T  getProxy(Class interfaceClass){
		//获取代理对象
		Object proxyInstance = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, new InvocationHandler() {
			//根据调用的信息，实现代理对象的调用逻辑
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				//封装调用信息
				Invocation invocation = new Invocation(
						interfaceClass.getName(),
						method.getName(),
						method.getParameterTypes(),
						args
				);

				//发送请求
				HttpClient client = new HttpClient();
				String result = client.send("localhost", 8080, invocation);

				return result;
			}
		});

		return (T) proxyInstance;
	}
}
