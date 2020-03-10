package nelson.com.mydaily;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import nelson.com.mydaily.bean.event.ClickIndexAdapterBean;
import nelson.com.mydaily.db.Opearations;
import nelson.com.mydaily.dialog.BottomToTopDialog;
import nelson.com.mydaily.ui.dashboard.DashboardFragment;
import nelson.com.mydaily.ui.home.HomeFragment;
import nelson.com.mydaily.ui.notifications.NotificationsFragment;
import nelson.com.mydaily.util.KeyboardUtil;
import nelson.com.mydaily.util.TextUtil;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
        TextView.OnEditorActionListener {

    private Map<Integer, Fragment> fragments = new HashMap<>();
    private FragmentManager fragmentManager;
    private Fragment showFragment = null;
    private Calendar calendar;
    private EditText moneyEt;
    private int index = -1;
    private DatePickerDialog datePickerDialog;
    private boolean isUpdate;
    private String uuid;
    private BottomToTopDialog bottomToTopDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        showFragment = new HomeFragment();
        fragments.put(0,showFragment);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frl,fragments.get(0));
        transaction.commit();

        navView.setOnNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int index = -1;
        switch (menuItem.getItemId()){
            case R.id.navigation_home:
                if (fragments.get(0) == null){
                    fragments.put(0,new HomeFragment());
                    transaction.add(R.id.frl,fragments.get(0));
                }
                index = 0;
                break;
            case R.id.navigation_dashboard:
                if (fragments.get(1) == null){
                    fragments.put(1,new DashboardFragment());
                    transaction.add(R.id.frl,fragments.get(1));
                }
                index = 1;
                break;
            case R.id.navigation_notifications:
                if (fragments.get(2) == null){
                    fragments.put(2,new NotificationsFragment());
                    transaction.add(R.id.frl,fragments.get(2));
                }
                index = 2;
                break;
        }
        if (index != -1){
            transaction.hide(showFragment);
            showFragment = fragments.get(index);
            transaction.show(showFragment);
            transaction.commit();
        }

        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit:
                edit(-1,0,0);
                break;
        }
        return true;
    }

    private void edit(int idType,float money,long time) {
        View editLayout = LayoutInflater.from(this).inflate(R.layout.edit_layout,null);
        moneyEt = editLayout.findViewById(R.id.money_et);
        if (money != 0){
            moneyEt.setText(String.valueOf(money));
            moneyEt.setSelection(String.valueOf(money).length());
        }
        moneyEt.setOnEditorActionListener(this);
        moneyEt.requestFocus();
        final TextView selectTimeTv = editLayout.findViewById(R.id.selectTimeTv);
        calendar = Calendar.getInstance();
        if (time > 0){
            calendar.setTimeInMillis(time);
        }
        selectTimeTv.setText(calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+ calendar.get(Calendar.DAY_OF_MONTH));
        selectTimeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        selectTimeTv.setText(year+"/"+(month+1)+"/"+ dayOfMonth);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        RadioGroup group = editLayout.findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                index = radioGroup.indexOfChild(radioGroup.findViewById(i));
            }
        });
        if (idType != -1){
            RadioButton rb = (RadioButton) group.getChildAt(idType);
            rb.setChecked(true);
        }
        bottomToTopDialog = new BottomToTopDialog(this,editLayout);
        bottomToTopDialog.show();
        moneyEt.postDelayed(new Runnable() {
            @Override
            public void run() {
                KeyboardUtil.showInputMethod(MainActivity.this, moneyEt);
            }
        },400);

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (i){
            case EditorInfo
                    .IME_ACTION_DONE:
                save();
                break;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clickAdapterItem(ClickIndexAdapterBean bean){
        if (bean != null){
            isUpdate = true;
            uuid = bean.getDetailBean().getUuid();
            edit(bean.getDetailBean().getIdType(),bean.getDetailBean().getMoney(),
                    bean.getDetailBean().getTime());
        }
    }

    private void save(){
        float money = 0;
        if (moneyEt != null){
            String m = moneyEt.getText().toString();
            if (!TextUtil.isEmpty(m)){
                money = Float.parseFloat(m);
            }
        }
        if (money == 0){
            Toast.makeText(this,"请填写金额",Toast.LENGTH_SHORT).show();
            return;
        }
        long time = calendar.getTimeInMillis();
        if (index == -1){
            Toast.makeText(this,"请选择种类",Toast.LENGTH_SHORT).show();
            return;
        }
        String typeName = "";
        switch (index){
            case 0:
                typeName = "衣服";
                break;
            case 1:
                typeName = "护肤";
                break;
            case 2:
                typeName = "水果";
                break;
            case 3:
                typeName = "车费";
                break;
            case 4:
                typeName = "饭菜";
                break;
        }
        if (isUpdate && !TextUtil.isEmpty(uuid)){
            Opearations.getInstance().updateBill(uuid,index,typeName,money,time);
            isUpdate = false;
            uuid = null;
        } else {
            Opearations.getInstance().saveBill(index,typeName,money,time);
        }
        Toast.makeText(this,"已保存",Toast.LENGTH_SHORT).show();
        moneyEt.postDelayed(new Runnable() {
            @Override
            public void run() {
                index = -1;
                calendar = null;
                bottomToTopDialog.dismiss();
            }
        },200);
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}


