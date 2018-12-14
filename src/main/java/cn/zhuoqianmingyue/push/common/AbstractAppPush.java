package cn.zhuoqianmingyue.push.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.zhuoqianmingyue.push.config.AppPushConfig;
import cn.zhuoqianmingyue.push.convert.IAppPushParamConverter;
import cn.zhuoqianmingyue.push.impl.JiGuangPushImpl;
import cn.zhuoqianmingyue.push.param.AppPushParam;

public abstract class AbstractAppPush implements IAppPush{
	
	private static Logger log = LoggerFactory.getLogger(JiGuangPushImpl.class);
	
    protected IAppPushParamConverter appPushParamConverter;
    protected AppPushConfig appPushConfig;
    
	/**
	 * @param appPushParam
	 * @return
	 */
	public boolean push(AppPushParam appPushParam){
		
		String pushParmJsonStr = convertAppPushParam(appPushParam);
    	String authorization = getAuthorization(appPushConfig);
    	
    	String returnJson = post(appPushConfig,authorization,pushParmJsonStr);
    	if(returnJson!=null){
    		log.info("app push sucess:"+returnJson);
    		return true;
    	}
    	log.info("app push fail!");
    	return false;
	}
	
	
	/**
	 * 认证信息获取
	 * @param appPushConfig
	 * @return
	 */
	public abstract String getAuthorization(AppPushConfig appPushConfig);
	/**
	 *  参数转换
	 *  @param appPushParam
	 */
	public abstract String convertAppPushParam(AppPushParam appPushParam);
	/**
	 *  调用推送第三放服务
	 * @param appPushParam
	 * @param authorization
	 * @param pushParmJsonStr
	 * @return
	 */
	public abstract String post(AppPushConfig appPushConfig, String authorization, String pushParmJsonStr);
}
