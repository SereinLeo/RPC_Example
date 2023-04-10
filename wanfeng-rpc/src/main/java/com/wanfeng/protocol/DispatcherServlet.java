package com.wanfeng.protocol;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Date: 2023-04-10 0010 18:16
 * Author: WanFeng
 * Description:
 */
//注入Tomcat中的servlet
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//根据不同的请求交由用不同的handler对象处理
		HttpServerHandler httpServerHandler = new HttpServerHandler();
		httpServerHandler.handler(req, resp);
	}
}
