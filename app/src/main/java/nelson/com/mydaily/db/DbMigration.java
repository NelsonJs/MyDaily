package nelson.com.mydaily.db;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;
import nelson.com.mydaily.bean.db.DetailBean;

/**
 * Created By PJSONG
 * On 2020/3/5 17:26
 */
public class DbMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
       /* RealmSchema schema = realm.getSchema();
        if (oldVersion == 0){
            schema.get("DetailBean").removePrimaryKey();
            oldVersion++;
        }*/
    }
}
