package nelson.com.mydaily.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Random;

import nelson.com.mydaily.R;
import nelson.com.mydaily.adapter.IndexAdapter;
import nelson.com.mydaily.bean.db.DetailBean;
import nelson.com.mydaily.bean.event.EditBean;
import nelson.com.mydaily.db.Opearations;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private IndexAdapter indexAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);
        homeViewModel.getData().observe(this, new Observer<List<DetailBean>>() {
            @Override
            public void onChanged(List<DetailBean> detailBeans) {
                if (indexAdapter == null){
                    indexAdapter = new IndexAdapter(detailBeans);
                    recyclerView.setAdapter(indexAdapter);
                } else {
                    indexAdapter.notifyDataSetChanged();
                }

            }
        });
        return root;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAdapter(EditBean editBean){
        if (editBean != null && editBean.isEdit()){
            if (indexAdapter != null){
                indexAdapter.notifyDataSetChanged();
            }
           // homeViewModel.getData().getValue().addAll(Opearations.getInstance().getList());
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}