package com.joker.paint;

import java.awt.*;

/**
 * Created by Adam on 2017/9/18.
 */
public class DrawingText extends DrawingItem {
    private String text;
    private Point pos;
    private Font font;
    private Color color;
    public DrawingText(String text, Point pos,Font font,Color color){
        super(Type.TEXT);
        this.text=text;
        this.font=font;
        this.pos=pos;
        this.color=color;
    }
    public void draw(Graphics g){
        Graphics2D graphics2D=(Graphics2D )g;
        graphics2D.setFont(font);
        graphics2D.setColor(color);
        graphics2D.drawString(text,pos.x,pos.y);
    }

    @Override
    public DrawingItem createPreview() {
        return new DrawingText(text,pos,font,selectedColor);
    }
}
