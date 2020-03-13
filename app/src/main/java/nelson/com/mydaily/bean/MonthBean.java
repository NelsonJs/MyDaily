package nelson.com.mydaily.bean;

import java.util.List;
import java.util.Map;

import nelson.com.mydaily.bean.db.DetailBean;

/**
 * Created By PJSONG
 * On 2020/3/11 16:55
 */
public class MonthBean {
    private String month;
    private List<DetailBean> dayBeanList;
    private Map<String,List<DetailBean>> dayMap;

    public List<DetailBean> getDayBeanList() {
        return dayBeanList;
    }

    public void setDayBeanList(List<DetailBean> datBeanList) {
        this.dayBeanList = datBeanList;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Map<String, List<DetailBean>> getDayMap() {
        return dayMap;
    }

    public void setDayMap(Map<String, List<DetailBean>> dayMap) {
        this.dayMap = dayMap;
    }
}
