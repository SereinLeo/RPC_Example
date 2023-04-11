package com.wanfeng.register;

import com.wanfeng.common.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date: 2023-04-10 0010 22:09
 * Author: WanFeng
 * Description:
 */
//远程注册中心
public class RemoteRegister {
	//一个接口对应多个服务的URL
	private static Map<String, List<URL>> map = new HashMap<String, List<URL>>();


	public static void register(String interfaceName, String version, URL url){
		List<URL> urlList = map.get(interfaceName + version);
		if(urlList == null){
			urlList = new ArrayList<>();
		}
		urlList.add(url);

		map.put(interfaceName+version, urlList);

		//将map存入文件
		saveFile();
	}

	public static List<URL> get(String interfaceName, String version){
		//从文件中读取map
		map = getFile();
		return map.get(interfaceName+version);
	}

	private static void saveFile(){
		try {
			//将map写入文件（使用ObjectOutputStream）相对路径从项目根目录 / 开始（RPC）
			FileOutputStream fileOutputStream = new FileOutputStream("wanfeng-rpc/src/main/resources/temp.txt");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(map);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Map<String, List<URL>> getFile(){
		try {
			FileInputStream fileInputStream = new FileInputStream("wanfeng-rpc/src/main/resources/temp.txt");
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			return (Map<String, List<URL>>) objectInputStream.readObject();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
