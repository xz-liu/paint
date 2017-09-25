package com.joker.paint;

import javafx.beans.property.SetProperty;
import napkin.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Adam on 2017/9/15.
 */
public class MainForm extends JFrame{
    private DrawingBoard board;
    private JPanel mainPanel,select,history;
    private BoardSettings settings;
    private JButton buttonOpenImg,buttonDelete,buttonPoints,buttonPolygon,buttonOval,buttonRect,buttonText;
    private JTextField textImput;
    private JButton buttonColor,buttonStroke,buttonSave;
    private JCheckBox checkBoxFill;

    public JPanel getHistory() {
        return history;
    }

    private void initButtons(){
        buttonPoints=new JButton("Pen");
        buttonPoints.addActionListener(e-> {
            settings.setType(BoardSettings.Type.POINTS);
            settings.clearPoints();
        });
        buttonOval=new JButton("Oval");
        buttonOval.addActionListener(e-> {
            settings.setType(BoardSettings.Type.OVAL);
            settings.clearPoints();
        });
        buttonPolygon=new JButton("Polygon");
        buttonPolygon.addActionListener(e-> {
            settings.setType(BoardSettings.Type.POLYGON);
            settings.clearPoints();
        });
        buttonDelete=new JButton("Del");
        buttonDelete.addActionListener(e-> {
            settings.setType(BoardSettings.Type.DELETE);
            settings.clearPoints();
        });
        buttonOpenImg=new JButton("Image");
        buttonOpenImg.addActionListener(e-> {
            settings.setType(BoardSettings.Type.IMAGE);
            settings.clearPoints();
        });
        buttonRect=new JButton("Rect");
        buttonRect.addActionListener(e-> {
            settings.setType(BoardSettings.Type.RECT);
            settings.clearPoints();
        });
        buttonText=new JButton("Text");
        buttonText.addActionListener(e ->  {
                settings.setType(BoardSettings.Type.TEXT);
            settings.clearPoints();
        });

        buttonColor=new JButton("Color");
        buttonColor.addActionListener(e->{
            Color color=JColorChooser.showDialog(settings.mainFrame,"Choose Color",Color.BLACK);
            settings.setColor(color);
        });

        select.add(buttonColor);
        select.add(buttonPoints);
        select.add(buttonOval);
        select.add(buttonRect);
        select.add(buttonPolygon);
        select.add(buttonText);
        select.add(buttonOpenImg);
        select.add(buttonDelete);

        textImput=new JTextField(15);
    }
    public  MainForm(){
        try {
            this.setUndecorated(false);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,"LOAD NAPKIN FAILED");
        }
        this.setSize(new Dimension(700,500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Paint");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel=new JPanel();
        mainPanel.setSize(new Dimension(700,500));
        mainPanel.setLayout(new BorderLayout(1,1));
        select=new JPanel();
        history=new JPanel();
        JScrollPane historyScroll=new JScrollPane(history);

        historyScroll.setBounds(0,0,0,50);
        history.setAutoscrolls(true);
        historyScroll.setPreferredSize(new Dimension(80,700));
        history.setSize(new Dimension(100,700));
        history.setLayout(new BoxLayout(history,BoxLayout.PAGE_AXIS));
        select.setSize(new Dimension(700,30));
        settings=new BoardSettings(this);
        board=new DrawingBoard(settings);
        initButtons();
        mainPanel.add(board);
        mainPanel.add(select,BorderLayout.NORTH);
//        mainPanel.add(history,BorderLayout.WEST);
        mainPanel.add(historyScroll,BorderLayout.WEST);
        this.add(mainPanel);
        select.revalidate();
    }
    public static void main(String[] args) {
        MainForm mainForm=new MainForm();
    }
}
