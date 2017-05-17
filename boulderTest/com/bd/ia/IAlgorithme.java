package boulderTest.com.bd.ia;

import boulderTest.Map;

import java.awt.*;

/**
 * Created by marin on 17/03/2017.
 */
public interface IAlgorithme {
    Point getDeplacement(Point posRockford, Map laMap);
    String toString();
}
