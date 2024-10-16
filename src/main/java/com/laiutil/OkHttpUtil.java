package com.laiutil;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

public class OkHttpUtil {
	private static OkHttpClient client = OkhttpInit();

	private static synchronized OkHttpClient OkhttpInit() {

		ConnectionPool connectionPool = new ConnectionPool(10, // 最大空闲连接数
				5, // 保持连接的时间
				TimeUnit.MINUTES);
		OkHttpClient client = new OkHttpClient.Builder().connectionPool(connectionPool)
				.connectTimeout(10, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
				.writeTimeout(30, TimeUnit.SECONDS).build();
		return client;
	}
	public static synchronized OkHttpClient getClient() {
		return client;
	}
}
