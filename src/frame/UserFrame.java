package frame;

import common.Administrator;
import common.DataProcessing;
import common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;
import static common.MainGUI.application;


public class UserFrame extends JFrame {
    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    String username;
    String password;
    String role;
    char[] p;

    //新增用户的组件
    JLabel label1 = new JLabel("用户名");
    JLabel label2 = new JLabel("密码");
    JLabel label3 = new JLabel("权限");

    JTextField textField = new JTextField(10);
    JPasswordField passwordField = new JPasswordField(10);
    JComboBox<String> comboBox = new JComboBox<>(new String[]{"Browser", "Operator", "Administrator"});

    JButton button1 = new JButton("增加");
    JButton button2 = new JButton("取消");

    //修改用户的组件
    JLabel label1a = new JLabel("用户名");
    JLabel label2a = new JLabel("密码");
    JLabel label3a = new JLabel("权限");

    JComboBox<String> comboBox1a = new JComboBox<>();
    JTextField passwordFielda = new JPasswordField(10);
    JComboBox<String> comboBox2a = new JComboBox<>(new String[]{"Browser", "Operator", "Administrator"});

    JButton button1a = new JButton("修改");
    JButton button2a = new JButton("取消");

    //删除用户的组件
    JLabel label1b = new JLabel("用户名");
    JComboBox<String> comboBox1b = new JComboBox<>();

    JButton button1b = new JButton("删除");
    JButton button2b = new JButton("取消");

    JPanel panel_add = new JPanel();
    JPanel panel_chg = new JPanel();
    JPanel panel_del = new JPanel();

    //构造方法
    public UserFrame(User user) {
        init((Administrator) user);
        setSize(500, 350);
        this.setResizable(false);
        this.setLocation(650, 300);
        this.setTitle("用户管理界面");
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public void init(Administrator user) {
        fresh();

        panel_add.setLayout(null);
        label1.setBounds(150, 10, 200, 100);
        textField.setBounds(200, 50, 100, 20);
        label2.setBounds(150, 50, 200, 100);
        passwordField.setBounds(200, 90, 100, 20);
        label3.setBounds(150, 90, 200, 100);
        comboBox.setBounds(200, 130, 110, 20);
        button1.setBounds(160, 180, 60, 30);
        button2.setBounds(240, 180, 60, 30);
        panel_add.add(label1);
        panel_add.add(textField);
        panel_add.add(label2);
        panel_add.add(passwordField);
        panel_add.add(label3);
        panel_add.add(comboBox);
        panel_add.add(button1);
        panel_add.add(button2);
        tabbedPane.addTab("新增用户", panel_add);

        panel_chg.setLayout(null);
        label1a.setBounds(150, 10, 200, 100);
        panel_chg.add(label1a);
        comboBox1a.setBounds(200, 50, 100, 20);
        panel_chg.add(comboBox1a);
        label2a.setBounds(150, 50, 200, 100);
        panel_chg.add(label2a);
        passwordFielda.setBounds(200, 90, 100, 20);
        panel_chg.add(passwordFielda);
        label3a.setBounds(150, 90, 200, 100);
        panel_chg.add(label3a);
        comboBox2a.setBounds(200, 130, 110, 20);
        panel_chg.add(comboBox2a);
        button1a.setBounds(160, 180, 60, 30);
        panel_chg.add(button1a);
        button2a.setBounds(240, 180, 60, 30);
        panel_chg.add(button2a);
        tabbedPane.addTab("修改用户", panel_chg);

        panel_del.setLayout(null);
        label1b.setBounds(150, 10, 200, 100);
        panel_del.add(label1b);
        comboBox1b.setBounds(200, 50, 100, 20);
        panel_del.add(comboBox1b);
        button1b.setBounds(160, 180, 60, 30);
        panel_del.add(button1b);
        button2b.setBounds(240, 180, 60, 30);
        panel_del.add(button2b);
        tabbedPane.addTab("删除用户", panel_del);

        //新增用户
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password = "";
                username = textField.getText();
                p = passwordField.getPassword();
                for (int i = 0; i < p.length; i++) {
                    password += p[i];
                }
                role = comboBox.getSelectedItem().toString();

                application.sendData("Request AddUser");
                application.sendData(username);
                application.sendData(password);
                application.sendData(role);




//                if (username.equalsIgnoreCase("")) {
//                    JOptionPane.showMessageDialog(null, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
//                } else if (password.equalsIgnoreCase("")) {
//                    JOptionPane.showMessageDialog(null, "请输入密码", "提示", JOptionPane.WARNING_MESSAGE);
//                } else {
//
//                    int i = user.addUser(username, password, role);
//                    if (i == 1) {
//                        JOptionPane.showMessageDialog(null, "添加用户成功", "提示", JOptionPane.WARNING_MESSAGE);
//                        fresh();
//                    }
//                    if (i == 0) {
//                        JOptionPane.showMessageDialog(null, "用户名已存在，无法添加", "提示", JOptionPane.WARNING_MESSAGE);
//                        fresh();
//                    }
//                    if (i == -1) {
//                        JOptionPane.showMessageDialog(null, "添加用户失败", "提示", JOptionPane.WARNING_MESSAGE);
//                        fresh();
//                    }
//                }
            }
        });

        //修改用户
        button1a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                password = passwordFielda.getText();
                role=comboBox2a.getSelectedItem().toString();
                username = (String) comboBox1a.getSelectedItem();

                application.sendData("Request ChangeUser");
                application.sendData(username);
                application.sendData(password);
                application.sendData(role);



