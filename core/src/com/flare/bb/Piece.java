package com.flare.bb;

import com.flare.bb.Math.Vector2d;

public class Piece {

    private Vector2d[] body;
    private int[] skirt;
    private int width;
    private int height;
    private Piece next; //The next counterclockwise rotation

    private static Piece[] pieces; //An array containing all the first rotations of the seven pieces.

    public Piece(Vector2d[] points) {
        // YOUR CODE HERE
        body = points.clone();
        int xmax = 0;
        int ymax = 0;
        for (Vector2d point:body) {
            if (point.getX() > xmax) xmax = point.getX();
            if (point.getY() > ymax) ymax = point.getY();
        }
        width = xmax + 1;
        height = ymax  + 1;
        skirt = new int[width];
        for (int x = 0; x < width; x++){
            skirt[x] = height - 1;
        }
        for (Vector2d point:body) {
            if (skirt[point.getX()] > point.getY()) skirt[point.getX()] = point.getY();
        }
        next = null;
    }

}
