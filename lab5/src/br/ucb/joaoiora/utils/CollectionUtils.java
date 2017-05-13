package br.ucb.joaoiora.utils;

import java.util.Collection;

/**
 * Created by Joao on 13/05/2017.
 */
public class CollectionUtils {

    /**
     *
     *
     * @param collection
     * @return
     */
    public static boolean isEmpty(final Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     *
     * @param collection
     * @return
     */
    public static boolean isNotEmpty(final Collection<?> collection) {
        return !isEmpty(collection);
    }

}