//                if(password.equalsIgnoreCase("")){
//                    JOptionPane.showMessageDialog(null, "请输入密码", "提示", JOptionPane.WARNING_MESSAGE);
//                }else if (user.changeUserInfo(username, password,role)) {
//                    JOptionPane.showMessageDialog(null, "修改用户成功", "提示", JOptionPane.WARNING_MESSAGE);
//                    fresh();
//                } else {
//                    JOptionPane.showMessageDialog(null, "修改用户失败", "提示", JOptionPane.WARNING_MESSAGE);
//                    fresh();
//                }
            }
        });

        //删除用户
        button1b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username = (String) comboBox1b.getSelectedItem();

                application.sendData("Request DeleteUser");
                application.sendData(username);


//                if (user.getName() == username) {
//                    JOptionPane.showMessageDialog(null, "无法删除当前用户", "提示", JOptionPane.WARNING_MESSAGE);
//                    fresh();
//                } else {
//                    if (user.delUser(username)) {
//                        JOptionPane.showMessageDialog(null, "删除用户成功", "提示", JOptionPane.WARNING_MESSAGE);
//                        fresh();
//                    } else {
//                        JOptionPane.showMessageDialog(null, "删除用户失败", "提示", JOptionPane.WARNING_MESSAGE);
//                        fresh();
//                    }
//                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        button2a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        button2b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        comboBox1a.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1a.getSelectedItem() != null) {
                    username = comboBox1a.getSelectedItem().toString();
                    try {
                        User user1 = DataProcessing.searchUser(username);
                        comboBox2a.setSelectedItem(user1.getRole());
                    } catch (SQLException ex) {
                        System.out.println("数据库异常");
                    }
                }
            }
        });
    }

    public void fresh(){
        textField.setText("");
        passwordField.setText("");
        passwordFielda.setText("");
        comboBox1a.removeAllItems();
        comboBox1b.removeAllItems();

        try {
            Enumeration<User> allUser1 = DataProcessing.getAllUser();
            Enumeration<User> allUser2 = DataProcessing.getAllUser();
            while (allUser1.hasMoreElements()) {
                comboBox1a.addItem(allUser1.nextElement().getName());
            }
            while (allUser2.hasMoreElements()) {
                comboBox1b.addItem(allUser2.nextElement().getName());
            }

        } catch (SQLException x) {
            System.out.println("数据库异常" + x.getMessage());
        }
    }
}
