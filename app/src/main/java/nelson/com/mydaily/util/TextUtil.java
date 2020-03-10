package nelson.com.mydaily.util;

/**
 * Created By PJSONG
 * On 2020/3/10 14:30
 */
public class TextUtil {

    public static boolean isEmpty(String txt){
        if (txt == null || txt.trim().length() == 0){
            return true;
        }
        return false;
    }
}
