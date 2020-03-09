package nelson.com.mydaily.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import nelson.com.mydaily.R;


/**
 * 从底部弹出的dialog
 */
public class BottomToTopDialog extends Dialog {
    private View layoutView;
    private Context context;
    private int height;

    public BottomToTopDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BottomToTopDialog(@NonNull Context context, View layoutView) {
        super(context, R.style.theme_Dialog_From_Bottom);
        this.layoutView = layoutView;
        this.context = context;
    }

    public BottomToTopDialog(@NonNull Context context, View layoutView, int height) {
        super(context, R.style.theme_Dialog_From_Bottom);
        this.layoutView = layoutView;
        this.context = context;
        this.height = height;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutView);
        initValues();
    }
    private void initValues() {
        Window window = getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        lp.width = dm.widthPixels;
        if (height > 0){
            lp.height = height;
        }
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }
}
