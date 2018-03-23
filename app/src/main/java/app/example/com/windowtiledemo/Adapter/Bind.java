package app.example.com.windowtiledemo.Adapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mnkj on 2018/3/23.
 */

@Target(ElementType.FIELD)

@Retention(RetentionPolicy.RUNTIME)

public @interface Bind {

    int value();

}
