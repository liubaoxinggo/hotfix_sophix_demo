package com.fhit.hotfix_sophix_demo;

import android.content.Context;
import android.content.pm.PackageManager;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

import android.support.annotation.Keep;
import android.util.Log;
/**
 * 稳健接入方式
 * Created by liubaoxing on 2018/3/14 13:45<br/>
 * Email:liubaoxinggo@foxmail.com<br/>
 */

public class SophixStubApplication extends SophixApplication {
    public final static String TAG = "hotFix";
    public final static String AppKey = "24821441";
    public final static String AppSecret = "484f5a71a3a4fed64c277255030e072c";
    public final static String RsaSecret = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCdq/ZpD8UqM4C8K5RW3aA/xzjOf9OomY3oKCLIj7ELwqQ8U+zu+YdjKdKrbZ2TX0SM+CJA+fhgmNmY/fMnXs3QeB2gPXNSAyZ1E51P1Mjz25TRPaJihWfEm6cOnIXn+ActVBvnun4CM9IBKsMHmKLhewcCIzeUZoyUR7gUA4IvQSMSn7qgGD8Lvf9K0OYOazrTo9D14SqyIvOqxW7RLlaOCEq+r1EY9qhxMs33Y9Pcag/3V578SWJ4pdTe7ZWpZO2yQ6SmtsUVdZ36FHDpjUQvr6UfkWZLGiZvRsWqOLB3/2/2LCmSsfScIJgb7dEt6xmxT07pA9Zqbnju++owJT71AgMBAAECggEAcl50RM0tpWjqbpPl7ASi4uBtskxCib+okcfbQ0QHlpI/c4slCEt0C1gg+jxu2qF2JhMvNZacteTpofJDWEGKZTdbtgodBlZGpRHvQCMHCOw0THq6uWAvXwvQ5+Zds1/UROsHutUp5gsUv8f25CGxtID7Gm/VNw4T1tMXEV1iJ0eivcGzgHZ8PQj+CHguV03b3hp/whYyvSgFTN2mJFT00BNZCbwyI7JTWqbtWNzYmL1aMZsoNUsw/tREFU4W4sgbrT4U4+yXlwFu/X6Cxx+B1TWiOAHE79QHDBPwOnLquBZFJNxEeA3RmznrJ2cC3nO4g0UOvU6w5zsF0+/4UPVH2QKBgQDPb+LtX3/8cRor44XJFQ+tL+/iVhDcggZCvPFIHpJZSySqBtPlFxweN1K5Q/s5KFusrHGvekRM/pg1mXhjXb3Gydo+FfAW9cUgavL5leJNdhAhWmpJ+4XApQX/+Vgqi28/dUCHnZ++atLNHz7gXKWTyt/6VaNayM/yf6gI1o6uYwKBgQDClYqds09ED4YSiAM0wfm7FyipZnN+N2DB1WsEO5Sa/3zRqdwcpCj5f/eU7DiX1HQhUbKgcMIcx2jikRCJMlZZucyNNZ5pmuhCXwL2jEZwakquDm8l89UyryvM/bpbiZQdL7GbEARXYZF7frDuhroYje02xbAr5iP2viKgTYyQxwKBgQCbZE90TmQL8ZYPZqNj3NkffZePz7VV2jliaTO+ONic94OBsxest8EiiYL4sgpR6P+zvIJ1V3wuyVGwZUUf+qsdbTyF4d029ekzvapCeZtLN7QhqB+TSE4L4BM8i8cWJ9mwid9LODLgbBl5+3wYio6mxgU+/EQgz+mm2SZBaKj9zQKBgHmjEavTU1GwgpdC/v4T5BDYQUrkCH52D74bs3JJ7HOYEmbeUAJ3fEW7gQFPSXFgFdheQdOZqT5kSWSYWkXpeaFNTFlZk1VgyJ/01N1agw3y5Aw6abDH5BZy+maCleRy9GvqTJQqRwx4qYERiXkSL3L6BbefjKlkjEbKRMsAr0YxAoGBALQwDddShaAiSdKS3vYCx9Yv0eQgmavq/MhabLdAA2c0k9S0oViSiLg6nmQwKuDYM3x3X58brSHedRA7PK/FNt0M43Yo/P1Ry8abIJPrXXMQT6c/lizaso6+UYzRXC0/E53Zvz1qdBpA9K5CKnO8pyV7M690CgaocNeJjZK5Ax7H";

    @Keep
    @SophixEntry(TApp.class)
    static class RealApplicationStub{}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.d(TAG,"【SophixStubApplication  attachBaseContext】");
        initSophix();
    }
    private void initSophix(){
        String appVersion = "0.0.0";
        try {
            appVersion = getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData(AppKey,AppSecret,RsaSecret)
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(int mode, int code, String info, int handlePatchVersion) {
                        if(code == PatchStatus.CODE_LOAD_SUCCESS){
                            Log.d(TAG,"[SophixStubApplication]【补丁加载成功】");
                        }else if(code == PatchStatus.CODE_LOAD_RELAUNCH){
                            Log.d(TAG,"[SophixStubApplication]【补丁生效需要重启】");
                        }else{
                            Log.d(TAG,"[SophixStubApplication]【补丁加载失败：mode = "+mode+",code = "+code+",info = "+info+",handlePatchVersion = "+handlePatchVersion+"】");
                        }
                    }
                }).initialize();
    }
}
