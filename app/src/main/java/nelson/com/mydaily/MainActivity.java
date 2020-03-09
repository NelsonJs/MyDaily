package nelson.com.mydaily;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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

import java.util.HashMap;
import java.util.Map;

import nelson.com.mydaily.dialog.BottomToTopDialog;
import nelson.com.mydaily.ui.dashboard.DashboardFragment;
import nelson.com.mydaily.ui.home.HomeFragment;
import nelson.com.mydaily.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private Map<Integer, Fragment> fragments = new HashMap<>();
    private FragmentManager fragmentManager;
    private Fragment showFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                View editLayout = LayoutInflater.from(this).inflate(R.layout.edit_layout,null);
                BottomToTopDialog bottomToTopDialog = new BottomToTopDialog(this,editLayout);
                bottomToTopDialog.show();
                break;
        }
        return true;
    }
}
