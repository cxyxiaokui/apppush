# apppush 项目介绍
app 推送调用第三方后台服务实现 目前实现了极光推送

# 推送服务调用流程说明

后台服务调用AppPushManger中的push方法，push方法中调用 IAppPush中的push方法 具体的IAppPush实现通过AppPushManger 的构造来进行传递。IAppPush的push方法具体实现是在 AbstractAppPush 进行实现的，我通过模板方法模式 在 AbstractAppPush 中将 认证信息获取 （getAuthorization）， 参数转换（convertAppPushParam）， 调用推送第三放服务（post） 定义成模板方法具体的实现在 JiGuangPushImpl中。
程序调用时序图：
![在这里插入图片描述](https://img-blog.csdnimg.cn/20181213150319139.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2xqazEyNnd5,size_16,color_FFFFFF,t_70)

具体使用方式如下：
```
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
```
