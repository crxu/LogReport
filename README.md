# SNSDemo
lib库发布

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  
  dependencies {
	        implementation 'com.github.crxu:SNSDemo:V1.0.4'
	}
	
	
	
 ublic class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initlogger();
        initLogReport();
    }

    private void initLogReport(){
        LogReport.getInstance()
                .setCacheSize(30 * 1024 * 1024)//支持设置缓存大小，超出后清空
                .setLogDir(getApplicationContext(), "sdcard/" + this.getString(this.getApplicationInfo().labelRes) + "/")//定义路径为：sdcard/[app name]/
                .setWifiOnly(true)//设置只在Wifi状态下上传，设置为false为Wifi和移动网络都上传
                .setLogSaver(new CrashWriter(getApplicationContext()))//支持自定义保存崩溃信息的样式
                //.setEncryption(new AESEncode()) //支持日志到AES加密或者DES加密，默认不开启
                .init(getApplicationContext());
        initEmailReporter();
    }

    private void initEmailReporter() {
        EmailReporter email = new EmailReporter(this);
        email.setReceiver("xucr@gratone.cn");//收件人
        email.setSender("286299340@qq.com");//发送人邮箱
        email.setSendPassword("ouogfdnbuonubgdf");//邮箱的客户端授权码，注意不是邮箱密码
        email.setSMTPHost("smtp.qq.com");//SMTP地址
        email.setPort("465");//SMTP 端口
        LogReport.getInstance().setUploadType(email);
    }

    private void initlogger(){
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)         // （可选）要显示的方法行数。 默认2
                .methodOffset(7)        // （可选）隐藏内部方法调用到偏移量。 默认5
                // .logStrategy(customLog) //（可选）更改要打印的日志策略。 默认LogCat
                .tag(getString(R.string.app_name))   //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
