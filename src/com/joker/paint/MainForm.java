package com.joker.paint;

import external.JFontChooser;
import external.StrokeChooserPanel;
import external.StrokeSample;
import javafx.event.ActionEvent;
import javafx.scene.control.ColorPicker;
import napkin.NapkinLookAndFeel;
//import napkin.NapkinLookAndFeel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;

/**
 * Created by Adam on 2017/9/15.
 */
public class MainForm extends JFrame{
    private DrawingBoard board;
    private JPanel mainPanel,select,history, historyMain;
    private BoardSettings settings;
    private JButton buttonOpenImg,buttonDelete,buttonPoints,buttonPolygon,buttonOval,buttonRect,buttonText,buttonLines;
    private JTextField textImput;
    private JButton buttonColor,buttonFont,buttonClear,buttonMove,buttonTop,buttonBottom,buttonSave,buttonSelect;
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
                new StrokeSample(new BasicStroke(12f)),
                new StrokeSample(new BasicStroke(1.5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 2.5f, new float[]{15, 10,},
                        0f)),
                new StrokeSample(new BasicStroke(2.5f, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_ROUND, 3.5f, new float[]{15, 10,},
                0f)),
                new StrokeSample(new BasicStroke(3.5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 4.5f, new float[]{15, 10,},
                        0f)),
                new StrokeSample(new BasicStroke(4.5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 5.5f, new float[]{15, 10,},
                        0f)),

                new StrokeSample(new BasicStroke(5.5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 6.5f, new float[]{15, 10,},
                        0f)),

                new StrokeSample(new BasicStroke(6.5f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_ROUND, 7.5f, new float[]{15, 10,},
                        0f)),
        };
        strokeChooserPanel = new StrokeChooserPanel(new StrokeSample(new BasicStroke(3f)), samples);
        strokeChooserPanel.addSelectorListener(e->{
           settings.setStroke(strokeChooserPanel.getSelectedStroke());
        });
    }
    private void initButtons(){
        initChoose();
        buttonSelect=new JButton("Select");
        buttonSelect.addActionListener(e->{
            settings.setType(BoardSettings.Type.SELECT);
            settings.clearPoints();
            settings.nextResizePoint(null);
        });
        buttonLines=new JButton("Lines");
        buttonLines.addActionListener(e->{
            settings.setType(BoardSettings.Type.LINES);
            settings.clearPoints();
        });
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
        buttonOpenImg=new JButton("Image");
        buttonOpenImg.addActionListener(e-> {
            settings.setType(BoardSettings.Type.IMAGE);
            JFileChooser fileChooser=new JFileChooser();
            fileChooser.setDialogTitle("Choose Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files","gif","png","jpg","bmp","jpeg"));
            while (fileChooser.showOpenDialog(this)!=JFileChooser.APPROVE_OPTION){
                int confirmDialog = JOptionPane.showConfirmDialog(this, "Selection failed, continue?");
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
            Color color=JColorChooser.showDialog(null,"Choose Color", Color.BLACK);
            settings.setColor(color);
        });

        buttonFont=new JButton("Font");
        buttonFont.addActionListener(e->{
            JFontChooser fontChooser=new JFontChooser();
            fontChooser.showDialog(this);
            settings.setFont(fontChooser.getSelectedFont());
        });

        buttonClear=new JButton("Clear");
        buttonClear.addActionListener(e->{
            board.clearBoard();
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
        buttonSave=new JButton("Save");
        buttonSave.addActionListener(e->{
            BufferedImage image=board.getImage();
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // save to file
                try {
                    ImageIO.write(image, "jpg", file);
                }catch (IOException ioe){
                    JOptionPane.showMessageDialog(this,"Save Failed");
                }
            }
        });

        checkBoxFill=new JCheckBox("Fill");
        checkBoxFill.addActionListener(e->{
            settings.setFill(checkBoxFill.isSelected());
        });
        select.add(buttonSave);
        select.add(strokeChooserPanel);
        select.add(buttonColor);
        select.add(buttonSelect);
        select.add(Box.createRigidArea(new Dimension(3,1)));
        select.add(buttonOpenImg);
        select.add(buttonPoints);
        select.add(buttonLines);
        select.add(buttonOval);
        select.add(buttonRect);
        select.add(buttonPolygon);
        select.add(checkBoxFill);
        select.add(buttonFont);
        select.add(textImput);
        select.add(buttonText);
        select.add(buttonClear);

    }
    private void initHistory(){

        buttonTop=new JButton("Top");
        buttonTop.addActionListener(e->{
            settings.setType(BoardSettings.Type.TOP);
            settings.clearPoints();
        });
        buttonBottom=new JButton("Bottom");
        buttonBottom.addActionListener(e->{
            settings.setType(BoardSettings.Type.BOTTOM);
            settings.clearPoints();
        });
        buttonDelete=new JButton("Del");
        buttonDelete.addActionListener(e-> {
            settings.setType(BoardSettings.Type.DELETE);
            settings.clearPoints();
        });
        buttonMove=new JButton("Move");
        buttonMove.addActionListener(e->{
            settings.setType(BoardSettings.Type.MOVE);
            settings.clearPoints();
        });

        historyMain =new JPanel();
        historyMain.setLayout(new BoxLayout(historyMain,BoxLayout.PAGE_AXIS));
        JPanel historyOptions=new JPanel();
        historyOptions.setLayout(new BorderLayout());
        historyOptions.add(buttonTop,BorderLayout.NORTH);
        historyOptions.add(buttonBottom,BorderLayout.SOUTH);
        historyOptions.add(buttonMove,BorderLayout.EAST);
        historyOptions.add(buttonDelete,BorderLayout.WEST);

        historyMain.add(historyOptions);

        history=new JPanel();
        JScrollPane historyScroll=new JScrollPane(history);

        historyScroll.setBounds(0,0,0,50);
        history.setAutoscrolls(true);
        historyScroll.setPreferredSize(new Dimension(80,700));
        history.setSize(new Dimension(100,700));
        history.setLayout(new BoxLayout(history,BoxLayout.PAGE_AXIS));
        historyMain.add(historyScroll);
    }
    public  MainForm(){
        try {
            this.setUndecorated(false);
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel(new NapkinLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this,"LOAD UI FAILED");
        }

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(d.width-100, d.height-100);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Paint");
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e) {
                if (JOptionPane.showConfirmDialog(settings.getMainFrame(),
                        "Discard all changes?") == 0) {
                    e.getWindow().dispose();
                    System.exit(0);
                }
            }
        });
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainPanel=new JPanel();
        mainPanel.setSize(d);
        mainPanel.setLayout(new BorderLayout(1,1));
        select=new JPanel();
        initHistory();
        select.setSize(new Dimension(700,30));
        settings=new BoardSettings(this);
        board=new DrawingBoard(settings,new Dimension(d.width-historyMain.getWidth(),d.height-historyMain.getHeight()));
        initButtons();
        mainPanel.add(board);
        mainPanel.add(select,BorderLayout.NORTH);
//        mainPanel.add(history,BorderLayout.WEST);
        mainPanel.add(historyMain,BorderLayout.WEST);
        this.add(mainPanel);
        select.revalidate();
    }
    public static void main(String[] args) {
        MainForm mainForm=new MainForm();
    }
}
