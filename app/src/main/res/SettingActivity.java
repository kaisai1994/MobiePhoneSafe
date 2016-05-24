package bilibili.kagura.com.mobiephonesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import bilibili.kagura.com.mobiephonesafe.ui.SettingView;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/5/11.12:12
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public class SettingActivity extends Activity {
    private SettingView sv_setting_update;
    protected SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        sv_setting_update = (SettingView) findViewById(R.id.sv_setting_update);
       // sv_setting_update.setTitle("提示更新");

        if(sp.getBoolean("update",true))
        {
            //sv_setting_update.setDes("打开提示更新");
            sv_setting_update.setChecked(true);
        }else {
            //sv_setting_update.setDes("关闭提示更新");
            sv_setting_update.setChecked(false);
        }

        sv_setting_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();
                if(sv_setting_update.isChecked())
                {
                    sv_setting_update.setDes("关闭提示更新");
                    sv_setting_update.setChecked(false);
                    edit.putBoolean("update",false);
                }else {
                    sv_setting_update.setDes("打开提示更新");
                    sv_setting_update.setChecked(true);
                    edit.putBoolean("update",true);
                }
                edit.commit();
            }
        });


    }
}
