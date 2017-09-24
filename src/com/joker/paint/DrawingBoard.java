package com.joker.paint;


import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.util.LinkedList;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/15.
 */
class BoardMouseListener implements MouseListener,MouseMotionListener {

    BoardSettings settings;
    DrawingBoard drawingBoard;
    LinkedList<DrawingItem> itemsList;
    Point begin, now, end;
    Stroke dash;
    private void setPreview(){
        drawingBoard.setPreview(null);
    }
    private void setPreview(Point beg,Point mouse){
        int xx = Math.min(beg.x, mouse.x),
                yy = Math.min(beg.y, mouse.y),
                disX = Math.abs(beg.x - mouse.x),
                disY = Math.abs(beg.y - mouse.y);
        switch (settings.getType()){
            case RECT:
            case IMAGE:
                drawingBoard.setPreview(new DrawingShape(Color.BLACK,new Rectangle(xx,yy,disX,disY),false,dash));
                break;
            case OVAL:
                drawingBoard.setPreview(new DrawingShape(Color.BLACK,new Ellipse2D.Float(xx,yy,disX,disY),false,dash));
                break;
            case POINTS:
            case POLYGON:
                drawingBoard.setPreview(new DrawingPoints(Color.BLACK,settings.getPoints().toArray(new Point[0]),settings.stroke));
                break;
        }
    }
    
    public BoardMouseListener(BoardSettings settings,
                              LinkedList<DrawingItem> itemsList,
                              DrawingBoard drawingBoard) {
        this.settings = settings;
        this.itemsList = itemsList;
        this.drawingBoard = drawingBoard;
        this.dash = new BasicStroke(2.5f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 3.5f, new float[]{15, 10,},
                0f);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        now = new Point(e.getPoint());
        if (settings.getType() == BoardSettings.Type.POINTS) {
            settings.getPoints().addElement(now);
        }
        setPreview(begin,now);
        drawingBoard.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        if (begin != null) {
            if (settings.getType() == BoardSettings.Type.POLYGON) {
                now = new Point(e.getPoint());
                JOptionPane.showMessageDialog(settings.mainFrame,"ADD POINT ("+now.x+","+now.y+")");
                settings.points.addElement(now);
            }
            setPreview(begin,now);
        }
        drawingBoard.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
            begin = new Point(e.getPoint());
            settings.getPoints().addElement(begin);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (begin != null) {
            end = new Point(e.getPoint());
            settings.getPoints().addElement(end);
            Vector<Point> points = settings.getPoints();
            double xx = Math.min(points.firstElement().x, points.lastElement().x),
                    yy = Math.min(points.firstElement().y, points.lastElement().y),
                    disX = Math.abs(points.firstElement().x - points.lastElement().x),
                    disY = Math.abs(points.firstElement().y - points.lastElement().y);
            switch (settings.getType()) {
                case OVAL:
                    itemsList.add(new DrawingShape(settings.color,
                            new Ellipse2D.Double(xx, yy, disX, disY), settings.isFill(), settings.getStroke()));
                    settings.setPoints(null);
                    setPreview();
                    break;
                case RECT:
                    itemsList.add(new DrawingShape(settings.color,
                            new Rectangle((int) xx, (int) yy, (int) disX, (int) disY), settings.isFill(), settings.getStroke()));
                    settings.setPoints(null);
                    setPreview();
                    break;
                case IMAGE:
                    itemsList.add(new DrawingImage(settings.getImgNow(),
                            new Rectangle((int) xx, (int) yy, (int) disX, (int) disY), settings.mainFrame));
                    settings.setPoints(null);
                    setPreview();
                    break;
                case POINTS:
                    itemsList.add(new DrawingPoints(settings.color, points.toArray(new Point[0]), settings.getStroke()));
                    settings.setPoints(null);
                    setPreview();
                    break;
                default:
                    break;
            }
            drawingBoard.repaint();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (settings.getType() == BoardSettings.Type.POLYGON) {
            begin = null;
        }
    }


    @Override
    public void mouseExited(MouseEvent e) {
        if (settings.getType() == BoardSettings.Type.POLYGON) {
            Vector<Point> points = settings.getPoints();
            int[] xpoints = new int[points.size()], ypoints = new int[points.size()];
            int i = 0;
            StringBuilder stringBuilder=new StringBuilder();
            stringBuilder.append(points.size()+"Points:");
            for (Point point : points) {
                xpoints[i] = (point.x);
                ypoints[i] = (point.y);
                stringBuilder.append("("+point.x+","+point.y+")");
                i++;
            }
            JOptionPane.showMessageDialog(settings.mainFrame,stringBuilder.toString());
            itemsList.add(new DrawingPolygon(settings.color,
                    new Polygon(xpoints, ypoints, points.size()), settings.isFill(), settings.getStroke()));

            settings.setPoints(null);
        }
    }
}
public class DrawingBoard extends JPanel  {
    BoardSettings settings;
    LinkedList<DrawingItem> itemsList;
    DrawingItem preview;
    BoardMouseListener listener;
    DrawingBoard(BoardSettings settings){
        this.settings=settings;
        setSize(new Dimension(700,700));
        itemsList=new LinkedList<>();
        listener=new BoardMouseListener(settings,itemsList,this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }
    void setPreview(DrawingItem item){
        this.preview=item;
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(itemsList!=null)
            for(DrawingItem items:itemsList) {
                items.draw(g);
            }
        if(preview!=null) {
            preview.draw(g);
        }
    }

}
