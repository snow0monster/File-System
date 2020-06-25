package frame;

import common.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static common.MainGUI.application;

public class SelfFrame extends JFrame {
    String username;
    String old_password;
    String new_password;
    String jud_password;
    String power;

    char[] p1;
    char[] p2;
    char[] p3;

    JLabel label1=new JLabel("用户名");
    JLabel label2=new JLabel("原密码");
    JLabel label3=new JLabel("新密码");
    JLabel label4=new JLabel("确认密码");
    JLabel label5=new JLabel("权限");

    JTextField field1;
    JPasswordField field2=new JPasswordField(15);
    JPasswordField field3=new JPasswordField(15);
    JPasswordField field4=new JPasswordField(15);
    JTextField field5;

    JButton button1=new JButton("确定");
    JButton button2=new JButton("取消");

    //构造方法
    public SelfFrame(User user){

        init(user);
        this.setSize(530, 400);
        this.setResizable(false);
        this.setLocation(700, 320);
        this.setTitle("个人信息管理");
    }

    public void init(User user){
        this.setLayout(null);

        username=user.getName();
        power=user.getRole();

        field1=new JTextField(username,15);
        field5=new JTextField(power,15);


        label1.setBounds(150,5,200,100);
        this.add(label1);
        label2.setBounds(150,55,200,100);
        this.add(label2);
        label3.setBounds(150,105,200,100);
        this.add(label3);
        label4.setBounds(150,155,200,100);
        this.add(label4);
        label5.setBounds(150,205,200,100);
        this.add(label5);

        field1.setEditable(false);//可读不可写
        field1.setBounds(220,45,100,20);
        this.add(field1);
        field2.setBounds(220,95,100,20);
        this.add(field2);
        field3.setBounds(220,147,100,20);
        this.add(field3);
        field4.setBounds(220,197,100,20);
        this.add(field4);
        field5.setEditable(false);
        field5.setBounds(220,247,100,20);
        this.add(field5);

        button1.setBounds(160,300,60,30);
        this.add(button1);
        button2.setBounds(250,300,60,30);
        this.add(button2);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                old_password="";
                new_password="";
                jud_password="";

                p1=field2.getPassword();
                p2=field3.getPassword();
                p3=field4.getPassword();

                for (int i = 0; i < p1.length; i++) {
                    old_password+=p1[i];
                }
                for (int i = 0; i < p2.length; i++) {
                    new_password+=p2[i];
                }for (int i = 0; i < p3.length; i++) {
                    jud_password+=p3[i];
                }

                application.sendData("Request ChangeSelfInfo");
                application.sendData(old_password);
                application.sendData(new_password);
                application.sendData(jud_password);

//                if(!old_password.equalsIgnoreCase(user.getPassword())){
//                    JOptionPane.showMessageDialog(null, "登录密码错误，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
//                    field2.setText("");
//                    field3.setText("");
//                    field4.setText("");
//                }
//                else if(!new_password.equalsIgnoreCase(jud_password)){
//                    JOptionPane.showMessageDialog(null, "两次新密码不一致，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
//                    field2.setText("");
//                    field3.setText("");
//                    field4.setText("");
//                }
//                else {
//                    try {
//                        if(user.changeSelfInfo(new_password)) {
//                            JOptionPane.showMessageDialog(null, "修改密码成功", "提示", JOptionPane.WARNING_MESSAGE);
//                            field2.setText("");
//                            field3.setText("");
//                            field4.setText("");
//                        }
//                    }catch (SQLException e2){
//                        JOptionPane.showMessageDialog(null, "数据库异常", "提示", JOptionPane.WARNING_MESSAGE);
//                    }
//
//                }
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public void refresh(){
        field2.setText("");
        field3.setText("");
        field4.setText("");
    }

}
