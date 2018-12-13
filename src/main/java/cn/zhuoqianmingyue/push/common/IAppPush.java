package cn.zhuoqianmingyue.push.common;

import com.arronlong.httpclientutil.exception.HttpProcessException;

import cn.zhuoqianmingyue.push.config.AppPushConfig;
import cn.zhuoqianmingyue.push.convert.IAppPushParamConverter;
import cn.zhuoqianmingyue.push.param.AppPushParam;

public interface IAppPush {
	
	
	/**
	 * app 推送服务
	 * @param appPushParam
	 * @return
	 * @throws HttpProcessException 
	 */
	public boolean push(AppPushParam appPushParam);
}
