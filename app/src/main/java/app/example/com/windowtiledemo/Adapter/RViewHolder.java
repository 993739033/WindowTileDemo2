package app.example.com.windowtiledemo.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by mnkj on 2018/3/23.
 */

public abstract class RViewHolder<T> extends RecyclerView.ViewHolder {

    protected Context context;

    protected View itemView;

    protected RAdapter<T> adapter;

    protected T data;

    protected int position;



    public RViewHolder(View itemView) {

        super(itemView);

        this.itemView = itemView;

        context = itemView.getContext();

        ViewBinder.bind(this, itemView);

    }



    public void setAdapter(RAdapter<T> adapter) {

        this.adapter = adapter;

    }


    public void setData(T t) {

        this.data = t;

    }


    public void setPosition(int position) {

        this.position = position;

    }



    public abstract void refresh();

}