package cn.zhuoqianmingyue.push.impl;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;

import com.arronlong.httpclientutil.HttpClientUtil;
import com.arronlong.httpclientutil.builder.HCB;
import com.arronlong.httpclientutil.common.HttpConfig;
import com.arronlong.httpclientutil.common.HttpHeader;
import com.arronlong.httpclientutil.exception.HttpProcessException;

import cn.zhuoqianmingyue.push.common.AbstractAppPush;
import cn.zhuoqianmingyue.push.config.AppPushConfig;
import cn.zhuoqianmingyue.push.config.impl.JiGuangConfig;
import cn.zhuoqianmingyue.push.convert.impl.JiGuangAppPushParamConverter;
import cn.zhuoqianmingyue.push.param.AppPushParam;
import sun.misc.BASE64Encoder;

public class JiGuangPushImpl  extends AbstractAppPush{
	static HCB hcb = null;
	static {
		try {
			 hcb = HCB.custom()
					 .timeout(1000) //超时
					 .pool(100, 10);//启用连接池，每个路由最大创建10个链接，总连接数限制为100个
		} catch (HttpProcessException e) {
			e.printStackTrace();
		}
	}
	public JiGuangPushImpl() {
		super.appPushParamConverter = new JiGuangAppPushParamConverter();
		super.appPushConfig = new JiGuangConfig();
	}
	
	@Override
	public String getAuthorization(AppPushConfig appPushConfig) {
		String appKey = appPushConfig.getAppKey();
		String masterSecret = appPushConfig.getMasterSecret();
		 String base64_auth_string = encryptBASE64(appKey + ":" + masterSecret);
	     String authorization = "Basic " + base64_auth_string;
	    return authorization;
	}

	@Override
	public String convertAppPushParam(AppPushParam appPushParam) {
		String pushParmJsonStr = appPushParamConverter.convert(appPushParam);
		return pushParmJsonStr;
	}

	@Override
	public String post(AppPushConfig appPushConfig, String authorization, String pushParmJsonStr) {
		
		String returnJson = null;
		Header[] headers = HttpHeader.custom()
    			.other("Authorization", authorization.trim())
    			.userAgent("Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36").build();
		
		try {
			
			CloseableHttpClient client = hcb.build();
			
			HttpConfig config = HttpConfig.custom()
	                .headers(headers)	//设置headers，不需要时则无需设置
	                .url(appPushConfig.getPushUrl())	          //设置请求的url
	                .json(pushParmJsonStr)	          //设置请求参数，没有则无需设置
	                .encoding("utf-8") //设置请求和返回编码，默认就是Charset.defaultCharset()
	                .client(client)    //如果只是简单使用，无需设置，会自动获取默认的一个client对象
	                .inenc("utf-8")  //设置请求编码，如果请求返回一直，不需要再单独设置
	                .inenc("utf-8");	//设置返回编码，如果请求返回一直，不需要再单独设置
	         returnJson = HttpClientUtil.post(config);//post请求
	        
	        try {
	        	 if(client != null) {
	        		client.close();
	        	 }
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (HttpProcessException e) {
			e.printStackTrace();
		} 
        
        return returnJson;
	}
	
	/**
	 * BASE64加密工具s
	 * @param str
	 * @return
	 */
	 public static String encryptBASE64(String str) {
	     byte[] key = str.getBytes();
	   BASE64Encoder base64Encoder = new BASE64Encoder();
	   String strs = base64Encoder.encodeBuffer(key);
	     return strs;
	 }
}
