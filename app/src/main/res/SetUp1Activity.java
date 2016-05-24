package bilibili.kagura.com.mobiephonesafe;

import android.content.Intent;
import android.os.Bundle;

public class SetUp1Activity extends bilibili.kagura.com.mobiephonesafe.SetUpBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up1);
    }

    @Override
    public void next_activity() {
        Intent intent = new Intent(this,SetUp2Activity.class);
        startActivity(intent);
        finish();
        //执行平移动画
        //执行界面切换动画的操作,是在startActivity或者finish之后执行
        //enterAnim : 新的界面进入的动画
        //exitAnim : 旧的界面退出的动画
        overridePendingTransition(R.anim.setup_enter_next,R.anim.setup_exit_next);
    }

    @Override
    public void pre_activity() {

    }
}
