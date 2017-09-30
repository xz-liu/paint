package com.joker.paint;

import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/22.
 */
public class DrawingPoints extends DrawingItem {
    private Color _color;
    private Point[] _points;
    private Stroke _stroke;
    public DrawingPoints(Color color,Point[] points,Stroke stroke){
        super(Type.POINTS,false);
        _color=color;
        _points=points;
        _stroke=stroke;
        initResizePoint();
    }
    public DrawingPoints(Color color,Point[] points,Stroke stroke,boolean isPreview){
        super(Type.POINTS,isPreview);
        _color=color;
        _points=points;
        _stroke=stroke;
        initResizePoint();
    }

    @Override
    public void resize(int resizePointRank, Point posTo) {
        reposition(posTo);
        resizePoint.reposition(null);
    }

    @Override
    public Vector<Point> getResizePoints() {
        Vector<Point> points=new Vector<>();
        if (_points.length!=0)points.add(_points[0]);
        return points;
    }

    public void reposition(Point pos) {
        Point delta=new Point(pos.x-_points[0].x,pos.y-_points[0].y);
        for (Point x:_points){
            x.x+=delta.x;
            x.y+=delta.y;
        }
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
        return new DrawingPoints(selectedColor,_points,_stroke,true);
    }
}
