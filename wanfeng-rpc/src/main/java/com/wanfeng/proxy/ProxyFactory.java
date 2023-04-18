package com.wanfeng.proxy;

import com.wanfeng.common.Invocation;
import com.wanfeng.common.URL;
import com.wanfeng.loadbalance.LoadBalance;
import com.wanfeng.protocol.HttpClient;
import com.wanfeng.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

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

				//实现服务mock
				String mock = System.getProperty("mock");
				if( mock != null && mock.startsWith("return:")){
					String result = mock.replace("return:", "");
					return result;
				}


				//封装调用信息
				Invocation invocation = new Invocation(
						interfaceClass.getName(),
						method.getName(),
						method.getParameterTypes(),
						args
				);

				//服务发现：从远程注册中心获取ip地址和端口
				List<URL> urlList = RemoteRegister.get(interfaceClass.getName(), "1.0");

				List<URL> urlCalled = new ArrayList<>();
				//服务调用：发送请求
				String result = null;
				int max = 3;	//最大调用次数
				while (max > 0){
					try {
						//每次服务调用都要进行负载均衡
						urlList.remove(urlCalled);				//从url集合中清除被调用的url
						URL url = LoadBalance.random(urlList);	//选取一个url进行调用
						urlCalled.add(url);						//记录被调用的url

						HttpClient client = new HttpClient();
						result = client.send(url.getHostname(), url.getPort(), invocation);
					}catch (Exception e){
						if(max-- != 0)	continue;
						return "服务调用失败！";
					}
				}



				return result;
			}
		});

		return (T) proxyInstance;
	}
}
