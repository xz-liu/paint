package com.joker.paint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Created by Adam on 2017/9/15.
 */
class BoardMouseListener implements MouseListener, MouseMotionListener {

    BoardSettings settings;
    DrawingBoard drawingBoard;
    //    LinkedList<JButton> buttons;
    Point begin, now, end;

    private void setPreview() {
        drawingBoard.setPreview(null);
    }

    private void setPreview(DrawingItem item) {
        drawingBoard.setPreview(item);
    }

    private void setPreview(Point beg, Point mouse) {
        int xx = Math.min(beg.x, mouse.x),
                yy = Math.min(beg.y, mouse.y),
                disX = Math.abs(beg.x - mouse.x),
                disY = Math.abs(beg.y - mouse.y);
        switch (settings.getType()) {
            case RECT:
                drawingBoard.setPreview(new DrawingShape(settings.getColor(),
                        new Rectangle(xx, yy, disX, disY), settings.isFill(), settings.getStroke()));
                break;
            case IMAGE:
                drawingBoard.setPreview(new DrawingImage(settings.getImgNow(),
                        new Rectangle(xx, yy, disX, disY), settings.getMainFrame()));
                break;
            case OVAL:
                drawingBoard.setPreview(new DrawingShape(settings.getColor(),
                        new Ellipse2D.Double(xx, yy, disX, disY), settings.isFill(), settings.getStroke()));
                break;
            case POINTS:
                drawingBoard.setPreview(new DrawingPoints(settings.getColor(),
                        settings.getPoints(), settings.getStroke()));
                break;
            case TEXT:
                drawingBoard.setPreview(new DrawingText(settings.getText(), now,
                        settings.getFont(), settings.getColor()));
                break;
        }
    }

    private void repositionPreview(Point pos) {
        if (drawingBoard.getPreview() != null)
            drawingBoard.getPreview().reposition(pos);
    }

