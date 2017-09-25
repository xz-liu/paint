package com.joker.paint;

import external.JFontChooser;
import external.StrokeChooserPanel;
import external.StrokeSample;
import javafx.beans.property.SetProperty;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.TextAction;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by Adam on 2017/9/15.
 */
public class MainForm extends JFrame{
    private DrawingBoard board;
    private JPanel mainPanel,select,history;
    private BoardSettings settings;
    private JButton buttonOpenImg,buttonDelete,buttonPoints,buttonPolygon,buttonOval,buttonRect,buttonText;
    private JTextField textImput;
    private JButton buttonColor,buttonFont,buttonMove,buttonSave;
    private JCheckBox checkBoxFill;
    private StrokeChooserPanel strokeChooserPanel;
    public JPanel getHistory() {
        return history;
    }
    private void initChoose() {
        StrokeSample[] samples = {
                new StrokeSample(new BasicStroke(1f)),
                new StrokeSample(new BasicStroke(2f)),
                new StrokeSample(new BasicStroke(3f)),
                new StrokeSample(new BasicStroke(4f)),
                new StrokeSample(new BasicStroke(5f)),
                new StrokeSample(new BasicStroke(6f)),
                new StrokeSample(new BasicStroke(7f)),
                new StrokeSample(new BasicStroke(8f)),
                new StrokeSample(new BasicStroke(9f)),
                new StrokeSample(new BasicStroke(10f)),
                new StrokeSample(new BasicStroke(11f)),
                new StrokeSample(new BasicStroke(12f))
        };
        strokeChooserPanel = new StrokeChooserPanel(new StrokeSample(new BasicStroke(3f)), samples);
        strokeChooserPanel.addSelectorListener(e->{
           settings.setStroke(strokeChooserPanel.getSelectedStroke());
        });
    }
    private void initButtons(){
        initChoose();
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
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setDialogTitle("Choose Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files","gif","png","jpg","bmp","jpeg"));
            while (fileChooser.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION){
                int confirmDialog = JOptionPane.showConfirmDialog(this, "Selection failed,continue?");
                if(confirmDialog!=0){
                    return;
                }
            }
            File file=fileChooser.getSelectedFile();
            try {
                settings.setImgNow(ImageIO.read(file));
            }catch (Exception ex){
                JOptionPane.showMessageDialog(this,"Read file failed");
            }
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

        buttonFont=new JButton("Font");
        buttonFont.addActionListener(e->{
            JFontChooser fontChooser=new JFontChooser();
            fontChooser.showDialog(this);
            settings.setFont(fontChooser.getSelectedFont());
        });


        buttonMove=new JButton("Move");
        buttonMove.addActionListener(e->{
            settings.setType(BoardSettings.Type.MOVE);
        });

        textImput=new JTextField(15);
        textImput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setText();
            }
            private void setText(){
                    settings.setText(textImput.getText());
            }
        });

        checkBoxFill=new JCheckBox("Fill");
        checkBoxFill.addActionListener(e->{
            settings.setFill(checkBoxFill.isSelected());
        });
        select.add(strokeChooserPanel);
        select.add(buttonColor);
        select.add(buttonFont);
        select.add(Box.createRigidArea(new Dimension(3,1)));
        select.add(buttonPoints);
        select.add(buttonOval);
        select.add(buttonRect);
        select.add(buttonPolygon);
        select.add(checkBoxFill);
        select.add(buttonDelete);
        select.add(buttonMove);
        select.add(textImput);
        select.add(buttonText);
        select.add(buttonOpenImg);

    }
    public  MainForm(){
        try {
            this.setUndecorated(false);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,"LOAD NAPKIN FAILED");
        }
        this.setSize(new Dimension(800,500));
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
