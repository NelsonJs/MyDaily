package nelson.com.mydaily.ui.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import nelson.com.mydaily.R;
import nelson.com.mydaily.bean.db.DetailBean;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        DetailBean detailBean = (DetailBean) getIntent().getSerializableExtra("data");
        if (detailBean == null){
            return;
        }
    }
}
