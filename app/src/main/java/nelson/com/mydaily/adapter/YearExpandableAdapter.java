package nelson.com.mydaily.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import nelson.com.mydaily.R;
import nelson.com.mydaily.YearBean;
import nelson.com.mydaily.bean.DayBean;
import nelson.com.mydaily.bean.MonthBean;
import nelson.com.mydaily.custom.CustomExpandabelListView;
import nelson.com.mydaily.custom.CustomYearExpandabelListView;

/**
 * Created By PJSONG
 * On 2020/3/11 16:24
 */
public class YearExpandableAdapter extends BaseExpandableListAdapter implements CustomYearExpandabelListView.HeaderAdapter {
    private List<YearBean> yearBeans;
    private Context mContext;
    private CustomYearExpandabelListView listView;

    public YearExpandableAdapter(List<YearBean> yearBeans,CustomYearExpandabelListView listView) {
        this.yearBeans = yearBeans;
        this.listView = listView;
    }

    @Override
    public int getGroupCount() {
        return yearBeans == null ? 0 : yearBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return yearBeans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return yearBeans.get(i).getMonthBeans().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        mContext = parent.getContext();
        convertView = LayoutInflater.from(mContext).inflate(R.layout.year_layout,null,false);
        YearHolder yearHolder = new YearHolder();
        yearHolder.yearTv = convertView.findViewById(R.id.yearTv);
        yearHolder.yearTv.setText(yearBeans.get(groupPosition).getYear()+"年");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        /*convertView = LayoutInflater.from(mContext).inflate(R.layout.month_layout,null,false);
        MonthHolder monthHolder = new MonthHolder();
        monthHolder.expandableListView = convertView.findViewById(R.id.expandableListView);
        Map<Integer, List<DayBean>> dayMap = monthBeans.get(groupPosition).get(childPosition).getDayMap();
        MonthExpandableAdapter monthExpandableAdapter = new MonthExpandableAdapter(monthBeans.get(groupPosition),dayMap);
        monthHolder.expandableListView.setAdapter(monthExpandableAdapter);*/
        CustomExpandabelListView expandableListView = (CustomExpandabelListView) convertView;
        if (convertView == null){
            expandableListView = new CustomExpandabelListView(mContext);
            expandableListView.setGroupIndicator(null);
        }
       // Map<Integer, List<DayBean>> dayMap = yearBeans.get(groupPosition).getMonthBeans().get(childPosition).getDayMap();
        MonthExpandableAdapter monthExpandableAdapter = new MonthExpandableAdapter(yearBeans.get(groupPosition).getMonthBeans(),yearBeans.get(groupPosition).getMonthBeans().get(childPosition).getDayBeanList());
        expandableListView.setAdapter(monthExpandableAdapter);
        return expandableListView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public int getHeaderState(int groupPosition, int childPosition) {
        final int childCount = getChildrenCount(groupPosition);
        if (childPosition == childCount - 1) {
            return PINNED_HEADER_PUSHED_UP;
        } else if (childPosition == -1
                && !listView.isGroupExpanded(groupPosition)) {
            return PINNED_HEADER_GONE;
        } else {
            return PINNED_HEADER_VISIBLE;
        }
    }

    @Override
    public void configureHeader(View header, int groupPosition, int childPosition, int alpha) {
        Log.e("nelson","configureHeader-----"+groupPosition+"---"+childPosition);
        if (groupPosition > -1) {
            ((TextView) header.findViewById(R.id.yearTv))
                    .setText("头部");
        }
    }

    class YearHolder {
        private TextView yearTv,moneyTv;
    }
    class MonthHolder {
        private ExpandableListView expandableListView;
    }
}
