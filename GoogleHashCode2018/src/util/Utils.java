/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import domain.Intersection;

/**
 *
 * @author Sklipnoty
 */
public class Utils {
        public static int getDistance(Intersection toIntersection, Intersection fromIntersection) {
        return Math.abs(fromIntersection.x - toIntersection.x) + Math.abs(fromIntersection.y - toIntersection.y);
    }
}
