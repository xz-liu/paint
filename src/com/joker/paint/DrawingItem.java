package com.joker.paint;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Created by Adam on 2017/9/15.
 */
public abstract class DrawingItem {

    public enum Type{
        IMAGE,TEXT,SHAPE,POINTS,POLYGON
    }
    protected Type _type;
    protected final Color selectedColor=Color.RED;
    public Type getType(){
        return _type;
    }
    public void setType(Type t){
        _type =t;
    }
    protected DrawingItem(Type type){
        _type=type;
    }
    public abstract void draw(Graphics g);
    public abstract DrawingItem createPreview();


}