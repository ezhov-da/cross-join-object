package ru.ezhov.crossjoinobject;

import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ObjectCrossJoin<T> {
    private static final Logger LOG = Logger.getLogger(ObjectCrossJoin.class.getName());
    private T[] arrayCrossJoin;

    @SuppressWarnings("unchecked")
    public ObjectCrossJoin(T... arrayCrossJoin) {
        this.arrayCrossJoin = arrayCrossJoin;
    }

    public T[] getArrayCrossJoin() {
        return arrayCrossJoin;
    }

    @Override
    public String toString() {
        return Stream
                .of(arrayCrossJoin)
                .map(Object::toString)
                .collect(Collectors.joining(","));
    }
}
