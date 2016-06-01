package phonesafe.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.HashMap;
import java.util.List;

import phonesafe.kagura.com.mobiephonesafe.engine.ContactEngine;
import phonesafe.kagura.com.mobiephonesafe.utils.MyAsycnTaks;

public class ContactActivity extends AppCompatActivity {

    @ViewInject(R.id.lv_contact_contacts)
    private ListView lv_contact_contacts;
    private List<HashMap<String, String>> list;
    //注解初始化控件,类似Spring,注解的形式生成javabean,内部:通过反射的方式执行了findviewById
    @ViewInject(R.id.loading)
    private ProgressBar loading;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg) {
            lv_contact_contacts.setAdapter(new Myadapter());
            //数据显示完成,隐藏进度条
            loading.setVisibility(View.INVISIBLE);
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        ViewUtils.inject(this);
        /**
        //在加载数据之前显示进度条
        loading.setVisibility(View.VISIBLE);
        new Thread(){
            public void run() {
                //获取联系人
                list = ContactEngine.getAllContactInfo(getApplicationContext());
                //获取完联系人的时候给handler发送一个消息,在handler中去setadapter
                handler.sendEmptyMessage(0);
            };
        }.start();

        //lv_contact_contacts = (ListView) findViewById(R.id.lv_contact_contacts);
        */
        new MyAsycnTaks()
        {
            @Override
            public void preTask() {
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void doinBack() {
                list = ContactEngine.getAllContactInfo(getApplicationContext());
            }

            @Override
            public void postTask() {
                lv_contact_contacts.setAdapter(new Myadapter());
                //数据显示完成,隐藏进度条
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void execute() {
                super.execute();
            }
        }.execute();

      /*//参数:提高扩展性
		//参数1:子线程执行所需的参数
		//参数2:执行的进度
		//参数3:子线程执行的结果
		//异步加载框架:面试必问,手写异步加载框架,百度面试题:当他执行到多少个操作的时候就和new一个线程没区别了,5个
		new AsyncTask<String, Integer, String>(){

			//子线程之前执行的方法
			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}
			//在子线程之中执行的方法
			@Override
			protected String doInBackground(String... params) {
				// TODO Auto-generated method stub
				return null;
			}
			//在子线程之后执行的方法
			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
			}
			//显示当前加载的进度
			@Override
			protected void onProgressUpdate(Integer... values) {
				// TODO Auto-generated method stub
				super.onProgressUpdate(values);
			}

		};*/

        lv_contact_contacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //将点击联系人的号码传递给设置安全号码界面
                Intent intent = new Intent();
                intent.putExtra("num", list.get(position).get("phone"));
                //将数据传递给设置安全号码界面
                //设置结果的方法,会将结果传递给调用当前activity的activity
                setResult(RESULT_OK, intent);
                //移出界面
                finish();
            }
        });
    }

    private class Myadapter extends BaseAdapter {
        // 获取条目的个数
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }
        // 设置条目的样式
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.item_contact, null);
            //初始化控件
            //view.findViewById 是从item_contact找控件,findViewById是从activity_contacts找控件
            TextView tv_itemcontact_name = (TextView) view.findViewById(R.id.tv_itemcontact_name);
            TextView tv_itemcontact_phone = (TextView) view.findViewById(R.id.tv_itemcontact_phone);
            //设置控件的值
            tv_itemcontact_name.setText(list.get(position).get("name"));//根据条目的位置从list集合中获取相应的数据
            tv_itemcontact_phone.setText(list.get(position).get("phone"));
            return view;
        }

        // 获取条目对应的数据
        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return null;
        }

        // 获取条目的id
        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return 0;
        }
    }
}
