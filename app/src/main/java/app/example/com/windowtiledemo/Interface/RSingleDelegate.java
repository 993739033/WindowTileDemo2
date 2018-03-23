package app.example.com.windowtiledemo.Interface;

import app.example.com.windowtiledemo.Adapter.RViewHolder;

/**
 * Created by mnkj on 2018/3/23.
 */

public class RSingleDelegate<T> implements RAdapterDelegate<T> {

    private Class<? extends RViewHolder<T>> clazz;



    public RSingleDelegate(Class<? extends RViewHolder<T>> clazz) {

        this.clazz = clazz;

    }



    @Override

    public Class<? extends RViewHolder<T>> getViewHolderClass(int position) {

        return clazz;

    }

}
