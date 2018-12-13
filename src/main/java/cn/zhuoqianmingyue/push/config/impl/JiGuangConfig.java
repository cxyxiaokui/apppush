package cn.zhuoqianmingyue.push.config.impl;

import cn.zhuoqianmingyue.push.config.AppPushConfig;

/**
 * 极光推送配置信息
 * @author lijunkui
 *
 */
public class JiGuangConfig implements AppPushConfig{
   private  String masterSecret = "";
   private  String appKey = "";
   private  String pushUrl = "https://api.jpush.cn/v3/push";
	@Override
	public String getAppKey() {
		return appKey;
	}
	@Override
	public String getPushUrl() {
		return pushUrl;
	}
	@Override
	public String getMasterSecret() {
		return masterSecret;
	}
	   
}
