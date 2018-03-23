package app.example.com.windowtiledemo.Adapter;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mnkj on 2018/3/23.
 */

@Target(ElementType.TYPE)

@Retention(RetentionPolicy.RUNTIME)

public @interface RLayout {

    @LayoutRes int value();

}