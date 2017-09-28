package com.joker.paint;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/18.
 */
public class DrawingImage extends DrawingItem {
    private Image _image;
    private JFrame _imgObserver;
    private Rectangle _pos;
    public DrawingImage(Image image,Rectangle pos,JFrame imgObserver){
       super(Type.IMAGE,false);
       _image=image;
       _pos=pos;
       _imgObserver=imgObserver;
        initResizePoint();
    }
    public DrawingImage(Image image,Rectangle pos,JFrame imgObserver,boolean isPreview){
        super(Type.IMAGE,isPreview);
        _image=image;
        _pos=pos;
        _imgObserver=imgObserver;
        initResizePoint();
    }

    @Override
    public void reposition(Point pos) {
        _pos.x=pos.x;
        _pos.y=pos.y;
    }

    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D)g;
        graphics2D.drawImage(_image,_pos.x,_pos.y,_pos.width,_pos.height,_imgObserver);
    }

    @Override
    public Vector<Point> getResizePoints() {
        Vector<Point> points=new Vector<>();
        points.add(new Point(_pos.x,_pos.y));
        points.add(new Point(_pos.x+_pos.width,_pos.y+_pos.height));
        return points;
    }

    @Override
    public void resize(int resizePointRank, Point posTo) {
        switch (resizePointRank){
            case 0:
                _pos.x=posTo.x;
                _pos.y=posTo.y;
                break;
            case 1:
                int x=Math.min(_pos.x,posTo.x),y=Math.min(_pos.y,posTo.y),
                        disX=Math.abs(posTo.x-_pos.x),disY=Math.abs(posTo.y-_pos.y);
                _pos=new Rectangle(x,y,disX,disY);

        }
        resizePoint.reposition(null);
    }

    @Override
    public DrawingItem createPreview() {
        return new DrawingShape(selectedColor,
                new Rectangle(_pos.x,_pos.y,_pos.width,_pos.height),false,new BasicStroke(6f),true);
    }
}
