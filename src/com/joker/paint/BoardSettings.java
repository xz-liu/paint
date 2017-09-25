package com.joker.paint;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/22.
 */
public class BoardSettings {
    public enum Type{
     POINTS,IMAGE,POLYGON,OVAL,RECT,TEXT,DELETE
    }
    Stroke stroke;
    Color color;
    boolean fill;
    Image imgNow;
    String text;
    Vector<Point> points;
    Type type;
    JFrame mainFrame;
    public BoardSettings(JFrame jFrame) {
        type = Type.OVAL;
        this.mainFrame = jFrame;
        this.color=Color.BLACK;
        this.stroke=new BasicStroke(5f);
        this.imgNow=null;
        this.fill=false;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }


    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public Image getImgNow() {
        return imgNow;
    }

    public void setImgNow(Image imgNow) {
        this.imgNow = imgNow;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Vector<Point> getPoints() {
        if(points==null){
            points=new Vector<Point>();
        }
        return points;
    }

    public void setPoints(Vector<Point> points) {
        this.points = points;
    }
}
