package com.wanfeng;

import com.wanfeng.protocol.HttpServer;
import com.wanfeng.register.LocalRegister;

/**
 * Date: 2023-04-10 0010 18:01
 * Author: WanFeng
 * Description:
 */
public class Provide {
	public static void main(String[] args) {
		//注册（存放）接口名和实现类的映射
		LocalRegister.register(HelloService.class.getName(),"1.0", HelloServiceImpl.class);
		LocalRegister.register(HelloService.class.getName(),"2.0", HelloServiceImpl2.class);


		//开启Http服务
		HttpServer httpServer = new HttpServer();
		httpServer.start("localhost", 8080);
	}
}
