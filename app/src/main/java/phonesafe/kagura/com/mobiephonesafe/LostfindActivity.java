package phonesafe.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LostfindActivity extends AppCompatActivity {
    //手机防盗
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        if(sp.getBoolean("first",true))
        {
            Intent intent = new Intent(this,SetUp1Activity.class);
            startActivity(intent);
            finish();
        }else {
            setContentView(R.layout.activity_lostfind);
        }
        TextView tv_lostfind_safenum = (TextView) findViewById(R.id.tv_lostfind_safenum);
        ImageView iv_lostfind_protected = (ImageView) findViewById(R.id.iv_lostfind_protected);
        tv_lostfind_safenum.setText(sp.getString("safenum", ""));
        //设置防盗保护是否开启状态
        //获取保存的防盗保护状态
        boolean b = sp.getBoolean("protected", false);
        //根据获取防盗保护状态设置相应显示图片
        if (b) {
            //开启防盗保护
            iv_lostfind_protected.setImageResource(R.drawable.lock);
        }else{
            //关闭防盗保护
            iv_lostfind_protected.setImageResource(R.drawable.unlock);
        }
    }
    public void resetup(View v){
        Intent intent = new Intent(this,SetUp1Activity.class);
        startActivity(intent);
        finish();
    }
}
