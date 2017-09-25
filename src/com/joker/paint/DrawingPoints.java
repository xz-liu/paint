package com.joker.paint;

import java.awt.*;

/**
 * Created by Adam on 2017/9/22.
 */
public class DrawingPoints extends DrawingItem {
    private Color _color;
    private Point[] _points;
    private Stroke _stroke;
    public DrawingPoints(Color color,Point[] points,Stroke stroke){
        super(Type.SHAPE);
        _color=color;
        _points=points;
        _stroke=stroke;
    }
    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(_color);
        graphics2D.setStroke(_stroke);
        for(Point x:_points){
            graphics2D.drawLine(x.x,x.y,x.x,x.y);
        }
    }

    @Override
    public DrawingItem createPreview() {
        return new DrawingPoints(selectedColor,_points,_stroke);
    }
}
