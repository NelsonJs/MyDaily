package nelson.com.mydaily.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import nelson.com.mydaily.bean.db.DetailBean;
import nelson.com.mydaily.db.Opearations;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<DetailBean>> mData;

    public HomeViewModel() {

    }

    public LiveData<List<DetailBean>> getData() {
        if (mData == null){
            mData = new MutableLiveData<>();
        }
        mData.setValue(Opearations.getInstance().getList());
        return mData;
    }
}