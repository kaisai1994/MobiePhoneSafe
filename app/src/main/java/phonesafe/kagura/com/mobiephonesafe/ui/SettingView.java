package phonesafe.kagura.com.mobiephonesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import phonesafe.kagura.com.mobiephonesafe.R;


/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/5/11.12:26
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public class SettingView extends RelativeLayout {
    private TextView tv_setting_title;
    private TextView tv_seting_des;
    private CheckBox cb_setting_update;
    private String des_on;
    private String des_off;
    public SettingView(Context context) {
        super(context);
        init();
    }

    public SettingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        String title = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","title");
        des_on = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","des_on");
        des_off = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto","des_off");

        tv_setting_title.setText(title);
        if(isChecked())
        {
            tv_seting_des.setText(des_on);
        }else{
            tv_seting_des.setText(des_off);
        }
    }

    public SettingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init()
    {
        View view = View.inflate(getContext(), R.layout.settingview,this);
        tv_setting_title = (TextView) view.findViewById(R.id.tv_setting_title);
        tv_seting_des = (TextView) view.findViewById(R.id.tv_setting_des);
        cb_setting_update = (CheckBox) view.findViewById(R.id.cb_setting_update);
    }
    public void setTitle(String title)
    {
        tv_setting_title.setText(title);
    }
    public void setDes(String des)
    {
        tv_seting_des.setText(des);
    }
    public void setChecked(boolean isChecked){
        //设置checkbox的状态
        cb_setting_update.setChecked(isChecked);
        //其实就是把sv_setting_update.setDes("打开提示更新");封装到了setChecked方法中
        if (isChecked()) {
            tv_seting_des.setText(des_on);
        }else{
            tv_seting_des.setText(des_off);
        }
    }
    public boolean isChecked()
    {
        return cb_setting_update.isChecked();
    }
}
