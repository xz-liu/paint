package com.joker.paint;

import java.awt.*;

/**
 * Created by Adam on 2017/9/24.
 */
public class DrawingPolygon extends DrawingItem {
    private Color _color;
    private Polygon _shape;
    private Stroke _stroke;
    private boolean _fill;
    boolean _isPolygon;
    public DrawingPolygon(Color color,Polygon shape,boolean fill,Stroke stroke){
        super(Type.POLYGON);
        _color=color;
        _shape=shape;
        _fill=fill;
        _stroke=fill?null:stroke;
    }
    public void reposition(Point pos) {
        Point delta=new Point(pos.x-_shape.xpoints[0],pos.y-_shape.ypoints[0]);
        for(int i=0;i<_shape.npoints;i++){
            _shape.xpoints[i]+=delta.x;
            _shape.ypoints[i]+=delta.y;
        }
    }
    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(_color);
        if(_fill) {
            graphics2D.fillPolygon(_shape);
        }else {
            graphics2D.setStroke(_stroke);
            graphics2D.drawPolygon(_shape);
        }
    }

    @Override
    public DrawingItem createPreview() {
        return new DrawingPolygon(selectedColor,_shape,_fill,_stroke);
    }
}
