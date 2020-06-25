package frame;

import common.DataProcessing;
import common.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import static common.MainGUI.application;

public class LoginFrame extends JFrame {
    String username;
    String password;
    char[] p;

    JLabel label1 = new JLabel("用户名");
    JLabel label2 = new JLabel(" 密码    ");

    JTextField field1 = new JTextField(10);
    JPasswordField field2 = new JPasswordField(10);

    JButton button1 = new JButton("确定");
    JButton button2 = new JButton("取消");

    JDialog dialog1 = new JDialog(this, "提示");
    JDialog dialog2 = new JDialog(this, "提示");

    JLabel label1a = new JLabel("用户名不存在，请重新输入!");
    JLabel label2a = new JLabel("密码错误，请重新输入");

    JButton button1a = new JButton("确定");
    JButton button2a = new JButton("确定");

    public LoginFrame() {
        setSize(380, 200);
        this.setResizable(false);//不可改变容器大小
        this.setLocation(770, 400);
        this.setTitle("系统登录");
        init();
    }

    public void init() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();     //布局管理器
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;    //区域比组件大则不填充该区域
        gridBagConstraints.anchor = GridBagConstraints.CENTER;

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(label1, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(field1, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(label2, gridBagConstraints);

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(field2, gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(button1, gridBagConstraints);

        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagLayout.setConstraints(button2, gridBagConstraints);

        dialog1.setResizable(false);
        dialog1.setLayout(new FlowLayout());
        dialog1.add(label1a);
        dialog1.add(button1a);
        dialog1.setBounds(740, 435, 200, 120);
        dialog2.setResizable(false);
        dialog2.setLayout(new FlowLayout());
        dialog2.add(label2a);
        dialog2.add(button2a);
        dialog2.setBounds(740, 435, 150, 100);

        //将设置好的组件添加到框架中
        this.add(field1);
        this.add(field2);
        this.add(label1);
        this.add(label2);
        this.add(button1);
        this.add(button2);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public void login() {
        username = field1.getText();
        password = "";
        p = field2.getPassword();
        for (int i = 0; i < p.length; i++) {
            password += p[i];
        }

        application.sendData("Request Login");
        application.sendData(username);
        application.sendData(password);

        application.setUsername(username);
        application.setPassword(password);


//        try {
//            if (DataProcessing.searchUser(username)==null){
//                JOptionPane.showMessageDialog(null, "用户名不存在，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
//            } else if(DataProcessing.searchUser(username,password)==null){
//                JOptionPane.showMessageDialog(null, "密码错误，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
//            } else{
//                dispose();
//                User user = DataProcessing.searchUser(username, password);
//                MainFrame mainFrame = new MainFrame(user);
//                mainFrame.setVisible(true);
//            }
//        } catch (SQLException e){
//             System.out.println("数据库异常1");
//        }

    }
}
