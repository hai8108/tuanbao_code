package com.haitian.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.text.TextUtils;
import android.webkit.URLUtil;

import com.haitian.main.Application;
import com.haitian.main.Configs;

public class HttpUtils {
	private static boolean isHostChange = false;

	/**
	 * server 为Chongis.HostName中server号 params 为构造好的参数
	 * 默认30秒超时，最多主服务器和备用服务器各重试请求两次 网址错误或者网络连接失败，返回null
	 * 
	 * @return
	 */
	public static String getServerString(Context context, int server, String params) {
		String newAurl;
		String rString = null;
		for (int i = 0; i < 4; i++) {// 主服务器和备用服务器各请求两次
			if (isHostChange) {
				newAurl = Configs.HostName2[server] + params;
			} else {
				newAurl = Configs.HostName1[server] + params;
			}
			rString = getServerString(context, newAurl, null, 30 * 1000);
			if (TextUtils.isEmpty(rString)) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				isHostChange = !isHostChange;
			} else {
				break;
			}
		}
		return rString;
	}

	/**
	 * 根据URL获取服务器的返回字符串,并保存在savpath中
	 * 
	 * @author Li Hongjun
	 */
	public static String getServerString(Context context, String aurl, String savePath, int timeOut) {
		String rsString = null;
		URL url = null;
		LogInfo.LogOut("request:  " + aurl);
		if (!URLUtil.isHttpUrl(aurl)) {
			return rsString;
		}
		try {
			url = new URL(aurl);
			String proxyHost = android.net.Proxy.getDefaultHost();
			HttpURLConnection cn = null;
			if (isWifi(context)) {
				cn = (HttpURLConnection) url.openConnection();
			} else {
				if (proxyHost != null) {
					java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP, new InetSocketAddress(
							android.net.Proxy.getDefaultHost(), android.net.Proxy.getDefaultPort()));
					cn = (HttpURLConnection) url.openConnection(p);
				} else {
					cn = (HttpURLConnection) url.openConnection();
				}
			}
			cn.setRequestProperty("Accept", "*/*");
			cn.setRequestProperty("Accept-Language", "zh-cn");
			cn.setRequestProperty("Accept-Encoding", "");
			cn.setConnectTimeout(timeOut);
			cn.setAllowUserInteraction(false);
			// cn.setRequestMethod("POST");
			cn.setDoInput(true);
			cn.setDoOutput(true);
			cn.setReadTimeout(timeOut);
			cn.connect();
			int L = cn.getContentLength();
			LogInfo.LogOut("getContentLength()=" + L);
			BufferedReader reader = new BufferedReader(new InputStreamReader(cn.getInputStream(), "utf-8"));
			String line;
			StringBuffer sBuffer = new StringBuffer();
			while ((line = reader.readLine()) != null) {
				sBuffer.append(line);
			}
			LogInfo.LogOut("returnSize=" + sBuffer.length());
			rsString = sBuffer.length() > 0 ? sBuffer.toString() : null;

			rsString = rsString.replaceAll("\\r", "");// 有的android手机支持此种替换方式
			rsString = rsString.replaceAll("\\\\r", "");// 有的android手机支持此种替换方式
			if (savePath != null) {
				File file = new File(savePath);
				if (!file.exists()) {
					file.createNewFile();
					FileOutputStream stream = new FileOutputStream(file);
					stream.write(rsString.getBytes("utf-8"));
					stream.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rsString = null;
			LogInfo.LogOut("dataException:" + e.getMessage());
		}
		LogInfo.LogOut("return   " + rsString);
		return rsString;
	}

	public static boolean isWifi(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // mobile
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
		if (wifi == State.CONNECTED) {
			LogInfo.LogOut("isWifi=true");
			return true;
		} else {
			LogInfo.LogOut("isWifi=false");
			return false;
		}
	}

	/**
	 * 生成URL
	 * */
	public static URL weaveUrl(String aUrl) {
		URL requestUrl = null;
		try {
			aUrl = aUrl.replaceAll(" ", "");
			if (aUrl.startsWith("http://")) {
				aUrl = aUrl.substring(7);
			}
			requestUrl = new URL("http://" + aUrl);
		} catch (Exception e) {
			e.printStackTrace();
			LogInfo.LogOut("weaveUrl-Exception:" + e.toString());
		}
		return requestUrl;
	}

	/**
	 * 根据URL获取服务器的返回字符串,并保存在savpath中 默认10秒超时时间
	 * 
	 * @author Li Hongjun
	 */
	public static String getServerString(String aurl, String savePath) {
		return getServerString(Application.application, aurl, savePath, 30000);
	}

	/**
	 * 根据URL得到输入流
	 * 
	 * @param urlStr
	 */
	public static InputStream getInputStreamFromUrl(String urlStr) {
		LogInfo.LogOut("request for stream:" + urlStr);
		try {
			// 创建一个URL对象；
			URL url = new URL(urlStr);
			// 创建一个Tcp连接；
			HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
			urlConn.setConnectTimeout(30000);
			urlConn.setReadTimeout(30000);
			if (urlConn.getResponseCode() != 200) {
				return null;
			}
			// 得到输入流；
			InputStream inputStream = urlConn.getInputStream();
			return inputStream;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
