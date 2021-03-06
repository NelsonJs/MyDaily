package nelson.com.mydaily.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import nelson.com.mydaily.R;
import nelson.com.mydaily.YearBean;
import nelson.com.mydaily.bean.DayBean;
import nelson.com.mydaily.bean.MonthBean;
import nelson.com.mydaily.bean.db.DetailBean;

/**
 * Created By PJSONG
 * On 2020/3/11 16:24
 */
public class MonthExpandableAdapter extends BaseExpandableListAdapter {
    private List<MonthBean> monthBeans;
    private List<DetailBean> dayBeans;
    private Context mContext;

    public MonthExpandableAdapter(List<MonthBean> monthBeans, List<DetailBean> dayBeans) {
        this.monthBeans = monthBeans;
        this.dayBeans = dayBeans;
    }

    @Override
    public int getGroupCount() {
        return monthBeans == null ? 0 : monthBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return dayBeans == null ? 0 : dayBeans.size();
    }

    @Override
    public Object getGroup(int i) {
        return monthBeans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return dayBeans.get(i1);
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
        MonthHolder monthHolder = new MonthHolder();
        monthHolder.monthTv = convertView.findViewById(R.id.monthTv);
        monthHolder.monthTv.setText(monthBeans.get(groupPosition).getMonth()+"月");
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_day_layout,null,false);
        DayHolder dayHolder = new DayHolder();
        dayHolder.nameTv = convertView.findViewById(R.id.nameTv);
        dayHolder.moneyTv = convertView.findViewById(R.id.moneyTv);
        dayHolder.timeTv = convertView.findViewById(R.id.timeTv);
        dayHolder.moneyTv.setText(dayBeans.get(childPosition).getMoney()+"元");
        dayHolder.nameTv.setText(dayBeans.get(childPosition).getTypeName());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(dayBeans.get(childPosition).getTime());
        dayHolder.timeTv.setText(calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
    class MonthHolder {
        private TextView monthTv,moneyTv;
    }
    class DayHolder {
        private TextView yearTv,moneyTv,nameTv,timeTv;
    }
}