    public BoardMouseListener(BoardSettings settings,
                              DrawingBoard drawingBoard) {
        this.settings = settings;
        this.drawingBoard = drawingBoard;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        now = new Point(e.getPoint());
        if (settings.getType() == BoardSettings.Type.POINTS) {
            settings.getPoints().addElement(now);
            setPreview(begin, now);
        } else if (settings.getType() == BoardSettings.Type.SELECT) {
            ResizePoint resizePoint = settings.getPointNow();
            if (resizePoint != null && resizePoint.valid())
                resizePoint.getItem().resize(settings.getSelectPoint(), now);
        } else if (settings.getType() != BoardSettings.Type.MOVE)
            setPreview(begin, now);
        else {
            repositionPreview(now);
        }
        drawingBoard.repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point mouse = new Point(e.getPoint());
        switch (settings.getType()) {
            case SELECT:
                selectResizePoint(mouse);
                break;
            case POLYGON:
            case LINES:
                Vector<Point> previewPoints =
                        (Vector<Point>) settings.getPoints().clone();
                previewPoints.add(mouse);
                drawingBoard.setPreview(new DrawingPoints(settings.color,
                        previewPoints, settings.getStroke(), true));
                drawingBoard.repaint();
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        now = new Point(e.getPoint());
        if (begin != null) {
            if (settings.getType() == BoardSettings.Type.POLYGON) {
                settings.getPoints().addElement(now);
                setPreview(begin, now);
            } else if (settings.getType() == BoardSettings.Type.LINES) {
                settings.getPoints().addElement(now);
                setPreview(begin, now);
            } else if (settings.getType() != BoardSettings.Type.MOVE)
                setPreview(begin, now);
            else {
                repositionPreview(now);
            }
        }
        drawingBoard.repaint();
    }

    private void selectResizePoint(Point point) {
        for (ListIterator<DrawingItem> iter =
             drawingBoard.getItemsList().
                     listIterator(drawingBoard.getItemsList().size()); iter.hasPrevious(); ) {
            DrawingItem item = iter.previous();
            int result;
            if ((result = item.getResizePoint().selected(point)) >= 0) {
                settings.nextResizePoint(item.getResizePoint());
                settings.setSelectPoint(result);
                drawingBoard.repaint();
                return;
            }
        }
        for (ListIterator<DrawingItem> iter =
             drawingBoard.getItemsList().
                     listIterator(drawingBoard.getItemsList().size()); iter.hasPrevious(); ) {
            DrawingItem item = iter.previous();
            if (item.contains(point)) {
                settings.nextResizePoint(item.getResizePoint());
                settings.setSelectPoint(0);
                drawingBoard.repaint();
                return;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        begin = new Point(e.getPoint());
        if (settings.getType() == BoardSettings.Type.SELECT) {
            selectResizePoint(begin);
        } else if (settings.getType() != BoardSettings.Type.POLYGON &&
                settings.getType() != BoardSettings.Type.LINES) {
            settings.getPoints().addElement(begin);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (begin != null) {
            end = new Point(e.getPoint());
            if (settings.getType() != BoardSettings.Type.POLYGON &&
                    settings.getType() != BoardSettings.Type.LINES) {
                settings.getPoints().addElement(end);
            }
            Vector<Point> points = settings.getPoints();
            if (points.isEmpty()) return;
            double xx = Math.min(points.firstElement().x, points.lastElement().x),
                    yy = Math.min(points.firstElement().y, points.lastElement().y),
                    disX = Math.abs(points.firstElement().x - points.lastElement().x),
                    disY = Math.abs(points.firstElement().y - points.lastElement().y);
            switch (settings.getType()) {
                case OVAL:
                    addListItem(new DrawingShape(settings.color,
                            new Ellipse2D.Double(xx, yy, disX, disY),
                            settings.isFill(), settings.getStroke()));
                    setPreview();
                    break;
                case RECT:
                    addListItem(new DrawingShape(settings.color,
                            new Rectangle((int) xx, (int) yy, (int) disX, (int) disY),
                            settings.isFill(), settings.getStroke()));
                    setPreview();
                    break;
                case IMAGE:
                    addListItem(new DrawingImage(settings.getImgNow(),
                            new Rectangle((int) xx, (int) yy, (int) disX, (int) disY), settings.mainFrame));
                    setPreview();
                    break;
                case POINTS:
                    addListItem(new DrawingPoints(settings.color, points, settings.getStroke()));
                    setPreview();
                    break;
                case TEXT:
                    addListItem(new DrawingText(settings.getText(), end, settings.getFont(), settings.getColor()));
                    setPreview();
                    break;
                case MOVE:
                    settings.replace(end);
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
        if (drawingBoard.getPreview() != null
                && drawingBoard.getPreview().isSelectedPreview()
                && settings.getType() != BoardSettings.Type.MOVE) {
            setPreview();
            drawingBoard.repaint();
        }
        Vector<Point> points = settings.getPoints();
        if (points == null || points.size() <= 1) return;
        if (settings.getType() == BoardSettings.Type.POLYGON) {
            int[] xpoints = new int[points.size()], ypoints = new int[points.size()];
            int i = 0;
            for (Point point : points) {
                xpoints[i] = (point.x);
                ypoints[i] = (point.y);
                i++;
            }
            addListItem(new DrawingPolygon(settings.color,
                    new Polygon(xpoints, ypoints, points.size()), settings.isFill(), settings.getStroke()));
            setPreview();
            drawingBoard.repaint();
        } else if (settings.getType() == BoardSettings.Type.LINES) {

            addListItem(new DrawingLines(settings.getColor(), points, settings.getStroke()));
            setPreview();
            drawingBoard.repaint();
        }
    }

    private void addListItem(DrawingItem item) {
        if (settings.getPoints() != null) {
            drawingBoard.getItemsList().add(item);
            HistoryButton button = new HistoryButton(drawingBoard, settings.getType().toString(), item);
            item.setRelatedButton(button);
            settings.getHistory().add(button);
            settings.getHistory().revalidate();
            settings.setPoints(null);
        }
    }
}

public class DrawingBoard extends JPanel {
    BoardSettings settings;
    LinkedList<DrawingItem> itemsList;
    DrawingItem preview;
    BoardMouseListener listener;

    private BufferedImage image;

    DrawingBoard(BoardSettings settings, Dimension dimension) {
        this.settings = settings;
        setSize(dimension);
        itemsList = new LinkedList<>();
        listener = new BoardMouseListener(settings, this);
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);
    }

    public void clearBoard() {
        itemsList.clear();
        settings.getHistory().removeAll();
        this.setPreview(null);
        this.repaint();
        if (settings.typeIsFunction())
            settings.setType(BoardSettings.Type.POINTS);
        settings.getHistory().revalidate();
        settings.getHistory().repaint();
    }

    public LinkedList<DrawingItem> getItemsList() {
        return itemsList;
    }

    void setPreview(DrawingItem item) {
        this.preview = item;
    }

    public DrawingItem getPreview() {
        return preview;
    }

    public BufferedImage getImage() {
        image = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = image.createGraphics();
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        this.setPreview(null);
        this.print(g);
        return image;
    }

    public void readList(LinkedList<DrawingItem> list) {
        clearBoard();
        itemsList.addAll(list);
        for (DrawingItem item : itemsList) {
            item.initResizePoint();
            item.setRelatedButton(new HistoryButton
                    (this, item.getType().toString(), item));
            settings.getHistory().add(item.getRelatedButton());
        }
        settings.getHistory().revalidate();
        settings.getHistory().repaint();
        repaint();
    }


    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, getWidth(), getHeight());
        if (itemsList != null)
            itemsList.forEach(item -> {
                item.draw(g);
                if (settings.getType() == BoardSettings.Type.SELECT) {
                    item.drawResizePoint(g);
                }
            });
        if (settings.getType() == BoardSettings.Type.SELECT &&
                settings.getPointNow() != null) {
            settings.getPointNow().getItem().drawBounds(g);
        }
        if (preview != null) {
            preview.draw(g);
        }
//        selectBoard.repaint(new Rectangle(0,0,700,30));
    }

}
