package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void distanceForwadrTest() {
        Point p1 = new Point(5, 5);
        Point p2 = new Point(-2, -4);

        Assert.assertEquals(p1.distance(p2), 11.40175425099138);
    }

    @Test
    public void distanceReverseTest() {
        Point p1 = new Point(5, 5);
        Point p2 = new Point(-2, -4);

        Assert.assertEquals(p2.distance(p1), 11.40175425099138);
    }

    @Test
    public void distanceToYouselfTest() {
        Point p1 = new Point(5, 5);

        Assert.assertEquals(p1.distance(p1), 0.0);
    }

    @Test
    public void distanceToStartPointTest() {
        Point p1 = new Point(-2, -4);
        Point p2 = new Point(0, 0);

        Assert.assertEquals(p2.distance(p1), 4.47213595499958);
    }
}



