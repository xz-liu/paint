package com.joker.paint;

//import com.sun.codemodel.internal.JOp;

import external.JFontChooser;
import external.StrokeChooserPanel;
import external.StrokeSample;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Adam on 2017/9/15.
 */
public class MainForm extends JFrame {
    private DrawingBoard board;
    private JToolBar select;
    private JPanel mainPanel, history, historyMain;
    private BoardSettings settings;
    private JButton buttonOpenImg, buttonDelete,
            buttonPoints, buttonPolygon, buttonOval,
            buttonRect, buttonText, buttonLines;
    private JButton buttonColor, buttonFont, buttonClear,
            buttonMove, buttonTop, buttonBottom, buttonSave, buttonSelect;
    private JCheckBox checkBoxFill;
    private JTextField textImput;
    private StrokeChooserPanel strokeChooserPanel;
    private JLabel labelModeNow;

    public JPanel getHistory() {
        return history;
    }

    public DrawingBoard getBoard() {
        return board;
    }

    public StrokeChooserPanel getStrokeChooserPanel() {
        return strokeChooserPanel;
    }

    private void initChoose() {
        StrokeSample[] samples = StrokeLibrary.strokes;
        strokeChooserPanel = new StrokeChooserPanel(samples[2], samples);
        strokeChooserPanel.addSelectorListener(e -> {
            settings.setStroke(strokeChooserPanel.getSelectedStroke());
        });
    }

    boolean asImageFile(File file) {
        String name = file.getName(), extension;
        int extBegin = name.lastIndexOf('.');
//        boolean asImageFile;
        if (extBegin == -1) return false;
        else if (extBegin == name.length() - 1) return false;
        else {
            extBegin++;
            extension = name.substring(extBegin);
            String[] exts = {"jpg", "png", "gif", "jpeg"};
            for (String ext : exts) {
                if (ext.equals(extension)) return true;
            }
        }
        return false;
    }

    private void initButtons() {

        buttonSelect = new JButton("Resize");
        buttonSelect.addActionListener(e -> {
            settings.setType(BoardSettings.Type.SELECT);
            settings.nextResizePoint(null);
            settings.clearPoints();
        });
        buttonLines = new JButton("Lines");
        buttonLines.addActionListener(e -> {
            settings.setType(BoardSettings.Type.LINES);
            settings.clearPoints();
            board.repaint();
        });
        buttonPoints = new JButton("Pen");
        buttonPoints.addActionListener(e -> {
            settings.setType(BoardSettings.Type.POINTS);
            settings.clearPoints();
        });
        buttonOval = new JButton("Oval");
        buttonOval.addActionListener(e -> {
            settings.setType(BoardSettings.Type.OVAL);
            settings.clearPoints();
        });
        buttonPolygon = new JButton("Polygon");
        buttonPolygon.addActionListener(e -> {
            settings.setType(BoardSettings.Type.POLYGON);
            settings.clearPoints();
        });
        buttonOpenImg = new JButton("Open");
        buttonOpenImg.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Choose Image");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image Files", "gif", "png", "jpg", "bmp", "jpeg"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Painting Files", "pnt"));
            while (fileChooser.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) {
                int confirmDialog = JOptionPane.showConfirmDialog(this, "Selection failed, continue?");
                if (confirmDialog != 0) {
                    return;
                }
            }
            File file = fileChooser.getSelectedFile();
            try {
                if (asImageFile(file)) {
                    settings.setType(BoardSettings.Type.IMAGE);
                    settings.setImgNow(ImageIO.read(file));
                } else {
                    board.readList(ListIO.readList(this, file));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Read file failed");
            }
            settings.clearPoints();
        });
        buttonRect = new JButton("Rect");
        buttonRect.addActionListener(e -> {
            settings.setType(BoardSettings.Type.RECT);
            settings.clearPoints();
        });
        buttonText = new JButton("Text");
        buttonText.addActionListener(e -> {
            settings.setType(BoardSettings.Type.TEXT);
            settings.clearPoints();
        });

        buttonColor = new JButton("Color");
        buttonColor.addActionListener(e -> {
            Color color = JColorChooser.showDialog(null, "Choose Color", Color.BLACK);
            settings.setColor(color);
        });

        buttonFont = new JButton("Font");
        buttonFont.addActionListener(e -> {
            JFontChooser fontChooser = new JFontChooser();
            fontChooser.showDialog(this);
            settings.setFont(fontChooser.getSelectedFont());
        });

