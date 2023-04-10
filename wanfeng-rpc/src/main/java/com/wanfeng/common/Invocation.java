package com.wanfeng.common;

import java.io.Serializable;

/**
 * Date: 2023-04-10 0010 18:20
 * Author: WanFeng
 * Description:
 */

//调用信息封装
public class Invocation implements Serializable {
	private String interfaceName;	//调用的接口名
	private String version;			//接口版本号
	private String methodName;		//调用的方法名
	private Class[] paramTypes;		//参数类型
	private Object[] params;		//参数


	public Invocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
		this.interfaceName = interfaceName;
		this.methodName = methodName;
		this.paramTypes = paramTypes;
		this.params = params;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Class[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}

	public Object[] getParams() {
		return params;
	}

	public void setParams(Object[] params) {
		this.params = params;
	}
}
