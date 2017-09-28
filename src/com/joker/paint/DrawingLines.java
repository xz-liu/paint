package com.joker.paint;

import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/22.
 */
public class DrawingLines extends DrawingItem {
    private Color _color;
    private Point[] _points;
    private Stroke _stroke;
    public DrawingLines(Color color,Point[] points,Stroke stroke){
        super(Type.SHAPE,false);
        _color=color;
        _points=points;
        _stroke=stroke;
        initResizePoint();
    }
    public DrawingLines(Color color,Point[] points,Stroke stroke,boolean isPreview){
        super(Type.SHAPE,isPreview);
        _color=color;
        _points=points;
        _stroke=stroke;
        initResizePoint();
    }

    @Override
    public void resize(int resizePointRank, Point posTo) {
        _points[resizePointRank].x=posTo.x;
        _points[resizePointRank].y=posTo.y;

        resizePoint.reposition(null);
    }

    public void reposition(Point pos) {
        Point delta=new Point(pos.x-_points[0].x,pos.y-_points[0].y);
        for (Point x:_points){
            x.x+=delta.x;
            x.y+=delta.y;
        }
    }

    @Override
    public Vector<Point> getResizePoints() {
        return new Vector(Arrays.asList(_points));
    }

    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(_color);
        graphics2D.setStroke(_stroke);
        for(int i=1;i<_points.length;i++){
            Point x=_points[i],y=_points[i-1];
            graphics2D.drawLine(x.x,x.y,y.x,y.y);
        }
//        for(Point x:_points){
//            graphics2D.drawLine(x.x,x.y,x.x,x.y);
//        }
    }

    @Override
    public DrawingItem createPreview() {
        return new DrawingLines(selectedColor,_points,_stroke,true);
    }
}
