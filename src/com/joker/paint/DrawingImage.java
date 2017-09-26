package com.joker.paint;

import javax.swing.*;
import java.awt.*;

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
    }
    public DrawingImage(Image image,Rectangle pos,JFrame imgObserver,boolean isPreview){
        super(Type.IMAGE,isPreview);
        _image=image;
        _pos=pos;
        _imgObserver=imgObserver;
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
    public DrawingItem createPreview() {
        return new DrawingShape(selectedColor,
                new Rectangle(_pos.x,_pos.y,_pos.width,_pos.height),false,new BasicStroke(6f),true);
    }
}
