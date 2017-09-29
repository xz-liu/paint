package com.joker.paint;


import javax.swing.*;
import java.awt.*;
import java.util.Vector;
/**
 * Created by Adam on 2017/9/22.
 */
public class BoardSettings {
    public enum Type{
     POINTS,IMAGE,POLYGON,OVAL,RECT,TEXT,DELETE,MOVE,TOP,BOTTOM,LINES,SELECT
    }
    Stroke stroke;
    Color color;
    boolean fill;
    Image imgNow;
    String text;
    Vector<Point> points;
    Type type;
    MainForm mainFrame;
    JPanel history;
    DrawingItem itemReplacing;
    ResizePoint pointNow;
    int selectPoint;
    public int getSelectPoint(){return selectPoint;}
    public void setSelectPoint(int i){
        selectPoint=i;
    }
    public MainForm getMainFrame() {
        return mainFrame;
    }

    public ResizePoint getPointNow() {
        return pointNow;
    }

    public void nextResizePoint(ResizePoint pointNext){
        if(pointNow!=null){
            pointNow.setShow(false);
        }
        if(pointNext!=null){
            pointNext.setShow(true);
        }
        pointNow=pointNext;
    }

    public void replace(Point pos){
        if (getItemReplacing()!=null){
            getItemReplacing().reposition(pos);
        }
    }
    public DrawingItem getItemReplacing() {
        return itemReplacing;
    }
    public void setItemReplacing(DrawingItem item){
        itemReplacing=item;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    Font  font;
    public BoardSettings(MainForm jFrame) {
        type = Type.POINTS;
        this.mainFrame = jFrame;
        this.color=Color.BLACK;
        this.stroke=jFrame.getStrokeChooserPanel().getSelectedStroke();
        this.imgNow=null;
        this.fill=false;
        this.history=jFrame.getHistory();
        this.font=new JLabel().getFont();
        this.text="";
        this.itemReplacing=null;
        pointNow=null;
    }
    public void clearPoints(){
        setPoints(null);
        mainFrame.getBoard().repaint();
    }

    public JPanel getHistory() {
        return history;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }


    public Stroke getStroke() {
        return stroke;
    }

    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isFill() {
        return fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public Image getImgNow() {
        return imgNow;
    }

    public void setImgNow(Image imgNow) {
        this.imgNow = imgNow;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Vector<Point> getPoints() {
        if(points==null){
            points=new Vector<Point>();
        }
        return points;
    }

    public void setPoints(Vector<Point> points) {
        this.points = points;
    }
}
