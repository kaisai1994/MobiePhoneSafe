package bilibili.kagura.com.mobiephonesafe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import bilibili.kagura.com.mobiephonesafe.utils.StreamUtil;

public class SplashActivity extends AppCompatActivity {

    protected static final int MSG_UPDATE_DIALOG = 1;
    protected static final int MSG_ENTER_HOME = 2;
    protected static final int MSG_SERVER_ERROR = 3;
    protected static final int MSG_URL_ERROR = 4;
    protected static final int MSG_IO_ERROR = 5;
    protected static final int MSG_JSON_ERROR = 6;
    private String code;
    private String apkurl;
    private String des;
    private TextView tv_splash_versionname;
    private TextView tv_splash_plan;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case MSG_UPDATE_DIALOG:
                    showdialog();
                    break;
                case MSG_ENTER_HOME:
                    enterHome();
                    break;
                case MSG_SERVER_ERROR:
                    enterHome();
                    break;
                case MSG_URL_ERROR:
                    enterHome();
                    break;
                case MSG_IO_ERROR:
                    enterHome();
                    break;
                case MSG_JSON_ERROR:
                    enterHome();
                    break;
            }
        }
    };

    protected void showdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框不能消失
        builder.setCancelable(false);
        //设置对话框的标题
        builder.setTitle("新版本:"+code);
        //设置对话框的图标
        builder.setIcon(R.drawable.ic_launcher);
        //设置对话框的描述信息
        builder.setMessage(des);
        //设置升级取消按钮
        builder.setPositiveButton("升级", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //3.下载最新版本
                download();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //1.隐藏对话框
                dialog.dismiss();
                //2.跳转到主界面
                enterHome();
            }
        });
        //显示对话框
        //builder.create().show();//两种方式效果一样
        builder.show();
    }

    private void download() {
        HttpUtils httpUtils = new HttpUtils();
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
        {
            httpUtils.download(apkurl, "/mnt/sdcard/mobliesafe75_2.apk", new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> responseInfo) {
                    installAPK();
                }
                //下载失败调用
                @Override
                public void onFailure(HttpException e, String s) {

                }
                //显示当前下载进度
                //total 下载总进度
                //current 下载的当前进度
                //isuploading 是否支持断点续传
                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    tv_splash_plan.setVisibility(View.VISIBLE);
                    tv_splash_plan.setText(current+"/"+total);
                }
            });
        }
    }

    private void installAPK() {
        /**
         * <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="content" /> //content : 从内容提供者中获取数据  content://
         <data android:scheme="file" /> // file : 从文件中获取数据
         <data android:mimeType="application/vnd.android.package-archive" />
         </intent-filter>
         */
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/mobliesafe75_2.apk")),"application/vnd.android.package-archive");
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        enterHome();
    }

    private void enterHome(){
        Intent intent = new Intent(bilibili.kagura.com.mobiephonesafe.SplashActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_splash_versionname = (TextView) findViewById(R.id.tv_splash_versionname);
        System.out.println(getVersionName());
        tv_splash_versionname.setText("版本号:"+getVersionName());
        sp = getSharedPreferences("config",MODE_PRIVATE);
        if(sp.getBoolean("update",true))
        {
            update();
        }else{
            new Thread(){
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    enterHome();
                }
            }.start();
        }
        update();
    }

    private void update() {
        //1.连接服务器查看是否有最新版本，联网操作，耗时操作不允许在主线程中执行
        new Thread(){
            @Override
            public void run() {
                Message message = Message.obtain();
                int startTime = (int) System.currentTimeMillis();
                try {
                    URL url = new URL("http://192.168.56.101:8080/updateinfo.html");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    int responseCode = conn.getResponseCode();
                    if(responseCode == 200)
                    {
                        System.out.println("连接成功");
                        InputStream in = conn.getInputStream();
                        String json = StreamUtil.parserStreamUtil(in);
                        JSONObject jsonObject = new JSONObject(json);
                        code = jsonObject.getString("code");
                        apkurl = jsonObject.getString("apkurl");
                        des = jsonObject.getString("des");
                        System.out.println(code+apkurl+des);
                        if(code.equals(getVersionName()))
                        {
                            message.what = MSG_ENTER_HOME;
                        }else{
                            message.what = MSG_UPDATE_DIALOG;
                        }
                    }else{
                        message.what=MSG_SERVER_ERROR;
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    message.what = MSG_URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = MSG_IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = MSG_JSON_ERROR;
                }finally {
                    int endTime = (int) System.currentTimeMillis();
                    int dtime = endTime - startTime;
                    if(dtime < 2000)
                    {
                        SystemClock.sleep(2000-dtime);
                    }
                    handler.sendMessage(message);
                }
            }
        }.start();
    }


    /**
     * 获取当前应用程序版本号
     * @return
     */
    private String getVersionName()
    {
        //包的管理者，获取清单文件中的所有信息
        PackageManager pm = getPackageManager();
        //根据包名获取清单文件中的信息，其实就是返回一个保存有清单文件的信息的javaben
        //packageName：应用程序的包名
        //flags:指定信息的标签，0：获取基础的信息，比如包名，版本号，想要获取权限等等信息，必须通过标签去指定才能获取
        //GET_PERMISSIONS：标签的含义：处理获取基础信息之外，还会额外获取权限的信息
        //getPackageName()获取当前应用程序包名
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(),0);
            String versionName = packageInfo.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
