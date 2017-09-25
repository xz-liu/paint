package com.joker.paint;

import java.awt.*;
import java.awt.geom.Ellipse2D;

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
    public void reposition(Point pos){
        if(_shape.getClass().getName().equals(Rectangle.class.getName())){
            Rectangle rectangle=(Rectangle)_shape;
            rectangle.x= pos.x;
            rectangle.y=pos.y;
            _shape=rectangle;
        }
        if(_shape.getClass().getName().equals(Ellipse2D.Double.class.getName())){
            Ellipse2D.Double oval=(Ellipse2D.Double) _shape;
            oval.x= pos.x;
            oval.y=pos.y;
            _shape=oval;
        }
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

    @Override
    public DrawingItem createPreview() {
        return new DrawingShape(selectedColor,_shape,_fill,_stroke);
    }
}
