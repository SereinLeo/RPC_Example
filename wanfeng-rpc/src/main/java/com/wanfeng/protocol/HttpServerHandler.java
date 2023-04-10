package com.wanfeng.protocol;

import com.wanfeng.common.Invocation;
import com.wanfeng.register.LocalRegister;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Date: 2023-04-10 0010 18:17
 * Author: WanFeng
 * Description:
 */
//网络请求处理
public class HttpServerHandler {
	//处理请求
	public void handler(HttpServletRequest req, HttpServletResponse resp){
		//获取请求的接口名，方法，参数等
		try {
			//读取请求中的对象并强转成invocation
			Invocation invocation =
					(Invocation) new ObjectInputStream(req.getInputStream()).readObject();

			//获取调用信息，根据本地注册的map获取对应的实现类Class，以及实现类中的方法
			String interfaceName = invocation.getInterfaceName();
			String interfaceVersion = invocation.getVersion();
			if(interfaceVersion==null || interfaceVersion.equals(""))
				interfaceVersion = "1.0";
			Class implclass = LocalRegister.get(interfaceName, interfaceVersion);
			Method method = implclass.getMethod(invocation.getMethodName(), invocation.getParamTypes());

			//利用反射执行方法
			String result = (String) method.invoke(implclass.getDeclaredConstructor().newInstance(), invocation.getParams());

			//将结果写入response的输出流
			IOUtils.write(result, resp.getOutputStream());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
