package com.wanfeng;

import com.wanfeng.common.URL;
import com.wanfeng.protocol.HttpServer;
import com.wanfeng.register.LocalRegister;
import com.wanfeng.register.RemoteRegister;

/**
 * Date: 2023-04-10 0010 18:01
 * Author: WanFeng
 * Description:
 */
public class Provide {
	public static void main(String[] args) {
		//本地注册（存放）接口名和实现类的映射
		LocalRegister.register(HelloService.class.getName(),"1.0", HelloServiceImpl.class);
		LocalRegister.register(HelloService.class.getName(),"2.0", HelloServiceImpl2.class);

		//注册中心注册
		URL url = new URL("localhost", 8080);
		RemoteRegister .register(HelloService.class.getName(), "1.0", url);

		//开启Http服务
		HttpServer httpServer = new HttpServer();
		httpServer.start(url.getHostname(), url.getPort());
	}
}
