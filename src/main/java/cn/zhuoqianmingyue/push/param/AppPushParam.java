package cn.zhuoqianmingyue.push.param;

import java.util.List;
import java.util.Map;

import cn.zhuoqianmingyue.push.config.AppPushConfig;
import cn.zhuoqianmingyue.push.convert.IAppPushParamConverter;
import cn.zhuoqianmingyue.push.enumClass.PlatFormEnum;

public class AppPushParam{
	
	private String title;//推送标题；
	private String message;//推送消息 空字符串，则表示不展示到通知栏。
	private List<PlatFormEnum> platform;//发送平台 android ios
	private Map<String,String[]> audience;//推送目标 如果为null则是广播
	private int timeToLive; //离线消息保留时长(秒)
	private boolean pushEnvironment;//True 表示推送生产环境，False 表示要推送开发环境
	private Map<String,String> extras;//额外的参数
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<PlatFormEnum> getPlatform() {
		return platform;
	}
	public void setPlatform(List<PlatFormEnum> platform) {
		this.platform = platform;
	}
	public Map<String, String[]> getAudience() {
		return audience;
	}
	public void setAudience(Map<String, String[]> audience) {
		this.audience = audience;
	}
	public int getTimeToLive() {
		return timeToLive;
	}
	public void setTimeToLive(int timeToLive) {
		this.timeToLive = timeToLive;
	}
	public boolean isPushEnvironment() {
		return pushEnvironment;
	}
	public void setPushEnvironment(boolean pushEnvironment) {
		this.pushEnvironment = pushEnvironment;
	}
	public Map<String, String> getExtras() {
		return extras;
	}
	public void setExtras(Map<String, String> extras) {
		this.extras = extras;
	}
}
