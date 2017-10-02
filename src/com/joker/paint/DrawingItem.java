package com.joker.paint;

import java.awt.*;
import java.io.Serializable;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/15.
 */
public abstract class DrawingItem implements Serializable {

    public enum Type{
        IMAGE,TEXT,RECT,OVAL,SHAPE,POINTS,POLYGON,RESIZEPOINT
    }

    protected static final Color selectedColor=Color.RED;
    public static final Color getSelectedColor(){
        return selectedColor;
    }

    protected Type _type;
    protected transient ResizePoint resizePoint;
    protected transient HistoryButton relatedButton;
    protected boolean selectedPreview;

    protected DrawingItem(Type type,boolean isPreview){
        _type=type;
        selectedPreview =isPreview;
    }

    public Type getType(){
        return _type;
    }
    public void setType(Type t){
        _type =t;
    }

    public boolean isSelectedPreview() {
        return selectedPreview;
    }
    public void initResizePoint(){
        resizePoint=new ResizePoint(this);
    }

    public ResizePoint getResizePoint() {
        return resizePoint;
    }

    public HistoryButton getRelatedButton() {
        return relatedButton;
    }

    public void setRelatedButton(HistoryButton relatedButton) {
        this.relatedButton = relatedButton;
    }

    public void drawResizePoint(Graphics graphics) {
        getResizePoint().draw(graphics);
    }

    protected boolean isItem(){
        return !(this instanceof ResizePoint);
    }

    public abstract void resize(int resizePointRank,Point posTo);
    public abstract void reposition(Point pos);
    public abstract void draw(Graphics g);
    protected abstract Vector<Point> getResizePoints();
    public abstract DrawingItem createPreview();

}