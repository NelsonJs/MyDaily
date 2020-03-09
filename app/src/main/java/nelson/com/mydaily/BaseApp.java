package nelson.com.mydaily;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import nelson.com.mydaily.db.DbMigration;

/**
 * Created By PJSONG
 * On 2020/3/5 16:24
 */
public class BaseApp extends Application {

    public static Realm realm;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name("daily.realm")
                .schemaVersion(0)
                .migration(new DbMigration())
                .build();
        realm = Realm.getInstance(configuration);
    }
}