        buttonClear = new JButton("Clear");
        buttonClear.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(settings.getMainFrame(),
                    "Clear the paint board and discard all changes?") == 0) {
                board.clearBoard();
            }
        });

        textImput = new JTextField(15);
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

            private void setText() {
                settings.setText(textImput.getText());
            }
        });
        buttonSave = new JButton("Save");
        buttonSave.addActionListener(e -> {
            BufferedImage image = board.getImage();
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter
                    (new FileNameExtensionFilter("Image File", "jpg", "png", "gif", "jpeg"));
            fileChooser.addChoosableFileFilter
                    (new FileNameExtensionFilter("Paint Save File", "pnt"));
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // save to file
                try {
                    String name = file.getName();
                    String path = file.getAbsolutePath();
                    String filter = fileChooser.getFileFilter().getDescription();
                    if (filter.contains("Image")) {
                        if (name.lastIndexOf('.') == -1) {
                            file = new File(path + ".jpg");
                        }
                        ImageIO.write(image, "jpg", file);
                    } else {
                        if (!name.substring(name.lastIndexOf('.') + 1).equals("pnt")) {
                            file = new File(path + ".pnt");
                        }
                        ListIO.saveList(this, board.getItemsList(), file);
                    }
                } catch (IOException ioe) {
                    JOptionPane.showMessageDialog(this, "Save Failed");
                }
            }
        });

        checkBoxFill = new JCheckBox("Fill");
        checkBoxFill.addActionListener(e -> {
            settings.setFill(checkBoxFill.isSelected());
        });

        JToolBar barSaveOpen=new JToolBar();
        JToolBar barChoose=new JToolBar();
        JToolBar barShapes=new JToolBar();
        JToolBar barText=new JToolBar();

        barSaveOpen.add(buttonSave);
        barSaveOpen.add(buttonOpenImg);

        barChoose.add(buttonSelect);
        barChoose.add(buttonClear);

        barShapes.add(strokeChooserPanel);
        barShapes.add(buttonColor);

        barShapes.add(buttonPoints);
        barShapes.add(buttonLines);
        barShapes.add(buttonOval);
        barShapes.add(buttonRect);
        barShapes.add(buttonPolygon);
        barShapes.add(checkBoxFill);

        barText.add(buttonFont);
        barText.add(buttonText);
        barText.add(textImput);

        select.add(barSaveOpen);
        select.add(barChoose);
        select.add(barShapes);
        select.add(barText);

        select.add(labelModeNow);

    }



    private void initHistory() {

        buttonTop = new JButton("Top");
        buttonTop.addActionListener(e -> {
            if (settings.getType()== BoardSettings.Type.SELECT){
                settings.getPointNow().getItem().getRelatedButton().goTop();
                settings.getPointNow().getItem().getRelatedButton().reposition();
            }
            else {
                settings.setType(BoardSettings.Type.TOP);
            }settings.clearPoints();

        });
        buttonBottom = new JButton("Bottom");
        buttonBottom.addActionListener(e -> {
            if (settings.getType() == BoardSettings.Type.SELECT) {
                settings.getPointNow().getItem().getRelatedButton().goBottom();
                settings.getPointNow().getItem().getRelatedButton().reposition();
            } else {
                settings.setType(BoardSettings.Type.BOTTOM);
            }
            settings.clearPoints();
            settings.clearPoints();
        });
        buttonDelete = new JButton("Del");
        buttonDelete.addActionListener(e -> {
            if (settings.getType()== BoardSettings.Type.SELECT){
                settings.getPointNow().getItem().getRelatedButton().delete();
                settings.getPointNow().getItem().getRelatedButton().reposition();
            }
            else {
                settings.setType(BoardSettings.Type.DELETE);
            }settings.clearPoints();
            settings.clearPoints();
        });
        buttonMove = new JButton("Move");
        buttonMove.addActionListener(e -> {
            if (settings.getType() == BoardSettings.Type.SELECT) {
                settings.getPointNow().getItem().getRelatedButton().move();
                settings.getPointNow().getItem().getRelatedButton().reposition();
            } else {
                settings.setType(BoardSettings.Type.MOVE);
            }
            settings.clearPoints();
            settings.clearPoints();
        });

        historyMain = new JPanel();
        historyMain.setLayout(new BoxLayout(historyMain, BoxLayout.PAGE_AXIS));
        JPanel historyOptions = new JPanel();
        historyOptions.setLayout(new BorderLayout());
        historyOptions.add(buttonTop, BorderLayout.NORTH);
        historyOptions.add(buttonBottom, BorderLayout.SOUTH);
        historyOptions.add(buttonMove, BorderLayout.EAST);
        historyOptions.add(buttonDelete, BorderLayout.WEST);

        historyMain.add(historyOptions);

        history = new JPanel();
        JScrollPane historyScroll = new JScrollPane(history);

        historyScroll.setBounds(0, 0, 0, 50);
        history.setAutoscrolls(true);
        historyScroll.setPreferredSize(new Dimension(80, 500));
        history.setSize(new Dimension(100, 500));
        history.setLayout(new BoxLayout(history, BoxLayout.PAGE_AXIS));
        historyMain.add(historyScroll);
    }

    public MainForm() {
        try {
            this.setUndecorated(false);
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            UIManager.setLookAndFeel(new NapkinLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "LOAD UI FAILED");
        }

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(d.width *2/3, d.height *4/5);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setTitle("Paint");
        addWindowListener(new WindowAdapter() {
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
        mainPanel = new JPanel();
        mainPanel.setSize(d);
        mainPanel.setLayout(new BorderLayout(1, 1));
        select = new JToolBar();
        initHistory();
        select.setSize(new Dimension(700, 30));
        initChoose();

        labelModeNow=new JLabel("MODE : "+BoardSettings.INITIAL_TYPE.toString());

        settings = new BoardSettings(this);
        board = new DrawingBoard(settings,
                new Dimension(d.width - historyMain.getWidth(),
                        d.height - historyMain.getHeight()));
        initButtons();
        mainPanel.add(board);
        mainPanel.add(select, BorderLayout.NORTH);
//        mainPanel.add(history,BorderLayout.WEST);
        
        JToolBar toolBarHistory=new JToolBar(JToolBar.VERTICAL);
        toolBarHistory.add(historyMain);
        mainPanel.add(toolBarHistory, BorderLayout.WEST);
        this.add(mainPanel);
        select.revalidate();
    }

    public JLabel getLabelModeNow() {
        return labelModeNow;
    }

    private static MainForm mainForm;

    public static MainForm getMainFormInstance() {
        return mainForm;
    }

    public static void main(String[] args) {
        mainForm = new MainForm();
    }
}
