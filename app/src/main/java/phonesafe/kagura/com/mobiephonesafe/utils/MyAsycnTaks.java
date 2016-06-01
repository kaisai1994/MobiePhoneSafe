package phonesafe.kagura.com.mobiephonesafe.utils;


import android.os.Handler;
import android.os.Message;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/6/1.11:41
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public abstract class MyAsycnTaks {
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           postTask();
        }
    };
    /**
     * 在子线程之前执行的方法
    */
    public abstract void preTask();

    /**
     * 在子线程中执行的方法
     */
    public abstract void doinBack();

    /**
     * 在子线程之后执行的方法
     */
    public abstract void postTask();

    /**
     * 执行
     */
    public void execute()
    {
        preTask();
        new Thread()
        {
            @Override
            public void run() {
                doinBack();
                mHandler.sendEmptyMessage(0);
            };
        }.start();
    }

}
