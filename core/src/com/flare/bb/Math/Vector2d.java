package com.flare.bb.Math;

public class Vector2d {

    private int x, y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d p){
        this(p.getX(), p.getY());
    }

    public Vector2d(){
        this(0, 0);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object other){
        // standard two checks for equals()
        if (this == other) return true;
        if (!(other instanceof Vector2d)) return false;

        // check if other point same as us
        Vector2d pt = (Vector2d) other;
        return(x==pt.x && y==pt.y);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}
