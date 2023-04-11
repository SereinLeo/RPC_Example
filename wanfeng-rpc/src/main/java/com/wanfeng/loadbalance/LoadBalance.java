package com.wanfeng.loadbalance;

import com.wanfeng.common.URL;

import java.util.List;
import java.util.Random;

/**
 * Date: 2023-04-10 0010 22:19
 * Author: WanFeng
 * Description:
 */
public class LoadBalance {

	public static URL random(List<URL> urls){
		Random random = new Random();
		int randomIndex = random.nextInt(urls.size());
		return urls.get(randomIndex);
	}
}
