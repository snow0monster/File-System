package frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import common.*;

public class MainFrame extends JFrame {
    JMenuBar jMenuBar=new JMenuBar();
    JMenu jMenu1=new JMenu("用户管理");
    JMenu jMenu2=new JMenu("档案管理");
    JMenu jMenu3=new JMenu("个人信息管理");
    JMenuItem jMenuItem1=new JMenuItem("新增用户");
    JMenuItem jMenuItem2=new JMenuItem("修改用户");
    JMenuItem jMenuItem3=new JMenuItem("删除用户");
    JMenuItem jMenuItem4=new JMenuItem("文件下载");
    JMenuItem jMenuItem5=new JMenuItem("文件上传");
    JMenuItem jMenuItem6=new JMenuItem("信息修改");
    JMenuItem jMenuItem7=new JMenuItem("切换用户");

    public static FileFrame fileFrame;
    public static SelfFrame selfFrame;
    public static UserFrame userFrame;

    public MainFrame(User user){
        init(user);

        setSize(1200,800);
        this.setResizable(false);
        this.setLocation(350,120);

        if(user.getRole().equalsIgnoreCase("Browser")) {
            this.setTitle("档案浏览员");
            jMenu1.setEnabled(false);
            jMenuItem5.setEnabled(false);
        }
        if(user.getRole().equalsIgnoreCase("Operator")) {
            this.setTitle("档案录入员");
            jMenu1.setEnabled(false);
        }
        if(user.getRole().equalsIgnoreCase("Administrator")) {
            this.setTitle("系统管理员");
            jMenuItem5.setEnabled(false);
        }
    }

    public void init(User user){

        jMenu1.add(jMenuItem1);
        jMenu1.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu2.add(jMenuItem4);
        jMenu2.add(jMenuItem5);
        jMenu3.add(jMenuItem6);
        jMenu3.add(jMenuItem7);

        jMenuBar.add(jMenu1);
        jMenuBar.add(jMenu2);
        jMenuBar.add(jMenu3);

        this.setJMenuBar(jMenuBar);

        jMenuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userFrame = new UserFrame(user);
                userFrame.setVisible(true);
                userFrame.tabbedPane.setSelectedIndex(0);
            }
        });

        jMenuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userFrame = new UserFrame(user);
                userFrame.setVisible(true);
                userFrame.tabbedPane.setSelectedIndex(1);
            }
        });

        jMenuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userFrame = new UserFrame(user);
                userFrame.setVisible(true);
                userFrame.tabbedPane.setSelectedIndex(2);
            }
        });

        jMenuItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileFrame = new FileFrame(user);
                fileFrame.setVisible(true);
                fileFrame.jTabbedPane.setSelectedIndex(0);
            }
        });

        jMenuItem5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fileFrame = new FileFrame(user);
                fileFrame.setVisible(true);
                fileFrame.jTabbedPane.setSelectedIndex(1);
            }
        });

        jMenuItem6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selfFrame = new SelfFrame(user);
                selfFrame.setVisible(true);
            }
        });

        jMenuItem7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginFrame f = new LoginFrame();
                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                f.setVisible(true);
            }
        });
    }

}
