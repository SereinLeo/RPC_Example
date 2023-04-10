package com.wanfeng.register;

import java.util.HashMap;
import java.util.Map;

/**
 * Date: 2023-04-10 0010 18:28
 * Author: WanFeng
 * Description:
 */
//存放接口名和实现类映射的本地注册
public class LocalRegister {
	private static Map<String, Class> map = new HashMap<String, Class>();


	public static void register(String interfaceName, String version, Class implClass){
		map.put(interfaceName+version, implClass);
	}

	public static Class get(String interfaceName, String version){
		return map.get(interfaceName+version);
	}
}
