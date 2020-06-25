package frame;

import common.DataProcessing;
import common.Doc;
import common.Operator;
import common.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;

import static common.MainGUI.application;


public class FileFrame extends JFrame {

    String ID;
    String filename;
    String description;
    File src;

    JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);

    //文件下载的组件
    final Object[] columnNames = {"档案号", "创建者", "时间", "文件名", "文件描述"};

    JButton button1a = new JButton("下载");

    //文件上传的组件
    JLabel label1b = new JLabel("档案号");
    JLabel label2b = new JLabel("档案描述");
    JLabel label3b = new JLabel("档案文件名");

    JTextField jTextField1b = new JTextField();
    JTextField jTextField2b = new JTextField();
    JTextArea jTextArea1b = new JTextArea();

    JButton button1b = new JButton("打开");
    JButton button2b = new JButton("上传");
    JButton button3b = new JButton("取消");

    JFileChooser chooser = new JFileChooser();

    JPanel file_up = new JPanel();
    JPanel file_down = new JPanel();

    //构造方法
    public FileFrame(User user) {

        init(user);
        this.setSize(550, 400);
        this.setResizable(false);
        this.setLocation(650, 300);
        this.setTitle("文件管理界面");
    }

    public void init(User user) {
        this.add(jTabbedPane, BorderLayout.CENTER);

        //文件下载
        ArrayList<Doc> arrayList = new ArrayList<>();
        try {
            Enumeration<Doc> allDocs = DataProcessing.getAllDocs();
            while (allDocs.hasMoreElements()) {
                arrayList.add(allDocs.nextElement());
            }
        } catch (SQLException e) {
            System.out.println("数据库异常" + e.getMessage());
        }
        Object[][] rowData = new Object[arrayList.size()][5];
        for (int i = 0; i < arrayList.size(); i++) {
            rowData[i][0] = arrayList.get(i).getID();
            rowData[i][1] = arrayList.get(i).getCreator();
            rowData[i][2] = arrayList.get(i).getTimestamp();
            rowData[i][3] = arrayList.get(i).getFilename();
            rowData[i][4] = arrayList.get(i).getDescription();
        }

        DefaultTableModel myTableModel = new DefaultTableModel(rowData, columnNames);
        JTable files = new JTable(myTableModel);
        JScrollPane scorll = new JScrollPane(files);

        scorll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scorll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        files.setRowHeight(25);//设置每行的高度为30
        files.setRowSelectionAllowed(true);//设置可否被选择.默认为false
        files.setShowGrid(true);//是否显示网格线
        files.doLayout();
        files.setBackground(Color.white);
        file_down.add(scorll);
        file_down.add(button1a);
        jTabbedPane.add(file_down, "文件下载");

        //文件上传
        jTextField2b.setEditable(false);
        chooser.setCurrentDirectory(new File("."));
        file_up.setLayout(null);
        label1b.setBounds(150, 5, 200, 100);
        file_up.add(label1b);
        jTextField1b.setBounds(220, 45, 100, 20);
        file_up.add(jTextField1b);
        label2b.setBounds(150, 65, 200, 100);
        file_up.add(label2b);
        jTextArea1b.setBounds(220, 100, 100, 70);
        file_up.add(jTextArea1b);
        label3b.setBounds(150, 150, 200, 100);
        file_up.add(label3b);
        jTextField2b.setBounds(220, 195, 100, 20);
        file_up.add(jTextField2b);
        button1b.setBounds(330, 190, 60, 30);
        file_up.add(button1b);
        button2b.setBounds(180, 250, 60, 30);
        file_up.add(button2b);
        button3b.setBounds(260, 250, 60, 30);
        file_up.add(button3b);
        jTabbedPane.add(file_up, "文件上传");

        //非Operator不能使用文件上传
        if (!user.getRole().equalsIgnoreCase("Operator")) {
            jTabbedPane.setEnabledAt(1, false);
        }

        button1a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                application.sendData("Request Downloading");
                System.out.println();
//                try {
                int index = files.getSelectedRow();
                ID = (String) files.getValueAt(index, 0);
                application.sendData(ID);
//                    if (user.downloadFile(ID)) {
//                        JOptionPane.showMessageDialog(null, "下载文件成功", "提示", JOptionPane.WARNING_MESSAGE);
////                    } else {
////                        JOptionPane.showMessageDialog(null, "下载文件失败", "提示", JOptionPane.WARNING_MESSAGE);
////                    }
////                } catch (SQLException e2) {
//                    System.out.println("数据库异常1");
//                }
            }
        });

        button1b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = chooser.showOpenDialog(button1b);
                filename = chooser.getSelectedFile().getName();
                jTextField2b.setText(filename);
            }
        });

        button2b.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Operator user2 = (Operator) user;
                ID = jTextField1b.getText();
                description = jTextArea1b.getText();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                if (!chooser.isFileSelectionEnabled()) {
                    JOptionPane.showMessageDialog(null, "请选择文件！", "提示", JOptionPane.WARNING_MESSAGE);
                } else if (ID.equals("")) {
                    JOptionPane.showMessageDialog(null, "档案号不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                } else if (filename.equals("")) {
                    JOptionPane.showMessageDialog(null, "文件名不能为空！", "提示", JOptionPane.WARNING_MESSAGE);
                } else {
//                    try {
                    src = chooser.getSelectedFile();

                    application.sendData("Request Uploading");
                    application.sendData(user2.getName());
                    application.sendData(ID);
                    application.sendData(filename);
                    application.sendData(description);

//                        if (user2.uploadFile(ID, filename, description, src)) {
//                            JOptionPane.showMessageDialog(null, "上传文件成功", "提示", JOptionPane.WARNING_MESSAGE);
//                            jTextField1b.setText("");
//                            jTextField2b.setText("");
//                            jTextArea1b.setText("");
//                            Object[] Data = new Object[5];
//                            Data[0] = ID;
//                            Data[1] = user.getName();
//                            Data[2] = timestamp.toString();
//                            Data[3] = filename;
//                            Data[4] = description;
//                            myTableModel.insertRow(myTableModel.getRowCount(), Data);
//                        } else {
//                            JOptionPane.showMessageDialog(null, "上传文件失败", "提示", JOptionPane.WARNING_MESSAGE);
//                        }
//                    } catch (SQLException e2) {
//                        JOptionPane.showMessageDialog(null, "上传文件失败" + e2.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
//                    }
                }
            }
        });

        button3b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
