package com.sns.demo;

import android.app.Application;
import android.content.Context;

import com.crxu.library.LogReport;
import com.crxu.library.save.imp.CrashWriter;
import com.crxu.library.upload.email.EmailReporter;
import com.crxu.library.util.JLog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import cat.ereza.customactivityoncrash.CustomActivityOnCrash;
import cat.ereza.customactivityoncrash.config.CaocConfig;


/**
 * File: MyApplication.java
 * Author: Administrator
 * Version:1.0.2
 * Create: 2018/10/29 0029 14:20
 * describe 请用一句话描述
 */

public class MyApplication extends Application {

    private static MyApplication application;


    @Override
    public void onCreate() {
        super.onCreate();
        initlogger();
        initLogReport();
        initCash();
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
        email.setReceiver("itilrong@aliyun.com");//收件人
        email.setSender("15990021742@163.com");//发送人邮箱
        email.setSendPassword("qazwsx1023");//邮箱的客户端授权码，注意不是邮箱密码
        email.setSMTPHost("smtp.163.com");//SMTP地址
        email.setPort("994");//SMTP 端口
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

    private void initCash(){
        CaocConfig.Builder.create()
                //程序在后台时，发生崩溃的三种处理方式
                //BackgroundMode.BACKGROUND_MODE_SHOW_CUSTOM: //当应用程序处于后台时崩溃，也会启动错误页面，
                //BackgroundMode.BACKGROUND_MODE_CRASH:      //当应用程序处于后台崩溃时显示默认系统错误（一个系统提示的错误对话框），
                //BackgroundMode.BACKGROUND_MODE_SILENT:     //当应用程序处于后台时崩溃，默默地关闭程序！
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT)
                .enabled(true)     //false表示对崩溃的拦截阻止。用它来禁用customactivityoncrash框架
                .showErrorDetails(false) //这将隐藏错误活动中的“错误详细信息”按钮，从而隐藏堆栈跟踪,—》针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。。
                .showRestartButton(false)    //是否可以重启页面,针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。
                .trackActivities(true)     //错误页面中显示错误详细信息；针对框架自带程序崩溃后显示的页面有用(DefaultErrorActivity)。
                .minTimeBetweenCrashesMs(2000)      //定义应用程序崩溃之间的最短时间，以确定我们不在崩溃循环中。比如：在规定的时间内再次崩溃，框架将不处理，让系统处理！
                .errorDrawable(R.mipmap.ic_launcher)     //崩溃页面显示的图标
                .restartActivity(MainActivity.class)      //重新启动后的页面
                .errorActivity(null) //程序崩溃后显示的页面
                .eventListener(new CustomEventListener())//设置监听
                .apply();

    }



    private static class CustomEventListener implements CustomActivityOnCrash.EventListener {
        //程序崩溃回调
        @Override
        public void onLaunchErrorActivity() {
            JLog.e("huangxiaoguo", "程序崩溃回调");
        }

        //重启程序时回调
        @Override
        public void onRestartAppFromErrorActivity() {
            JLog.e("huangxiaoguo", "重启程序时回调");
        }

        //在崩溃提示页面关闭程序时回调
        @Override
        public void onCloseAppFromErrorActivity() {
            JLog.e("huangxiaoguo", "在崩溃提示页面关闭程序时回调");
        }

    }

}
