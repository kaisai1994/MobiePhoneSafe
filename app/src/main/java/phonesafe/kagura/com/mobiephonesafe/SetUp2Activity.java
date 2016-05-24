package phonesafe.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import phonesafe.kagura.com.mobiephonesafe.ui.SettingView;


public class SetUp2Activity extends SetUpBaseActivity {

    private SettingView sv_setup2_sim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up2);
        sv_setup2_sim = (SettingView) findViewById(R.id.sv_setting_update);
        if(TextUtils.isEmpty(sp.getString("sim","")))
        {
            sv_setup2_sim.setChecked(false);
        }else {
            sv_setup2_sim.setChecked(true);
        }
        sv_setup2_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sp.edit();
                if(sv_setup2_sim.isChecked())
                {
                    editor.putString("sim","");
                    sv_setup2_sim.setChecked(false);
                }else {
                    TelephonyManager tel = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                    String sim = tel.getSimSerialNumber();
                    editor.putString("sim",sim);
                    sv_setup2_sim.setChecked(true);
                }
                editor.commit();
            }
        });
    }
    @Override
    public void next_activity() {
        if (sv_setup2_sim.isChecked()) {
            Intent intent = new Intent(this, SetUp3Activity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);
        }else{
            Toast.makeText(getApplicationContext(),"请绑定SIM卡",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(this,SetUp1Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.setup_enter_pre,R.anim.setup_exit_pre);
    }
}
