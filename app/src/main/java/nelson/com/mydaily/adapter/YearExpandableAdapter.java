package nelson.com.mydaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import nelson.com.mydaily.R;
import nelson.com.mydaily.YearBean;
import nelson.com.mydaily.bean.MonthBean;

/**
 * Created By PJSONG
 * On 2020/3/11 16:24
 */
public class YearExpandableAdapter extends BaseExpandableListAdapter {
    private List<YearBean> yearBeans;
    private Map<Integer, List<MonthBean>> monthBeans;
    private Context mContext;

    public YearExpandableAdapter(List<YearBean> yearBeans, Map<Integer, List<MonthBean>> monthBeans) {
        this.yearBeans = yearBeans;
        this.monthBeans = monthBeans;
    }

    @Override
    public int getGroupCount() {
        return yearBeans == null ? 0 : yearBeans.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return monthBeans == null ? 0 : (monthBeans.get(i) == null ? 0 : monthBeans.get(i).size());
    }

    @Override
    public Object getGroup(int i) {
        return yearBeans.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return monthBeans.get(i).get(i1);
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
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.month_layout,null,false);
        MonthHolder monthHolder = new MonthHolder();
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class YearHolder {
        private TextView yearTv,moneyTv;
    }
    class MonthHolder {
        private TextView yearTv,moneyTv;
    }
}
