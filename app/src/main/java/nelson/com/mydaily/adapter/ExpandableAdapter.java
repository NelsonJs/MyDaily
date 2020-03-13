package nelson.com.mydaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import nelson.com.mydaily.R;
import nelson.com.mydaily.YearBean;
import nelson.com.mydaily.bean.MonthBean;
import nelson.com.mydaily.custom.CustomExpandabelListView;
import nelson.com.mydaily.custom.CustomYearExpandabelListView;

/**
 * Created By PJSONG
 * On 2020/3/11 16:24
 */
public class ExpandableAdapter extends BaseExpandableListAdapter implements CustomYearExpandabelListView.HeaderAdapter {
    private Context mContext;
    private CustomYearExpandabelListView listView;
    private List<MonthBean> monthBeans;

    public ExpandableAdapter(List<MonthBean> monthBeans, CustomYearExpandabelListView listView) {
        this.monthBeans = monthBeans;
        this.listView = listView;
    }

    @Override
    public int getGroupCount() {
        return monthBeans == null ? 0 : monthBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return monthBeans.get(i).getDayBeanList().size();
    }

    @Override
    public Object getGroup(int i) {
        return monthBeans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return monthBeans.get(i).getDayBeanList().get(i1);
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
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_month_layout,null,false);
        MonthHolder yearHolder = new MonthHolder();
        yearHolder.monthTv = convertView.findViewById(R.id.monthTv);
        yearHolder.monthTv.setText(monthBeans.get(groupPosition).getMonth());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_day_layout,null,false);
        DayHolder dayHolder = new DayHolder();
        dayHolder.nameTv = convertView.findViewById(R.id.nameTv);
        dayHolder.moneyTv = convertView.findViewById(R.id.moneyTv);
        dayHolder.timeTv = convertView.findViewById(R.id.timeTv);
        dayHolder.moneyTv.setText(monthBeans.get(groupPosition).getDayBeanList().get(childPosition).getMoney()+"元");
        dayHolder.nameTv.setText(monthBeans.get(groupPosition).getDayBeanList().get(childPosition).getTypeName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(monthBeans.get(groupPosition).getDayBeanList().get(childPosition).getTime());
        dayHolder.timeTv.setText(calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH));

        return convertView;
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
        if (groupPosition > -1) {
            ((TextView) header.findViewById(R.id.yearTv))
                    .setText("头部");
        }
    }
    class DayHolder {
        private TextView monthTv,moneyTv,nameTv,timeTv;
    }
    class MonthHolder {
        private TextView monthTv,moneyTv;
    }
}
