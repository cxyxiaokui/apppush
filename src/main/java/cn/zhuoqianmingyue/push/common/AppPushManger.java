package cn.zhuoqianmingyue.push.common;

import cn.zhuoqianmingyue.push.param.AppPushParam;

public class AppPushManger {
	
	private IAppPush appPush;
	
	public AppPushManger(IAppPush appPush) {
		this.appPush = appPush;
	}
	
	public boolean push(AppPushParam appPushParam) {
		return appPush.push(appPushParam);
	}
}
