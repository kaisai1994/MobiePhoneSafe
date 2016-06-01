package phonesafe.kagura.com.mobiephonesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import phonesafe.kagura.com.mobiephonesafe.R;
import phonesafe.kagura.com.mobiephonesafe.service.GPSService;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/6/1.13:42
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public class SmsReceiver extends BroadcastReceiver {
    private static MediaPlayer mediaPlayer;

    @Override
    public void onReceive(Context context, Intent intent) {
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = new ComponentName(context, Admin.class);

        //接受解析短信

        //70汉字一条短信,71汉字两条短信
        Object[] objs = (Object[]) intent.getExtras().get("pdus");
        for(Object obj:objs){
            //解析成SmsMessage
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) obj);
            String body = smsMessage.getMessageBody();//获取短信的内容
            String sender = smsMessage.getOriginatingAddress();//获取发件人
            System.out.println("发件人:"+sender+"  短信内容:"+body);
            //真机测试,加发件人判断
            //判断短信是哪个指令
            if ("#*location*#".equals(body)) {
                    //GPS追踪
                    System.out.println("GPS追踪");
                    Intent intent_gps = new Intent(context,GPSService.class);
                    context.startService(intent_gps);
                  //拦截短信
                  abortBroadcast();//拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
            }else if("#*alarm*#".equals(body)){
                //播放报警音乐
                System.out.println("播放报警音乐");
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC),0);
                if (mediaPlayer!=null) {
                    mediaPlayer.release();//释放资源
                }
                mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                //mediaPlayer.setVolume(1.0f, 1.0f);//设置最大音量,音量比例
                //mediaPlayer.setLooping(true);
                mediaPlayer.start();
                abortBroadcast();//拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
            }else if("#*wipedata*#".equals(body)){
                //远程删除数据

                System.out.println("远程删除数据");
                if (devicePolicyManager.isAdminActive(componentName)) {
                    devicePolicyManager.wipeData(0);//远程删除数据
                }
                abortBroadcast();//拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
            }else if("#*lockscreen*#".equals(body)){
                //远程锁屏
                System.out.println("远程锁屏");
                if (devicePolicyManager.isAdminActive(componentName)) {
                    devicePolicyManager.lockNow();
                }
                abortBroadcast();//拦截操作,原生android系统,国产深度定制系统中屏蔽,比如小米
            }

        }
    }
}

