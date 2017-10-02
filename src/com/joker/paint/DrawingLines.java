package com.joker.paint;

//import external.BetterBasicStroke;
import external.StrokeSample;

import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/22.
 */
public class DrawingLines extends DrawingItem {
    private Color _color;
    private Vector<Point> _points;
    private Stroke _stroke;
    public DrawingLines(Color color,Vector<Point> points,Stroke stroke){
        this(color,points,stroke,false);
    }
    public DrawingLines(Color color,Vector<Point> points,Stroke stroke,boolean isPreview){
        super(Type.SHAPE,isPreview);
        _color=color;
        if (isPreview)
            _points=points;
        else
            _points=(Vector<Point>) points.clone();
        _stroke= stroke;
        initResizePoint();
    }

    @Override
    public void resize(int resizePointRank, Point posTo) {
        _points.elementAt(resizePointRank).x=posTo.x;
        _points.elementAt(resizePointRank).y=posTo.y;

        resizePoint.reposition(null);
    }

    public void reposition(Point pos) {
        Point delta=new Point(pos.x-_points.elementAt(0).x,pos.y-_points.elementAt(0).y);
        for (Point x:_points){
            x.x+=delta.x;
            x.y+=delta.y;
        }
    }

    @Override
    protected Vector<Point> getResizePoints() {
        return (Vector<Point>) _points.clone();
    }

    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.setColor(_color);
        graphics2D.setStroke(_stroke);
        for(int i=1;i<_points.size();i++){
            Point x=_points.elementAt(i),y=_points.elementAt(i-1);
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
