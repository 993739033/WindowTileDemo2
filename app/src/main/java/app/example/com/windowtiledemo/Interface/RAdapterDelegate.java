package app.example.com.windowtiledemo.Interface;

import app.example.com.windowtiledemo.Adapter.RViewHolder;

/**
 * Created by mnkj on 2018/3/23.
 */


public interface RAdapterDelegate<T> {

    Class<? extends RViewHolder<T>> getViewHolderClass(int position);

}
