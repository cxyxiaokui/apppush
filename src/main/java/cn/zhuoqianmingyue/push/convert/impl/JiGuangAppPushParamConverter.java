package cn.zhuoqianmingyue.push.convert.impl;

import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.zhuoqianmingyue.push.convert.IAppPushParamConverter;
import cn.zhuoqianmingyue.push.enumClass.PlatFormEnum;
import cn.zhuoqianmingyue.push.param.AppPushParam;
/**
 * app 推送参数json数据转换类
 * @author zhuoqianmingyue
 */
public class JiGuangAppPushParamConverter implements IAppPushParamConverter{
	private static Logger log = LoggerFactory.getLogger(JiGuangAppPushParamConverter.class);
	/**
	 *  
	 * @param appPushParam
	 * @return
	 */
	public String convert(AppPushParam appPushParam) {

		JSONObject pushParamJson = new JSONObject();
		List<PlatFormEnum> platformList = appPushParam.getPlatform();
		Map<String, String[]> audienceMap = appPushParam.getAudience();
		Map<String, String> extras = appPushParam.getExtras();
		//构建推送平台信息
		JSONArray platform = buildPlatform(platformList,new JSONArray());;//平台
		//构建推送目标信息
		JSONObject audience = buildAudience(audienceMap,new JSONObject());;
		//构建安卓的推送信息
		JSONObject notification = new JSONObject();
		buildAndroidInfo(notification,extras,platformList,appPushParam);
		buildIosInfo(notification,extras,platformList,appPushParam);
		//构建IOS参数信息
        JSONObject options = new JSONObject();
        buildOptions(options,appPushParam);
        //设置推送平台信息
        setPlatform(platform,pushParamJson);
        //设置推送目标信息
        setAudience(audience,pushParamJson);
        //设置推送信息
        pushParamJson.put("notification", notification);
        //设置options信息
        pushParamJson.put("options", options);
        log.info("app push json:"+pushParamJson.toString());
		return pushParamJson.toString();
	}
	private static void setAudience(JSONObject audience, JSONObject pushParamJson) {
		 if(audience==null){
	       	 	pushParamJson.put("audience", "all");//发广播
	        }else{
	       	 	pushParamJson.put("audience", audience);
	        }
		
	}
	private static void setPlatform(JSONArray platform, JSONObject pushParamJson) {
		if(platform == null){
       	 pushParamJson.put("platform", "all");
       }else{
       	 pushParamJson.put("platform", platform);
       }
		
	}
	private static void buildOptions(JSONObject options, AppPushParam appPushParam) {
		 options.put("time_to_live", appPushParam.getTimeToLive());
	     options.put("apns_production", appPushParam.isPushEnvironment());
	}
	private static void buildIosInfo(JSONObject notification, Map<String, String> extras,
			List<PlatFormEnum> platformList, AppPushParam appPushParam) {
		if(platformList != null && platformList.size()>0 && platformList.contains(PlatFormEnum.IOS)){
			  JSONObject ios = new JSONObject();//ios通知内容
		        ios.put("alert", appPushParam.getMessage());
		        ios.put("sound", "default");//通知提示声音或警告通知
		        ios.put("badge", "+1");//应用角标
		        
		        JSONObject ios_extras = new JSONObject();//ios额外参数
		        if(extras!=null && extras.size()>0){
		        	 for (Map.Entry<String, String> m : extras.entrySet()) {
		    			 ios_extras.put(m.getKey(), m.getValue());
		    		 }
		    		 ios.put("extras", ios_extras);//ios额外参数
		        }
		       notification.put("ios", ios);
		}
	}
	private static void buildAndroidInfo(JSONObject notification, Map<String, String> extras,
			List<PlatFormEnum> platformList,AppPushParam appPushParam) {
		if(platformList != null && platformList.size()>0 && platformList.contains(PlatFormEnum.ANDROID)){
			JSONObject android = new JSONObject();
			android.put("alert", appPushParam.getMessage());
			android.put("title", appPushParam.getTitle());
			android.put("builder_id", 1);//设置通知栏样式
			JSONObject android_extras = new JSONObject();//android额外参数
			
			if(extras!=null && extras.size()>0){
				 for (Map.Entry<String, String> m : extras.entrySet()) {
					 android_extras.put(m.getKey(), m.getValue());
				 }
			}
			 android.put("extras", android_extras);//android额外参数
			 notification.put("android", android);
		}
		
	}
	private static JSONObject buildAudience(Map<String, String[]> audienceMap, JSONObject audience) {
		if(audienceMap == null || audienceMap.size() == 0) {
			return null;
		}
		for (Map.Entry<String, String[]> m : audienceMap.entrySet()) {
			String[] value = m.getValue();
			JSONArray audienceArray = new JSONArray();//平台
			for (String str : value) {
				audienceArray.add(str);
			}
			audience.put(m.getKey(), audienceArray);
	    }
		return audience;
		
	}
	/**
	 *  构建推送平台
	 * @param platformList
	 * @param platform
	 */
	private static JSONArray buildPlatform(List<PlatFormEnum> platformList, JSONArray platform) {
		
		if(platformList != null && platformList.size()>0){
			for (PlatFormEnum platFormEnum : platformList) {
				String platformStr = platFormEnum.toString().toLowerCase();
				platform.add(platformStr);
			}
			return platform;
		}else {
			return null;
		}
	}

}
