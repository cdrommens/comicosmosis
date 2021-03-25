package be.rommens.scraper.utils;

import java.util.Collection;
import java.util.NoSuchElementException;

/**
 *
 */
public class MyCollectionUtils {

    public static <E> E getOnlyElement(Collection<E> collection) {
        return collection.stream()
                .reduce((a, b) -> {
                    throw new IllegalStateException("Multiple elements: " + a + ", " + b);
                })
                .orElseThrow(NoSuchElementException::new);
    }

}
