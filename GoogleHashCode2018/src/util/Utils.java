package util;

import domain.Intersection;

public class Utils
{
    public static int getDistance(Intersection toIntersection, Intersection fromIntersection)
    {
        return Math.abs(fromIntersection.row - toIntersection.row) + Math.abs(fromIntersection.col - toIntersection.col);
    }
}
