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
    private BarcodeManager mBarcodeManager;
    private BarcodeConfig mBarcodeConfig;
    public void onStart(Context pContext, Bundle pSavedInstanceState, String[] pRuntimeArgs) {
        


    }

    public void PluginTestFunction(IWebview    pWebview, JSONArray array)
    {
        mcontent = Const.context;
        mBarcodeConfig = new BarcodeConfig(mcontent);
        Log.d("Scaner",String.valueOf(mcontent));
        boolean isAim = mBarcodeConfig.isDecodeAimIlluminiation();
        mBarcodeManager = new BarcodeManager(mcontent);
        mBarcodeManager.addListener(new BarcodeListener() {
            @Override
            public void barcodeEvent(BarcodeEvent event) {
                // TODO Auto-generated method stub
                Log.d(TAG,	"---->>heww barcodeEvent() event =" + event.getOrder());
                // 当条码事件的命令为“SCANNER_READ”时，进行操作
                if (event.getOrder().equals(SCANNER_READ)) {
                    if(true){
                        handleResult();
                    }
                }
            }

        });


    	// 原生代码中获取JS层传递的参数，
    	// 参数的获取顺序与JS层传递的顺序一致
        String CallBackID = array.optString(0);
        Log.d(TAG, "PluginTestFunction: "+CallBackID);
        Log.d(TAG, "PluginTestFunction: "+array.optString(1));
        // 调用方法将原生代码的执行结果返回给js层并触发相应的JS层回调函数
        JSUtil.execCallback(pWebview, CallBackID, array.optString(1), JSUtil.OK, false);
        mBarcodeManager.startScanner();
    }
    private void handleResult(){
        Log.d(TAG, "---->>heww handleResult()");
        // 调用 getBarcode()方法读取条码信息
        String barcode = mBarcodeManager.getBarcode();
        Log.d("Scaner",barcode);
    }
    public void PluginTestFunctionArrayArgu(IWebview pWebview, JSONArray array)
    {
        String ReturnString = null;
        String CallBackID =  array.optString(0);
        JSONArray newArray = null;
        try {

            newArray = new JSONArray( array.optString(1));
            Log.d(TAG, "PluginTestFunctionArrayArgu: "+newArray);
            String inValue1 = newArray.getString(0);
            String inValue2 = newArray.getString(1);
            String inValue3 = newArray.getString(2);
            ReturnString = inValue1 + "-" + inValue2 + "-" + inValue3;
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        JSUtil.execCallback(pWebview, CallBackID, ReturnString, JSUtil.OK, false);
    }

    public String PluginTestFunctionSyncArrayArgu(IWebview pWebview, JSONArray array)
    {
        JSONArray newArray = null;
        JSONObject retJSONObj = null;
        Log.d(TAG, "PluginTestFunctionSyncArrayArgu: "+array);
        try {

            newArray = array.optJSONArray(0);
            String inValue1 = newArray.getString(0);
            String inValue2 = newArray.getString(1);
            String inValue3 = newArray.getString(2);
            String inValue4 = newArray.getString(3);

            retJSONObj = new JSONObject();
            retJSONObj.putOpt("RetArgu1", inValue1);
            retJSONObj.putOpt("RetArgu2", inValue2);
            retJSONObj.putOpt("RetArgu3", inValue3);
            retJSONObj.putOpt("RetArgu4", inValue4);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }       

        return JSUtil.wrapJsVar(retJSONObj);
    }

    public String PluginTestFunctionSync(IWebview pWebview, JSONArray array)
    {
        String inValue1 = array.optString(0);
        String inValue2 = array.optString(1);
        String inValue3 = array.optString(2);
        String inValue4 = array.optString(3);
        Log.d(TAG, "PluginTestFunctionSync: "+array);
        String ReturnValue = inValue1 + "-" + inValue2 + "-" + inValue3 + "-" + inValue4;
        // 只能返回String类型到JS层。
        return JSUtil.wrapJsVar(ReturnValue,true);
    }




}