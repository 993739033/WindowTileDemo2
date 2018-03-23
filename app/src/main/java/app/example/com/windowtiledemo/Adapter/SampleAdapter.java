package app.example.com.windowtiledemo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by mnkj on 2018/3/23.
 */

public class SampleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> dataList;
    public SampleAdapter(List<String> dataList) {
        this.dataList = dataList;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            // 加载View
            View view = new View(parent.getContext());
            return new SampleViewHolder1(view);
        } else {
            // 加载View
            View view = new View(parent.getContext());
            return new SampleViewHolder2(view);
        }
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SampleViewHolder1) {
            ((SampleViewHolder1) holder).bind(dataList.get(position));
        } else {
            ((SampleViewHolder2) holder).bind(dataList.get(position));
        }
    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
    private class SampleViewHolder1 extends RecyclerView.ViewHolder {
        public SampleViewHolder1(View itemView) {
            super(itemView);
        }
        public void bind(String data) {
            // ...
        }
    }
    private class SampleViewHolder2 extends RecyclerView.ViewHolder {
        public SampleViewHolder2(View itemView) {
            super(itemView);
        }
        public void bind(String data) {
            // ...
        }
    }
}