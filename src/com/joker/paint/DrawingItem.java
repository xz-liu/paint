package com.joker.paint;

import java.awt.*;

/**
 * Created by Adam on 2017/9/15.
 */
public abstract class DrawingItem {

    public enum Type{
        IMAGE,TEXT,SHAPE,POINTS,POLYGON
    }
    protected Type _type;

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
    public abstract void reposition(Point pos);
    public abstract void draw(Graphics g);

    public abstract DrawingItem createPreview();


}