package nelson.com.mydaily.bean.db;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created By PJSONG
 * On 2020/3/5 16:27
 */
public class DetailBean extends RealmObject {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
