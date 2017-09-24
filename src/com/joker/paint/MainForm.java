package com.joker.paint;

import javafx.beans.property.SetProperty;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Adam on 2017/9/15.
 */
public class MainForm extends JFrame{

    private JPanel selectPanel;
    private DrawingBoard board;
    private BoardSettings settings;
    private JRadioButton buttonOpenImg,buttonDelete,buttonPoints,buttonPolygon,buttonOval;
    private JButton buttonColor,buttonStroke,buttonSave;
//    private JRadioButton
    private JCheckBox checkBoxFill;
    private void initButtons(){

    }
    public  MainForm(){
        this.setSize(new Dimension(700,700));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Paint");
        Toolkit tk=Toolkit.getDefaultToolkit();
        Dimension dim=tk.getScreenSize();
        int xpos=(dim.width/2)-(this.getWidth()/2),
                ypos=(dim.height/2)-(this.getHeight()/2);
        this.setLocation(xpos,ypos);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        settings=new BoardSettings(this);
        board=new DrawingBoard(settings);
        selectPanel=new JPanel();
        this.add(selectPanel);
        this.add(board);
    }
    public static void main(String[] args) {
        MainForm mainForm=new MainForm();

    }
}
