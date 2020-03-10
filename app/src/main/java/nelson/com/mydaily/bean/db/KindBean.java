package nelson.com.mydaily.bean.db;

import io.realm.RealmObject;

/**
 * Created By PJSONG
 * On 2020/3/10 14:21
 */
public class KindBean extends RealmObject {
    private int idType;
    private String typeName;

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
