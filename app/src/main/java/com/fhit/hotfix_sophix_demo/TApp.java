package com.fhit.hotfix_sophix_demo;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.fhit.hotfix_sophix_demo.util.Utils;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 *快速接入方式
 * Created by liubaoxing on 2018/3/13 10:49<br/>
 * Email:liubaoxinggo@foxmail.com<br/>
 */

public class TApp extends Application {

    public final static String TAG = "hotFix";
    public final static String AppKey = "24821441";
    public final static String AppSecret = "484f5a71a3a4fed64c277255030e072c";
    public final static String RsaSecret = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdq/ZpD8UqM4C8K5RW3aA/xzjOf9OomY3oKCLIj7ELwqQ8U+zu+YdjKdKrbZ2TX0SM+CJA+fhgmNmY/fMnXs3QeB2gPXNSAyZ1E51P1Mjz25TRPaJihWfEm6cOnIXn+ActVBvnun4CM9IBKsMHmKLhewcCIzeUZoyUR7gUA4IvQSMSn7qgGD8Lvf9K0OYOazrTo9D14SqyIvOqxW7RLlaOCEq+r1EY9qhxMs33Y9Pcag/3V578SWJ4pdTe7ZWpZO2yQ6SmtsUVdZ36FHDpjUQvr6UfkWZLGiZvRsWqOLB3/2/2LCmSsfScIJgb7dEt6xmxT07pA9Zqbnju++owJT71AgMBAAECggEAcl50RM0tpWjqbpPl7ASi4uBtskxCib+okcfbQ0QHlpI/c4slCEt0C1gg+jxu2qF2JhMvNZacteTpofJDWEGKZTdbtgodBlZGpRHvQCMHCOw0THq6uWAvXwvQ5+Zds1/UROsHutUp5gsUv8f25CGxtID7Gm/VNw4T1tMXEV1iJ0eivcGzgHZ8PQj+CHguV03b3hp/whYyvSgFTN2mJFT00BNZCbwyI7JTWqbtWNzYmL1aMZsoNUsw/tREFU4W4sgbrT4U4+yXlwFu/X6Cxx+B1TWiOAHE79QHDBPwOnLquBZFJNxEeA3RmznrJ2cC3nO4g0UOvU6w5zsF0+/4UPVH2QKBgQDPb+LtX3/8cRor44XJFQ+tL+/iVhDcggZCvPFIHpJZSySqBtPlFxweN1K5Q/s5KFusrHGvekRM/pg1mXhjXb3Gydo+FfAW9cUgavL5leJNdhAhWmpJ+4XApQX/+Vgqi28/dUCHnZ++atLNHz7gXKWTyt/6VaNayM/yf6gI1o6uYwKBgQDClYqds09ED4YSiAM0wfm7FyipZnN+N2DB1WsEO5Sa/3zRqdwcpCj5f/eU7DiX1HQhUbKgcMIcx2jikRCJMlZZucyNNZ5pmuhCXwL2jEZwakquDm8l89UyryvM/bpbiZQdL7GbEARXYZF7frDuhroYje02xbAr5iP2viKgTYyQxwKBgQCbZE90TmQL8ZYPZqNj3NkffZePz7VV2jliaTO+ONic94OBsxest8EiiYL4sgpR6P+zvIJ1V3wuyVGwZUUf+qsdbTyF4d029ekzvapCeZtLN7QhqB+TSE4L4BM8i8cWJ9mwid9LODLgbBl5+3wYio6mxgU+/EQgz+mm2SZBaKj9zQKBgHmjEavTU1GwgpdC/v4T5BDYQUrkCH52D74bs3JJ7HOYEmbeUAJ3fEW7gQFPSXFgFdheQdOZqT5kSWSYWkXpeaFNTFlZk1VgyJ/01N1agw3y5Aw6abDH5BZy+maCleRy9GvqTJQqRwx4qYERiXkSL3L6BbefjKlkjEbKRMsAr0YxAoGBALQwDddShaAiSdKS3vYCx9Yv0eQgmavq/MhabLdAA2c0k9S0oViSiLg6nmQwKuDYM3x3X58brSHedRA7PK/FNt0M43Yo/P1Ry8abIJPrXXMQT6c/lizaso6+UYzRXC0/E53Zvz1qdBpA9K5CKnO8pyV7M690CgaocNeJjZK5Ax7H";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"【TApp  onCreate】");
        Utils.init(this);
        //queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中;
        //该方法主要用于查询服务器是否有新的可用补丁,只会下载补丁版本号比当前应用存在的补丁版本号高的补丁
        SophixManager.getInstance().queryAndLoadNewPatch();//
    }

    /**
     * initialize的调用应该尽可能的早，必须在Application.attachBaseContext()或者Application.onCreate()的
     * 最开始进行SDK初始化操作，初始化之前不能用到其他自定义类，否则极有可能导致崩溃。
     * 而查询服务器是否有可用补丁的操作可以在后面的任意地方
     */
    private void initSophix(){
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            appVersion = "1.0.0";
        }
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                //(idSecret, appSecret, rsaSecret)
                .setSecretMetaData(AppKey,AppSecret,RsaSecret)//最好在这里设置，不要写在manifest里
                //.setAesKey("")//可选，必须是16位数字或字母的组合
                .setEnableDebug(true)//默认为false，设为true即调试模式下会输出日志以及不进行补丁签名校验. 线下调试此参数可以设置为true, 它会强制不对补丁进行签名校验, 所有就算补丁未签名或者签名失败也发现可以加载成功. 但是正式发布该参数必须为false, false会对补丁做签名校验, 否则就可能存在安全漏洞风险。

                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    /**
                     *
                     * @param mode 无实际意义, 为了兼容老版本, 默认始终为0
                     * @param code 补丁加载状态码, 详情查看PatchStatus类说明 https://help.aliyun.com/document_detail/53240.html?spm=a2c4g.11186623.2.3.urBwA9
                     * @param info 补丁加载详细说明
                     * @param handlePatchVersion 当前处理的补丁版本号, 0:无 -1:本地补丁 其它:后台补丁
                     */
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        //补丁加载回调
                        if(code == PatchStatus.CODE_LOAD_SUCCESS){//加载成功
                            Log.d(TAG,"【补丁加载成功】");
                        }else if(code == PatchStatus.CODE_LOAD_RELAUNCH){//补丁生效需要重启
                            //开发者可以通知用户或者强制重启；//可以监听进入后台事件，然后调用
                            // killProcessSafely自杀，以此来加快应用补丁
                            Log.d(TAG,"【补丁生效需要重启】");
                        }else{//其他的错误信息
                            Log.d(TAG,"补丁加载失败：mode = "+mode+",code = "+code+",info = "+info+",handlePatchVersion = "+handlePatchVersion);
                        }
                    }
                }).initialize();
    }
}
