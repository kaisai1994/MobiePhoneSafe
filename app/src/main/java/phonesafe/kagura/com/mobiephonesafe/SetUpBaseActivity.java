package phonesafe.kagura.com.mobiephonesafe;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/5/15.15:30
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public abstract class SetUpBaseActivity extends Activity {
    protected SharedPreferences sp;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = getSharedPreferences("config",MODE_PRIVATE);
        mGestureDetector = new GestureDetector(this,new MyOnGestureListener());
    }

    public void pre(View v)
   {
       pre_activity();
   }
    public void next(View v)
    {
        next_activity();
    }
    public abstract void next_activity();
    public abstract void pre_activity();

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            pre_activity();
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    //e1 : 按下的事件,保存有按下的坐标
    //e2 : 抬起的事件,保存有抬起的坐标
    //velocityX : velocity 速度    在x轴上移动的速率
    private class MyOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float startX = e1.getRawX();
            float endX = e2.getRawX();
            float startY = e1.getRawY();
            float endY = e2.getRawY();
            //防止误斜划
            if ((Math.abs(startY-endY)) > 150) {
                Toast.makeText(getApplicationContext(), "不要乱滑动哦(⊙o⊙)", Toast.LENGTH_SHORT).show();
                return false;
            }
            //下一步
            if ((startX-endX) > 100) {
                next_activity();
            }
            //上一步
            if ((endX-startX) > 100) {
                pre_activity();
            }
            //true if the event is consumed, else false
            //true : 事件执行     false:拦截事件,事件不执行
            return true;
        }
    }
}
