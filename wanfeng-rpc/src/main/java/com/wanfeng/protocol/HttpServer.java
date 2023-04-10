package com.wanfeng.protocol;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

/**
 * Date: 2023-04-10 0010 18:03
 * Author: WanFeng
 * Description:
 */

//用于开启网络服务如Tomcat
public class HttpServer {
	public void start(String hostname, Integer port){
		//读取用户配置 如server.name=netty

		//开启Tomcat
		Tomcat tomcat = new Tomcat();

		Server server = tomcat.getServer();
		Service service = server.findService("Tomcat");

		Connector connector = new Connector();
		connector.setPort(port);

		Engine engine = new StandardEngine();
		engine.setDefaultHost(hostname);

		Host host = new StandardHost();
		host.setName(hostname);

		String contextPath = "";
		StandardContext context = new StandardContext();
		context.setPath(contextPath);
		context.addLifecycleListener(new Tomcat.FixContextListener());

		host.addChild(context);
		engine.addChild(host);

		service.setContainer(engine);
		service.addConnector(connector);

		//向tomcat中添加处理请求的servlet
		tomcat.addServlet(contextPath, "dispatcher", new DispatcherServlet());
		context.addServletMappingDecoded("/*", "dispatcher");

		try {
			tomcat.start();
			tomcat.getServer().await();
		} catch (LifecycleException e) {
			e.printStackTrace();
		}
	}
}
