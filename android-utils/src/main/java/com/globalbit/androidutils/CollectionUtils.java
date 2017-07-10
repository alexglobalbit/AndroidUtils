package com.globalbit.androidutils;

import java.util.Collection;

/**
 * Created by Alex on 12/12/2016.
 */

public class CollectionUtils {

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }
}
