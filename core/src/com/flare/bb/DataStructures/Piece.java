package com.flare.bb.DataStructures;

import com.badlogic.gdx.graphics.Color;
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
    private int type;

    private static Piece[] pieces; //An array containing all the first rotations of the seven pieces.

    public Piece(Vector2d[] points, int type) {
        body = points.clone();
        this.type = type;
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
     Returns the type of the piece measured in blocks.
     */
    public int getType(){
        return type;
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
        Piece piece = new Piece(retArr, this.type);
        return piece;
    }

    //Reflect over y = x
    public Vector2d[] flipDiagonal(Vector2d[] points){
        Vector2d[] arr = points;
        for(int i = 0; i < arr.length; i++){
            arr[i] = new Vector2d(points[i].getY(), points[i].getX());
        }
        return arr;
    }

    //Reflect over Y Axis and displace x appropriately.
    public Vector2d[] flipVertical(Vector2d[] points, int height){
        Vector2d[] arr = points;
        for(int i = 0; i < arr.length; i++){
            arr[i] = new Vector2d(width - points[i].getX() - 1, points[i].getY());
        }
        return arr;
    }
    /**
     Returns true if two pieces are the same --
     their bodies contain the same points.
     Interestingly, this is not the same as having exactly the
     same body arrays, since the points may not be
     in the same order in the bodies. Used internally to detect
     if two rotations are effectively the same.
     */
    public boolean equals(Object obj) {
        // standard equals() technique 1
        if (obj == this) return true;

        // standard equals() technique 2
        // (null will be false)
        if (!(obj instanceof Piece)) return false;
        Piece other = (Piece)obj;

        if (width != other.getWidth()) return false;
        if (height != other.getHeight()) return false;
        for (int i = 0; i < skirt.length; i++) {
            if (skirt[i] != other.getSkirt()[i]) return false;
        }

        return true;
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


    // String constants for the standard 7 tetris pieces
    public static final String STICK_STR	= "0 0	0 1	 0 2  0 3";
    public static final String L1_STR		= "0 0	0 1	 0 2  1 0";
    public static final String L2_STR		= "0 0	1 0  1 1  1 2";
    public static final String S1_STR		= "0 0	1 0	 1 1  2 1";
    public static final String S2_STR		= "0 1	1 1  1 0  2 0";
    public static final String SQUARE_STR	= "0 0  0 1  1 0  1 1";
    public static final String PYRAMID_STR	= "0 0  1 0  1 1  2 0";

    // Indexes for the standard 7 pieces in the pieces array
    public static final int STICK = 0;
    public static final int L1	  = 1;
    public static final int L2	  = 2;
    public static final int S1	  = 3;
    public static final int S2	  = 4;
    public static final int SQUARE	= 5;
    public static final int PYRAMID = 6;

    public static final Color[] PIECE_COLORS = {Color.CYAN, Color.ORANGE, Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.PURPLE};

    public static Piece[] getPieces() {
        // lazy evaluation -- create static array if needed
        if (Piece.pieces==null) {
            // use makeFastRotations() to compute all the rotations for each piece

            Piece.pieces = new Piece[] {
                    makeFastRotations(new Piece(STICK_STR, STICK)),
                    makeFastRotations(new Piece(L1_STR, L1)),
                    makeFastRotations(new Piece(L2_STR, L2)),
                    makeFastRotations(new Piece(S1_STR, S1)),
                    makeFastRotations(new Piece(S2_STR, S2)),
                    makeFastRotations(new Piece(SQUARE_STR, SQUARE)),
                    makeFastRotations(new Piece(PYRAMID_STR, PYRAMID)),
            };
        }

        return Piece.pieces;
    }

    /**
     Given the "first" root rotation of a piece, computes all
     the other rotations and links them all together
     in a circular list. The list loops back to the root as soon
     as possible. Returns the root piece. fastRotation() relies on the
     pointer structure setup here.
     */
	/*
	 Implementation: uses computeNextRotation()
	 and Piece.equals() to detect when the rotations have gotten us back
	 to the first piece.
	*/
    private static Piece makeFastRotations(Piece root) {
        Piece curr = root;
        Piece next;
        while (true) {
            next = curr.computeNextRotation();
            if (next.equals(root)) {
                curr.next = root;
                break;
            } else {
                curr.next = next;
                curr = next;
            }
        }
        return root;
    }



    /**
     * Alternate constructor, takes a String with the x,y body points
     * all separated by spaces, such as "0 0  1 0  2 0	1 1".
     * (provided)
     */
    public Piece(String points, int t) {
        this(parsePoints(points), t);
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
