package com.flare.bb;

import com.flare.bb.Math.Vector2d;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Piece {

    private Vector2d[] body;
    private int[] skirt;
    private int width;
    private int height;
    private Piece next; //The next counterclockwise rotation

    private static Piece[] pieces; //An array containing all the first rotations of the seven pieces.

    public Piece(Vector2d[] points) {
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

    /**
     Returns the width of the piece measured in blocks.
     */
    public int getWidth() {
        return width;
    }

    /**
     Returns the height of the piece measured in blocks.
     */
    public int getHeight() {
        return height;
    }

    /**
     Returns a pointer to the piece's body. The caller
     should not modify this array.
     */
    public Vector2d[] getBody() {
        return body;
    }

    /**
     Returns a pointer to the piece's skirt. For each x value
     across the piece, the skirt gives the lowest y value in the body.
     This is useful for computing where the piece will land.
     The caller should not modify this array.
     */
    public int[] getSkirt() {
        return skirt;
    }

    /**
     Returns a piece that is rotated counter-clockwise 90 degrees.
     */
    public Piece computeNextRotation(){
        Vector2d[] retArr = flipVertical(body, height);
        retArr = flipDiagonal(retArr);
        Piece piece = new Piece(retArr);
        return piece;
    }

    public Vector2d[] flipDiagonal(Vector2d[] points){
        Vector2d[] arr = points;
        for(int i = 0; i < arr.length; i++){
            arr[i] = new Vector2d(points[i].getY(), points[i].getX());
        }
        return arr;
    }

    public Vector2d[] flipVertical(Vector2d[] points, int height){
        Vector2d[] arr = points;
        for(int i = 0; i < arr.length; i++){
            arr[i] = new Vector2d(points[i].getX(), height-points[i].getY()-1);
        }
        return arr;
    }

    /**
     Returns a pre-computed piece that is 90 degrees counter-clockwise
     rotated from the receiver.	 Fast because the piece is pre-computed.
     This only works on pieces set up by makeFastRotations(), and otherwise
     just returns null.
     */
    public Piece fastRotation() {
        return next;
    }



    /**
     * Alternate constructor, takes a String with the x,y body points
     * all separated by spaces, such as "0 0  1 0  2 0	1 1".
     * (provided)
     */
    public Piece(String points) {
        this(parsePoints(points));
    }

    /**
     Given a string of x,y pairs ("0 0	0 1 0 2 1 0"), parses
     the points into a Vector2d[] array.
     (Provided code)
     */
    private static Vector2d[] parsePoints(String string) {
        List<Vector2d> points = new ArrayList<Vector2d>();
        StringTokenizer tok = new StringTokenizer(string);
        try {
            while(tok.hasMoreTokens()) {
                int x = Integer.parseInt(tok.nextToken());
                int y = Integer.parseInt(tok.nextToken());

                points.add(new Vector2d(x, y));
            }
        }
        catch (NumberFormatException e) {
            throw new RuntimeException("Could not parse x,y string:" + string);
        }

        // Make an array out of the collection
        Vector2d[] array = points.toArray(new Vector2d[0]);
        return array;
    }

}
