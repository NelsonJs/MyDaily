package nelson.com.mydaily.db;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import nelson.com.mydaily.BaseApp;
import nelson.com.mydaily.bean.db.DetailBean;
import nelson.com.mydaily.bean.event.EditBean;

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

    public void saveBill(int idType,String typeName,float money,long time,int year,int month){
        BaseApp.realm.beginTransaction();
        DetailBean detailBean = BaseApp.realm.createObject(DetailBean.class);
        detailBean.setIdType(idType);
        detailBean.setMoney(money);
        detailBean.setTypeName(typeName);
        detailBean.setTime(time);
        detailBean.setYear(year);
        detailBean.setMonth(month);
        BaseApp.realm.commitTransaction();
    }

    public void updateBill(final String uuid, final int idType, final String typeName, final float money, final long time, final int year, final int month){
        BaseApp.realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Log.d("uuid", "----" + uuid);
                DetailBean bean = realm.where(DetailBean.class)
                        .equalTo("uuid", uuid).findFirst();
                bean.setIdType(idType);
                bean.setTime(time);
                bean.setMoney(money);
                bean.setTypeName(typeName);
                bean.setYear(year);
                bean.setMonth(month);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                EventBus.getDefault().post(new EditBean(true));
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {

            }
        });
    }


    RealmQuery<DetailBean> detailQuery = BaseApp.realm.where(DetailBean.class);
    public List<DetailBean> getList(){
        RealmResults<DetailBean> results = detailQuery.findAll();
        return results;
    }
}
