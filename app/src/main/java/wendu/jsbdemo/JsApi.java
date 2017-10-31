package wendu.jsbdemo;
import android.app.DownloadManager;
import android.os.CountDownTimer;
import android.util.Base64;
import android.util.Log;
import android.webkit.JavascriptInterface;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;
import wendu.dsbridge.CompletionHandler;

/**
 * Created by du on 16/12/31.
 */

public class JsApi{
    @JavascriptInterface
    public String testSyn(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("msg") + "［syn call］";
    }

    //@JavascriptInterface
    //此方法没有@JavascriptInterface标注将不会被调用
    public String testNever(JSONObject jsonObject) throws JSONException {
        return jsonObject.getString("msg") + "[ never call]";
    }

    @JavascriptInterface
    public String testNoArgSyn(JSONObject jsonObject) throws JSONException {
        return  "testNoArgSyn called [ syn call]";
    }

    @JavascriptInterface
    public void testNoArgAsyn(JSONObject jsonObject,CompletionHandler handler) throws JSONException {
        handler.complete( "testNoArgAsyn  called [ asyn call]");
    }

    @JavascriptInterface
    public void testAsyn(JSONObject jsonObject, CompletionHandler handler) throws JSONException {
        handler.complete(jsonObject.getString("msg")+" [ asyn call]");
    }

    @JavascriptInterface
    public void callProgress(JSONObject jsonObject, final CompletionHandler handler) throws JSONException {

        new CountDownTimer(11000, 1000) {
            int i=10;
            @Override
            public void onTick(long millisUntilFinished) {
                //setProgressData can be called many times util complete be called.
                handler.setProgressData((i--)+"");

            }
            @Override
            public void onFinish() {
                //complete the js invocation with data; handler will expire when complete is called
                handler.complete("");

            }
        }.start();
    }

    /**
     *
     * @param requestData
     * @param handler
     *
     * Note: This api is for Fly.js
     * In browsers, Ajax requests are sent by browsers, and Fly can
     * redirect requests to native, more about Fly see  https://github.com/wendux/fly
     */

    @JavascriptInterface
    public void onAjaxRequest(JSONObject requestData, CompletionHandler handler){
        // Handle ajax request redirected by Fly
        AjaxHandler.onAjaxRequest(requestData,handler);
    }


}