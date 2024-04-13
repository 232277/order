package com.dyc.order.cashier.base;

import android.Manifest;
import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.dyc.administrator.toollibrary.utils.MLogger;
import com.dyc.order.cashier.BuildConfig;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.OkHttpClient;
import rxhttp.wrapper.param.RxHttp;

/**
 * func:
 * author:丁语成 on 2020/2/11 16:06
 * mail:dingyucheng@centerm.com
 * tel:18279114677
 */
public class MyApplication extends Application {
    public static final String[] NEEDED_PERMISSIO = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static Context context;

    public static Context getContext() {
        return context;
    }

    public static String getStringFromR(int id) {
        return context.getString(id);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        MLogger.config(this);

        initRefresh();

        Utils.init(this);
        RxHttp.setDebug(BuildConfig.DEBUG);
        //设置读、写、连接超时时间为15s
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        RxHttp.init(client);
    }

    private void initRefresh() {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {

                return new ClassicsHeader(context)
                        .setEnableLastTime(false)
                        .setTextSizeTitle(13f);
            }
        });

        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new ClassicsFooter(context).setTextSizeTitle(13f);
            }
        });
    }
}