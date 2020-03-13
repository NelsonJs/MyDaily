package nelson.com.mydaily;

import java.util.List;

import nelson.com.mydaily.bean.MonthBean;

/**
 * Created By PJSONG
 * On 2020/3/11 16:53
 */
public class YearBean {
    private String year;
    private List<MonthBean> monthBeans;

    public List<MonthBean> getMonthBeans() {
        return monthBeans;
    }

    public void setMonthBeans(List<MonthBean> monthBeans) {
        this.monthBeans = monthBeans;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
