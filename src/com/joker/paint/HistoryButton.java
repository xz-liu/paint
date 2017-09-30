package com.joker.paint;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class HistoryButton extends JButton {
    DrawingItem item;
    DrawingBoard board;
    BoardSettings settings;
    private void replaceItems(LinkedList<DrawingItem> itemsList, boolean isFirst){
        itemsList.remove(item);
        if (isFirst){
            itemsList.addFirst(item);
        }else {
            itemsList.addLast(item);
        }
    }
    public void delete(){
        board.getItemsList().remove(this.item);
        settings.getHistory().remove(this);
        board.setPreview(null);
    }
    public void goBottom(){
        replaceItems(board.getItemsList(),true);
        settings.getHistory().remove(this);
        settings.getHistory().add(this,0);
        board.setPreview(null);
    }
    public void goTop(){
        replaceItems(board.getItemsList(),false);
        settings.getHistory().remove(this);
        settings.getHistory().add(this);
        board.setPreview(null);
    }
    public void move(){
        settings.setItemReplacing(this.item);
        board.setPreview(this.item.createPreview());
    }
    public void reposition(){
        settings.getHistory().revalidate();
        settings.getHistory().repaint();
        board.repaint();
    }
    public HistoryButton(DrawingBoard board,String title,DrawingItem item){
        super(title);
        this.item=item;
        this.board=board;
        this.settings=board.settings;
        LinkedList<DrawingItem> itemsList=board.getItemsList();
        final ActionListener listener= e->{
            switch (settings.getType()) {
                case DELETE:
                    delete();
                    break;
                case BOTTOM:
                    goBottom();
                    break;
                case TOP:
                    goTop();
                    break;
                case MOVE:
                    move();
                    break;
                default:
                    board.setPreview(this.item.createPreview());
                    break;

            }
            reposition();
        };
        this.addActionListener(listener);
    }
}