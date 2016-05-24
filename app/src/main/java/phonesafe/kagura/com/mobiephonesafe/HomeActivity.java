package phonesafe.kagura.com.mobiephonesafe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import phonesafe.kagura.com.mobiephonesafe.utils.MD5Util;

/**
 * @author Administrator
 * @time 2016/5/10 18:24
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 */

public class HomeActivity extends Activity{
    private GridView gv_home_gridview;
    private AlertDialog dialog;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        gv_home_gridview = (GridView) findViewById(R.id.gv_home_gridview);
        gv_home_gridview.setAdapter(new MyAdapter());
        gv_home_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (TextUtils.isEmpty(sp.getString("password","")))
                        {
                            showSetPasswordDialog();
                        }else{
                            showEnterPasswordDialog();
                        }
                        break;
                    case 8:
                        Intent intent = new Intent(HomeActivity.this,SettingActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    int count = 0;
    /**
     * 输入密码对话框
     */
    private void showEnterPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(getApplicationContext(),R.layout.dialog_enterpassword,null);
        final EditText et_setpassword_password = (EditText) view.findViewById(R.id.et_setpassword_password);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        ImageView iv_enterpassword_hide = (ImageView) view.findViewById(R.id.iv_enterpassword_hide);
        iv_enterpassword_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(count%2 == 0)
                {
                    et_setpassword_password.setInputType(0);
                }else{
                    et_setpassword_password.setInputType(129);//0x81
                }
                count++;
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_setpassword_password.getText().toString().trim();
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sp_password = sp.getString("password","");
                if(MD5Util.passwordMD5(password).equals(sp_password))
                {
                    Intent intent = new Intent(HomeActivity.this,LostfindActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "密码正确", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }


    /**
     * 设置密码对话框
     */
    private void showSetPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        View view = View.inflate(getApplicationContext(),R.layout.dialog_setpassword,null);
        final EditText et_setpassword_password = (EditText) view.findViewById(R.id.et_setpassword_password);
        final EditText et_setpassword_confrim = (EditText) view.findViewById(R.id.et_setpassword_confrim);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = et_setpassword_password.getText().toString().trim();
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String confrim_password = et_setpassword_confrim.getText().toString().trim();
                if(password.equals(confrim_password))
                {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("password", MD5Util.passwordMD5(password));
                    edit.commit();
                    dialog.dismiss();;
                    Toast.makeText(getApplicationContext(), "密码设置成功", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.setView(view,0,0,0,0);
        dialog.show();
    }

    class MyAdapter extends BaseAdapter
    {
        int[] imageId = {R.drawable.safe,R.drawable.callmsgsafe,R.drawable.app,
        R.drawable.taskmanager,R.drawable.netmanager,R.drawable.trojan,R.drawable.sysoptimize,
        R.drawable.atools,R.drawable.settings};
        String[] names = {"手机防盗","通讯卫士","软件管理","进程管理","流量统计","手机杀毒","缓存清理","高级工具","设置中心"};

        @Override
        public int getCount() {
            return 9;
        }
        @Override
        public Object getItem(int position) {
            return null;
        }
        @Override
        public long getItemId(int position) {
            return 0;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),R.layout.item_home,null);
            ImageView iv_itemhome_icon = (ImageView) view.findViewById(R.id.iv_itemhome_icon);
            TextView tv_itemhome_text = (TextView) view.findViewById(R.id.tv_home_text);
            iv_itemhome_icon.setImageResource(imageId[position]);
            tv_itemhome_text.setText(names[position]);
            return view;
        }
    }
}
