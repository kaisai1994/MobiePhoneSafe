package bilibili.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SetUp3Activity extends bilibili.kagura.com.mobiephonesafe.SetUpBaseActivity {

    private EditText et_setup3_safenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up3);
        et_setup3_safenum = (EditText) findViewById(R.id.et_setup3_safenum);
        et_setup3_safenum.setText(sp.getString("safenum", ""));
    }

    @Override
    public void next_activity() {

        String safenum = et_setup3_safenum.getText().toString().trim();
        if (TextUtils.isEmpty(safenum)) {
            Toast.makeText(getApplicationContext(), "安全密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("safenum", safenum);
        editor.commit();
        Intent intent = new Intent(this, SetUp4Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.setup_enter_next, R.anim.setup_exit_next);
    }

    @Override
    public void pre_activity() {
        Intent intent = new Intent(this, SetUp2Activity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.setup_enter_pre, R.anim.setup_exit_pre);
    }

    public void selectContacts(View view) {
        Intent intent = new Intent(this, bilibili.kagura.com.mobiephonesafe.ContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //接受选择联系人界面传递过来的数据,null.方法      参数为null
            String num = data.getStringExtra("num");
            //将获取到的号码,设置给安全号码输入框
            et_setup3_safenum.setText(num);
        }
    }
}
