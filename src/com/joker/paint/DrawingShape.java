package com.joker.paint;

import java.awt.*;

/**
 * Created by Adam on 2017/9/18.
 */
public class DrawingShape extends DrawingItem {
    private Color _color;
    private Shape _shape;
    private Stroke _stroke;
    private boolean _fill;
    public DrawingShape(Color color,Shape shape,boolean fill,Stroke stroke){
        super(Type.SHAPE);
        _color=color;
        _shape=shape;
        _fill=fill;
        _stroke=fill?null:stroke;
    }
    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(_color);
        if(_fill) {
            graphics2D.fill(_shape);
        }else {
            graphics2D.setStroke(_stroke);
            graphics2D.draw(_shape);
        }
    }
}
