package com.joker.paint;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class ListIO {
    public static LinkedList<DrawingItem> readList(JFrame mainForm, File file) throws IOException {
        try {
//            String fileName = file.getName();
//            String tail = fileName.substring(fileName.lastIndexOf(".")+1);
//            if (tail != "pnt") throw new IOException("File type error");
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            LinkedList<DrawingItem> list = (LinkedList<DrawingItem>) ois.readObject();
            ois.close();
            fis.close();
            return list;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainForm, "File not found");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
           JOptionPane.showMessageDialog(mainForm, "Class not found");
           e.printStackTrace();
        }
        return null;
    }

    public static void saveList(JFrame mainForm, LinkedList<DrawingItem> target, File file) {
        try {
            String fileName = file.getName();
            String tail = fileName.substring(fileName.lastIndexOf(".")+1);
//            if (tail != "pnt") throw new IOException("File type error");
            if (!file.exists())
               file.createNewFile();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(mainForm,"Save file failed");
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(target);
            oos.flush();
            oos.close();
            fos.close();
        } catch(FileNotFoundException e) {
            JOptionPane.showMessageDialog(mainForm,"File not found");
            e.printStackTrace();
        } catch(IOException e) {
            JOptionPane.showMessageDialog(mainForm,"Saving error!");
            e.printStackTrace();
        }
    }
}
