package com.joker.paint;

        import external.StrokeChooserPanel;

        import java.awt.*;
        import java.util.Vector;

public class ResizePoint extends DrawingItem{
    Vector<Point> points;
    DrawingItem item;
    boolean show;
    private final double radius=10;
    private static final Stroke dash= new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
            BasicStroke.JOIN_ROUND, 1.5f, new float[]{15, 10,},
            0f);
    public ResizePoint(DrawingItem itemRef) {
        super(Type.RESIZEPOINT, true);
        show=false;
        points=itemRef.getResizePoints();
        item=itemRef;
    }
    void setShow(boolean show){
        this.show=show;
    }
    void setItem(DrawingItem itemRef){
        points=itemRef.getResizePoints();
        item=itemRef;
    }
    public ResizePoint(){
        super(Type.RESIZEPOINT, true);
    }
    public boolean valid(){return points!=null&&item!=null;}
    @Override
    public void reposition(Point point) {
        if(point==null){
            points=item.getResizePoints();
        }else {
            Point delta=new Point(point.x-points.elementAt(0).x,
                    point.y-points.elementAt(0).y);
            for (Point x:points){
                x.x+=delta.x;
                x.y+=delta.y;
            }
        }
    }

    @Override
    public void resize(int resizePointRank,Point posTo) {
        reposition(null);
    }

    public DrawingItem getItem() {
        return item;
    }

    public int selected(Point point){
        for (int i=0;i<points.size();i++){
            double a=point.x-points.elementAt(i).x
                ,b=point.y-points.elementAt(i).y;
            a=a*a;b=b*b;
            if(a+b<=radius*radius){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void draw(Graphics g) {
        if(show) {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setStroke(new BasicStroke(1));
            graphics2D.setColor(Color.BLACK);
            graphics2D.setStroke(dash);
            for (int i=1;i<points.size();i++){
                Point x=points.elementAt(i-1),y=points.elementAt(i);
                graphics2D.drawLine(x.x,x.y,y.x,y.y);
            }
            for (Point point : points) {
                graphics2D.setColor(Color.WHITE);
                graphics2D.fillOval(point.x -(int) radius, point.y - (int)radius, (int)radius*2, (int)radius*2);
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawOval(point.x -(int) radius, point.y - (int)radius, (int)radius*2, (int)radius*2);
            }
        }
    }

    @Override
    public Vector<Point> getResizePoints() {
        return null;
    }

    @Override
    public DrawingItem createPreview() {
        return null;
    }
}
