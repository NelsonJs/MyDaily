package nelson.com.mydaily.bean.event;

/**
 * Created By PJSONG
 * On 2020/3/10 16:18
 */
public class EditBean {
    private boolean edit;

    public EditBean(boolean edit) {
        this.edit = edit;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }
}
