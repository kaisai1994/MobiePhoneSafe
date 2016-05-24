package bilibili.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SetUp4Activity extends bilibili.kagura.com.mobiephonesafe.SetUpBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up4);
    }

    @Override
    public void next_activity() {
        //保存用户第一次进入手机防盗模块设置向导的状态,frist
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("first",false);
        editor.commit();
        Intent intent = new Intent(this, bilibili.kagura.com.mobiephonesafe.LostfindActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(this, bilibili.kagura.com.mobiephonesafe.SetUp3Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.setup_enter_pre,R.anim.setup_exit_pre);
    }
}
