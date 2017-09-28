package com.joker.paint;

import java.awt.*;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/15.
 */
public abstract class DrawingItem {

    public enum Type{
        IMAGE,TEXT,SHAPE,POINTS,POLYGON,RESIZEPOINT
    }
    protected Type _type;
    protected ResizePoint resizePoint;
   //  private Rectangle rectSelect;
    public boolean isSelectedPreview() {
        return selectedPreview;
    }
    public void setSelectedPreview(boolean selectedPreview){
        this.selectedPreview = selectedPreview;
    }
    private boolean selectedPreview;
    protected static final Color selectedColor=Color.RED;
    public static final Color getSelectedColor(){
        return selectedColor;
    }
    public Type getType(){
        return _type;
    }
    public void setType(Type t){
        _type =t;
    }
    protected DrawingItem(Type type,boolean isPreview){
        _type=type;
        selectedPreview =isPreview;
    }
    protected void initResizePoint(){
        resizePoint=new ResizePoint(this);
    }

    public ResizePoint getResizePoint() {
        return resizePoint;
    }
    public abstract void resize(int resizePointRank,Point posTo);

    public abstract void reposition(Point pos);
    public abstract void draw(Graphics g);
//    public abstract Rectangle getRectSelect();
    public abstract Vector<Point> getResizePoints();
    public abstract DrawingItem createPreview();
}