package nelson.com.mydaily.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ExpandableListView;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import nelson.com.mydaily.R;
import nelson.com.mydaily.YearBean;
import nelson.com.mydaily.adapter.ExpandableAdapter;
import nelson.com.mydaily.adapter.IndexAdapter;
import nelson.com.mydaily.adapter.YearExpandableAdapter;
import nelson.com.mydaily.bean.DayBean;
import nelson.com.mydaily.bean.MonthBean;
import nelson.com.mydaily.bean.db.DetailBean;
import nelson.com.mydaily.bean.event.EditBean;
import nelson.com.mydaily.custom.CustomYearExpandabelListView;
import nelson.com.mydaily.db.Opearations;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private CustomYearExpandabelListView expandableListView;
    private YearExpandableAdapter yearExpandableAdapter;
    //private IndexAdapter indexAdapter;

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
        expandableListView = root.findViewById(R.id.expandableListView);
        homeViewModel.getData().observe(this, new Observer<List<DetailBean>>() {
            @Override
            public void onChanged(List<DetailBean> detailBeans) {
                List<Integer> yearInts = new ArrayList<>();
                Map<Integer,List<DetailBean>> yearMap = new HashMap<>();//根据年份，将数据分类
                for (DetailBean d : detailBeans) {
                    int yearNum = d.getYear();
                    if (yearMap.containsKey(yearNum)){
                        yearMap.get(yearNum).add(d);
                    } else {
                        List<DetailBean> years = new ArrayList<>();
                        years.add(d);
                        yearMap.put(yearNum,years);
                        yearInts.add(yearNum);
                    }
                }
                Collections.sort(yearInts);

                Calendar calendar = Calendar.getInstance();
                List<YearBean> yearBeans = new ArrayList<>();
                for (Integer y : yearInts) {
                    YearBean yearBean = new YearBean();
                    yearBean.setYear(y+"");
                    List<DetailBean> sameYearBean = yearMap.get(y);//同一年的

                    Map<String,List<DetailBean>> monthDayMap = new HashMap<>(); //月 跟 对应的天
                    List<Integer> monthInts = new ArrayList<>();
                    for (DetailBean innerSameYearBean : sameYearBean) {//获取月份
                        calendar.setTimeInMillis(innerSameYearBean.getTime());

                        int month = calendar.get(Calendar.MONTH)+1;
                        if (monthDayMap.containsKey(String.valueOf(month))){
                            monthDayMap.get(String.valueOf(month)).add(innerSameYearBean);
                        } else {
                            monthInts.add(month);

                            List<DetailBean> days = new ArrayList<>();
                            days.add(innerSameYearBean);
                            monthDayMap.put(String.valueOf(month), days);
                        }
                    }
                    Collections.sort(monthInts);
                    List<MonthBean> monthBeans = new ArrayList<>();
                    for (Integer month : monthInts) {
                        MonthBean monthBean = new MonthBean();
                        monthBean.setMonth(String.valueOf(month));
                        monthBean.setDayBeanList(monthDayMap.get(String.valueOf(month)));
                        monthBeans.add(monthBean);
                    }
                    yearBean.setMonthBeans(monthBeans);
                    yearBeans.add(yearBean);
                }

                if (yearExpandableAdapter == null){
                    yearExpandableAdapter = new YearExpandableAdapter(yearBeans, expandableListView);
                    expandableListView.setAdapter(yearExpandableAdapter);
                    expandableListView.setHeaderView(LayoutInflater.from(getContext()).inflate(R.layout.year_layout, expandableListView,false));
                } else {
                    yearExpandableAdapter.notifyDataSetChanged();
                }
               //ExpandableAdapter expandableAdapter = new ExpandableAdapter()
            }
        });


        /*List<YearBean> yearBeans = new ArrayList<>();
        Map<Integer,List<MonthBean>> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            List<MonthBean> monthBeans = new ArrayList<>();
            for (int j = 0; j < 7; j++) {
                MonthBean monthBean = new MonthBean();
                Map<Integer,List<DayBean>> dayMap = new HashMap<>();
                List<DayBean> dayBeans = new ArrayList<>();
                for (int k = 0; k < 6; k++) {
                    DayBean dayBean = new DayBean();
                    dayBeans.add(dayBean);
                }
                dayMap.put(j,dayBeans);
                monthBean.setDayMap(dayMap);
                monthBeans.add(monthBean);
            }
            map.put(i,monthBeans);
            YearBean yearBean = new YearBean();
            yearBean.setYear("201"+i+"年");
            yearBeans.add(yearBean);
        }*/



        return root;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshAdapter(EditBean editBean){
        if (editBean != null && editBean.isEdit()){
            if (yearExpandableAdapter != null){
                yearExpandableAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}