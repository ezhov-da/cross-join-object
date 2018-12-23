package ru.ezhov.crossjoinobject;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public final class CrossJoinList<T> {
    private static final Logger LOG = Logger.getLogger(CrossJoinList.class.getName());

    @SuppressWarnings("unchecked")
    private List<ObjectCrossJoin<T>> crossJoin(Class<T> clazz, List<T> listOne, List<T> listTwo) {
        return listOne
                .stream()
                .flatMap(t
                        -> listTwo
                        .stream()
                        .map(t1 ->
                                {
                                    T[] array = (T[]) Array.newInstance(clazz, 2);
                                    array[0] = t;
                                    array[1] = t1;
                                    return new ObjectCrossJoin<T>(array);
                                }
                        )
                ).collect(Collectors.toList());
    }

    private List<ObjectCrossJoin<T>> objectCrossJoins;

    @SuppressWarnings("unchecked")
    public final List<ObjectCrossJoin<T>> crossJoin(Class<T> clazz, List<T>... listsForJoin) {
        switch (listsForJoin.length) {
            case 0:
            case 1:
                throw new IllegalArgumentException("Not use for join one list. Min two list.");
            case 2:
                return crossJoin(clazz, listsForJoin[0], listsForJoin[1]);
            default:
                //сделано для того, чтоб cross join проводился в последовательности следования массива
                List<T> listOne = listsForJoin[listsForJoin.length - 2];
                List<T> listTwo = listsForJoin[listsForJoin.length - 1];
                objectCrossJoins = crossJoin(clazz, listOne, listTwo);
                for (int i = listsForJoin.length - 3; i >= 0; i--) {
                    objectCrossJoins = listsForJoin[i]
                            .stream()
                            .flatMap(t
                                    -> objectCrossJoins
                                    .stream()
                                    .map((ObjectCrossJoin<T> t1) ->
                                    {
                                        T[] array = t1.getArrayCrossJoin();
                                        final T[] arrayResult = (T[]) Array.newInstance(clazz, array.length + 1);
                                        arrayResult[0] = t;
                                        for (int i1 = 1; i1 < arrayResult.length; i1++) {
                                            arrayResult[i1] = array[i1 - 1];
                                        }
                                        ObjectCrossJoin<T> objectCrossJoin = new ObjectCrossJoin<>(arrayResult);
                                        return objectCrossJoin;
                                    })
                            ).collect(Collectors.toList());
                }
                return objectCrossJoins;
        }
    }
}
