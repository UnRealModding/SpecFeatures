package com.unrealdinnerbone.specfeatures.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class LazyHashMap<T, B> {

    private final Function<T, B> function;
    private final Map<T, B> theMap = new HashMap<>();

    public LazyHashMap(Function<T, B> theFunction) {
        this.function = theFunction;
    }

    public B get(T t) {
        if(!theMap.containsKey(t)) {
            theMap.put(t, function.apply(t));
        }
        return theMap.get(t);
    }

    public void remove(T t) {
        theMap.remove(t);
    }

    public void remove(T t, Consumer<B> bSupplier) {
        if(theMap.containsKey(t)) {
            bSupplier.accept(theMap.get(t));
            remove(t);
        }
    }


    public Map<T, B> getCurrentMap() {
        return Map.copyOf(theMap);
    }

    public void reset() {
        theMap.clear();
    }

    public Collection<B> values() {
        return theMap.values();
    }

    public int size() {
        return theMap.size();
    }
}
