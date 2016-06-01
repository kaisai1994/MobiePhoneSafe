package phonesafe.kagura.com.mobiephonesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/5/17.20:51
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public class BootCompleteReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("手机重启了");
        SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        if (sp.getBoolean("protected", false)) {
            String sp_sim = sp.getString("sim", "");
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String sim = tel.getSimSerialNumber();
            if (!TextUtils.isEmpty(sp_sim) && !TextUtils.isEmpty(sim)) {
                if (!sp_sim.equals(sim)) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("5556", null, "HELP ME", null, null);
                }
            }
        }
    }
}
