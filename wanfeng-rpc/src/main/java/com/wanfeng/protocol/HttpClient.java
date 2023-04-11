package com.wanfeng.protocol;

import com.wanfeng.common.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Date: 2023-04-10 0010 18:52
 * Author: WanFeng
 * Description:
 */
//http请求客户端，用于Consumer模块发送或请求
public class HttpClient {
	public String send(String hostname, Integer port, Invocation invocation) throws IOException {
		//读取用户配置（http发送方式）
		try {
			//声明url并开启http连接
			URL url = new URL("http", hostname, port, "/");
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

			//配置http连接
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setDoOutput(true);

			//获取到http连接的输入流构造对象输入流用以发送对象
			OutputStream outputStream = httpURLConnection.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(outputStream);

			//将对象写入对象输入流，发送
			oos.writeObject(invocation);
			oos.flush();
			oos.close();

			//等待从http的输入流中获取结果
			InputStream inputStream = httpURLConnection.getInputStream();
			String result = IOUtils.toString(inputStream);

			return result;
		} catch (Exception e) {
			throw e;
		}

	}
}
