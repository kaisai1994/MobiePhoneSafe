package phonesafe.kagura.com.mobiephonesafe.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @version $Rev$
 * @auther kagura
 * @time 2016/5/11.11:20
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateData $Author$
 * @updatedes ${TODO}
 */
public class HomeTextView extends TextView {
    public HomeTextView(Context context) {
        super(context);
    }

    public HomeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
