package cn.zhuoqianmingyue.push.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.arronlong.httpclientutil.exception.HttpProcessException;

import cn.zhuoqianmingyue.push.common.AppPushManger;
import cn.zhuoqianmingyue.push.enumClass.PlatFormEnum;
import cn.zhuoqianmingyue.push.impl.JiGuangPushImpl;
import cn.zhuoqianmingyue.push.param.AppPushParam;

public class JiGuangPushTest {
	public static  void main(String[] args) throws HttpProcessException {
		AppPushParam appPushParam = new AppPushParam();
		//推送弹窗的信息内容
		appPushParam.setTitle("标题");
		appPushParam.setMessage("测试信息");
 		//推动的自定义信息
 		Map<String, String> homeExtrasInfo = new HashMap<String,String>();
 		appPushParam.setExtras(homeExtrasInfo);
    	//设置推送的平台
    	List<PlatFormEnum> platformList = new ArrayList<PlatFormEnum>();
    	platformList.add(PlatFormEnum.ANDROID);
    	//platformList.add(PlatFormEnum.IOS);
    	appPushParam.setPlatform(platformList);
    	//设置推送目标 如果不设置就是广播模式
    	Map<String, String[]> audience = new HashMap<String,String[]>();
    	audience.put("registration_id", new String[] {"140fe1da9efeac6fd85"});//指定设备的registration_id进行发送
    	appPushParam.setAudience(audience);
    	//设置开发环境 极光推送只是对ios有效 
    	appPushParam.setPushEnvironment(false);
    	//设置离线消息保留时长(秒) 
    	appPushParam.setTimeToLive(60);
    	
		AppPushManger manger = new AppPushManger(new JiGuangPushImpl());
		manger.push(appPushParam);
	}
}
