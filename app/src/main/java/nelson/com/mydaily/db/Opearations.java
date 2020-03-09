package nelson.com.mydaily.db;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import nelson.com.mydaily.BaseApp;
import nelson.com.mydaily.bean.db.DetailBean;

/**
 * Created By PJSONG
 * On 2020/3/5 16:46
 */
public class Opearations {
    private static Opearations instance;

    public static Opearations getInstance(){
        if (instance == null){
            instance = new Opearations();
        }
        return instance;
    }

    public void add(){
        BaseApp.realm.beginTransaction();
        DetailBean detailBean = BaseApp.realm.createObject(DetailBean.class);
        detailBean.setName("测试1");
        BaseApp.realm.commitTransaction();
    }

    RealmQuery<DetailBean> detailQuery = BaseApp.realm.where(DetailBean.class);
    public List<DetailBean> getList(){
        RealmResults<DetailBean> results = detailQuery.findAll();
        return results;
    }
}
