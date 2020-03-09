package nelson.com.mydaily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nelson.com.mydaily.R;
import nelson.com.mydaily.bean.db.DetailBean;

/**
 * Created By PJSONG
 * On 2020/3/9 16:00
 */
public class IndexAdapter extends RecyclerView.Adapter {

    public List<DetailBean> mData;

    public IndexAdapter(List<DetailBean> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_index_rcv_layout,parent,false);
        return new ItemHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        itemHolder.nameTv.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView nameTv;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }
}
