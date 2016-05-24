package bilibili.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

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

    }
    public void resetup(View v){
        Intent intent = new Intent(this,SetUp1Activity.class);
        startActivity(intent);
        finish();
    }
}
