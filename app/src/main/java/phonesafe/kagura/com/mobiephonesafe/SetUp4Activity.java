package phonesafe.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SetUp4Activity extends SetUpBaseActivity {

    private CheckBox cb_setup4_protected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up4);
        cb_setup4_protected = (CheckBox) findViewById(R.id.cb_setup4_protected);
        if(sp.getBoolean("protected",false))
        {
            cb_setup4_protected.setText("您已经开启了防盗保护");
            cb_setup4_protected.setChecked(true);
        }else {
            cb_setup4_protected.setText("您还没有开启防盗保护");
            cb_setup4_protected.setChecked(false);
        }

        cb_setup4_protected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();

                if(isChecked)
                {
                    cb_setup4_protected.setText("您已经开启了防盗保护");
                    cb_setup4_protected.setChecked(true);//程序严谨性
                    //保存用户选中的状态
                    edit.putBoolean("protected", true);
                }else{
                    //关闭防盗保护
                    cb_setup4_protected.setText("您还没有开启防盗保护");
                    cb_setup4_protected.setChecked(false);//程序严谨性
                    edit.putBoolean("protected", false);
                }
                edit.commit();
            }
        });
    }

    @Override
    public void next_activity() {
        //保存用户第一次进入手机防盗模块设置向导的状态,frist
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first",false);
        editor.commit();
        Intent intent = new Intent(this,LostfindActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(this,SetUp3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.setup_enter_pre,R.anim.setup_exit_pre);
    }
}
