package nelson.com.mydaily.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created By PJSONG
 * On 2020/3/12 13:29
 */
public class CustomExpandabelListView extends ExpandableListView {
    public CustomExpandabelListView(Context context) {
        super(context);
    }

    public CustomExpandabelListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomExpandabelListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomExpandabelListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 解决显示不全的问题
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2
                , MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
