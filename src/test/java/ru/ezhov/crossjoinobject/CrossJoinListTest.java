package ru.ezhov.crossjoinobject;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CrossJoinListTest {

    @Test
    @SuppressWarnings("unchecked")
    public void crossJoin() {
        CrossJoinList crossJoinList = new CrossJoinList();
        List<ObjectCrossJoin> objectCrossJoins = crossJoinList.crossJoin(
                Integer.class,
                Arrays.asList(1, 2),
                Arrays.asList(1, 2),
                Arrays.asList(1, 2)
        );
        Assert.assertEquals(
                "[1,1,1, 1,1,2, 1,2,1, 1,2,2, 2,1,1, 2,1,2, 2,2,1, 2,2,2]",
                objectCrossJoins.toString()
        );
    }
}