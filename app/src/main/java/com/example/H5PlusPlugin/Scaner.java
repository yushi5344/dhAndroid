package com.example.H5PlusPlugin;
import android.os.Bundle;
import android.util.Log;
import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.dcloud.common.DHInterface.IWebview;
import io.dcloud.common.DHInterface.StandardFeature;
import io.dcloud.common.util.JSUtil;

import com.mexxen.barcode.BarcodeConfig;
import com.mexxen.barcode.BarcodeEvent;
import com.mexxen.barcode.BarcodeListener;
import com.mexxen.barcode.BarcodeManager;
/**
 * 5+ SDK 扩展插件示例
 * 5+ 扩扎插件在使用时需要以下两个地方进行配置
 * 		1  WebApp的mainfest.json文件的permissions节点下添加JS标识
 * 		2  assets/data/properties.xml文件添加JS标识和原生类的对应关系
 * 本插件对应的JS文件在 assets/apps/H5Plugin/js/test.js
 * 本插件对应的使用的HTML assest/apps/H5plugin/index.html
 * 
 * 更详细说明请参考文档http://ask.dcloud.net.cn/article/66
 * **/
public class Scaner extends StandardFeature
{
    private static String TAG = "Scaner";

    public static final String SCANNER_READ = "SCANNER_READ";
    public static final String SCANNER_START = "SCANNER_START";
    public static final String SCANNER_STOP = "SCANNER_STOP";
    public static final String SCANNER_IDLE = "SCANNER_IDLE";


    public Context mcontent;
    public int mScanerNumber = 0;
    public int mScanerTimeStep;
    public String Code=null;
    private BarcodeManager mBarcodeManager;
    private BarcodeConfig mBarcodeConfig;
    public void onStart(Context pContext, Bundle pSavedInstanceState, String[] pRuntimeArgs) {
        


    }

    public void ScanCode(IWebview    pWebview, JSONArray array)
    {
        mcontent = Const.context;
        mBarcodeConfig = new BarcodeConfig(mcontent);
        Log.d("Scaner",String.valueOf(mcontent));
        boolean isAim = mBarcodeConfig.isDecodeAimIlluminiation();
        mBarcodeManager = new BarcodeManager(mcontent);
        mBarcodeManager.addListener(new BarcodeListener() {
            @Override
            public void barcodeEvent(BarcodeEvent event) {
                // 当条码事件的命令为“SCANNER_READ”时，进行操作
                if (event.getOrder().equals(SCANNER_READ)) {
                    if(true){
                       Code= handleResult();
                        Log.d(TAG, "barcodeEvent: "+Code);
                    }
                }
            }
        });

    	// 原生代码中获取JS层传递的参数，
    	// 参数的获取顺序与JS层传递的顺序一致
        String CallBackID = array.optString(0);
        Log.d(TAG, "PluginTestFunction1: "+Code);
        Log.d(TAG, "PluginTestFunction: "+array.optString(1));
        // 调用方法将原生代码的执行结果返回给js层并触发相应的JS层回调函数
        JSUtil.execCallback(pWebview, CallBackID, array.optString(1), JSUtil.OK, false);
//        mBarcodeManager.startScanner();
    }
    private String handleResult(){
        Log.d(TAG, "---->>heww handleResult()");
        // 调用 getBarcode()方法读取条码信息
        String barcode = mBarcodeManager.getBarcode();
        Log.d("Scaner",barcode);
        return barcode;
    }
}