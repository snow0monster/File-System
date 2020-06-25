package common;


import frame.LoginFrame;
import frame.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client extends JFrame {
    private static JTextField enterField; // enters information from user
    private static JTextArea displayArea; // display information to user
    private static ObjectOutputStream output; // output stream to server
    private static ObjectInputStream input; // input stream from server
    private static String message = ""; // message from server
    private static String chatServer; // host server for this application
    private static Socket client; // socket to communicate with server

    private static LoginFrame loginframe;
    private static MainFrame mainFrame;

    private static String ID;
    private static String username;
    private static String password;
    private static String role;
    private static String filename;
    private static String description;

    // initialize chatServer and set up GUI
    public Client(String host) {
        super("Client");

        chatServer = host; // set server to which this client connects

        enterField = new JTextField(); // create enterField
        enterField.setEditable(false);
        enterField.addActionListener(
                new ActionListener() {
                    // send message to server
                    public void actionPerformed(ActionEvent event) {
                        sendData(event.getActionCommand());
                        enterField.setText("");
                    } // end method actionPerformed
                } // end anonymous inner class
        ); // end call to addActionListener

        add(enterField, BorderLayout.NORTH);
        displayArea = new JTextArea(); // create displayArea
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(300, 150); // set size of window
        setVisible(true); // show window
    } // end Client constructor

    // connect to server and process messages from server
    public static void runClient() {
        try // connect to server, get streams, process connection
        {
            connectToServer(); // create a Socket to make connection
            getStreams(); // get the input and output streams
            loginframe = new LoginFrame();
            loginframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            loginframe.setVisible(true);

            processConnection(); // process connection
        } // end try
        catch (EOFException eofException) {
            displayMessage("\nClient terminated connection");
        } // end catch
        catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch
        finally {
            closeConnection(); // close connection
        } // end finally
    } // end method runClient

    // connect to server
    public static void connectToServer() throws IOException {
        displayMessage("Attempting connection\n");

        // create Socket to make connection to server
        client = new Socket(InetAddress.getByName(chatServer), 8888);

        // display connection information
        displayMessage("Connected to: " +
                client.getInetAddress().getHostName());
    } // end method connectToServer

    // get streams to send and receive data
    public static void getStreams() throws IOException {
        // set up output stream for objects
        output = new ObjectOutputStream(client.getOutputStream());
        output.flush(); // flush output buffer to send header information

        // set up input stream for objects
        input = new ObjectInputStream(client.getInputStream());

        displayMessage("\nGot I/O streams\n");
    } // end method getStreams

    // process connection with server
    public static void processConnection() throws IOException {
        // enable enterField so client user can send messages
        setTextFieldEditable(true);

        do // process messages sent from server
        {
            try // read message and display it
            {
                message = (String) input.readObject(); // read new message
                displayMessage("\nReceived from Server:"); // display message
                displayMessage("\n" + message); // display message

                //登录界面
                if (message.equalsIgnoreCase("LOGIN TRUE")) {
                    role = (String) input.readObject();
                    if (role.equalsIgnoreCase("Browser")) {
                        Browser user = new Browser(username, password, role);
                        mainFrame = new MainFrame(user);
                        mainFrame.setVisible(true);
                    } else if (role.equalsIgnoreCase("Operator")) {
                        Operator user = new Operator(username, password, role);
                        mainFrame = new MainFrame(user);
                        mainFrame.setVisible(true);
                    } else if (role.equalsIgnoreCase("Administrator")) {
                        Administrator user = new Administrator(username, password, role);
                        mainFrame = new MainFrame(user);
                        mainFrame.setVisible(true);
                    }
                    loginframe.dispose();
                } else if (message.equalsIgnoreCase("LOGIN FALSE1")) {
                    JOptionPane.showMessageDialog(null, "用户名不存在，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                } else if (message.equalsIgnoreCase("LOGIN FALSE2")) {
                    JOptionPane.showMessageDialog(null, "密码错误，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                }

                //个人界面
                if (message.equalsIgnoreCase("ChangeSelfInfo TRUE")) {
                    JOptionPane.showMessageDialog(null, "修改密码成功", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.selfFrame.refresh();
                } else if (message.equalsIgnoreCase("ChangeSelfInfo FALSE1")) {
                    JOptionPane.showMessageDialog(null, "登录密码错误，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.selfFrame.refresh();
                } else if (message.equalsIgnoreCase("ChangeSelfInfo FALSE2")) {
                    JOptionPane.showMessageDialog(null, "两次新密码不一致，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.selfFrame.refresh();
                }

                //用户管理界面
                if (message.equalsIgnoreCase("AddUser TRUE")) {
                    JOptionPane.showMessageDialog(null, "添加用户成功", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if (message.equalsIgnoreCase("AddUser FALSE1")) {
                    JOptionPane.showMessageDialog(null, "请输入用户名", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if (message.equalsIgnoreCase("AddUser FALSE2")) {
                    JOptionPane.showMessageDialog(null, "请输入密码", "提示", JOptionPane.WARNING_MESSAGE);
                }
                else if (message.equalsIgnoreCase("AddUser FALSE3")) {
                    JOptionPane.showMessageDialog(null, "用户名已存在，无法添加", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if (message.equalsIgnoreCase("AddUser FALSE4")) {
                    JOptionPane.showMessageDialog(null, "添加用户失败", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if(message.equalsIgnoreCase("ChangeUser TRUE")) {
                    JOptionPane.showMessageDialog(null, "修改用户成功", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if(message.equalsIgnoreCase("ChangeUser FALSE1")){
                    JOptionPane.showMessageDialog(null, "请输入密码", "提示", JOptionPane.WARNING_MESSAGE);
                }
                else if(message.equalsIgnoreCase("ChangeUser FALSE2")){
                    JOptionPane.showMessageDialog(null, "修改用户失败", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if(message.equalsIgnoreCase("DeleteUser TRUE")){
                    JOptionPane.showMessageDialog(null, "删除用户成功", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if(message.equalsIgnoreCase("DeleteUser FALSE1")){
                    JOptionPane.showMessageDialog(null, "无法删除当前用户", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                else if(message.equalsIgnoreCase("DeleteUser FALSE2")) {
                    JOptionPane.showMessageDialog(null, "删除用户失败", "提示", JOptionPane.WARNING_MESSAGE);
                    mainFrame.userFrame.fresh();
                }
                //文件界面
                if (message.equalsIgnoreCase("Downloading file")) {

                    String filename = (String) input.readObject();
                    displayMessage("\nReceived from Server:"); // display message
                    displayMessage("\n" + filename); // display message
                    File aim = new File("C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\downloadfile\\" + filename);
                    FileOutputStream out = new FileOutputStream(aim);

                    try {
                        byte[] bt = new byte[1024];
                        int len = 1024;
                        while ((input.read(bt, 0, len)) != -1) {
                            out.write(bt);
                            out.flush();
                            System.out.println("1");
                        }
                    } catch (IOException e) {
                        System.out.println("IO流异常");
                    }

                    if (aim.exists()) {
                        JOptionPane.showMessageDialog(null, "下载文件成功", "提示", JOptionPane.WARNING_MESSAGE);
                        displayMessage("\nDownload successfully");
                    } else {
                        JOptionPane.showMessageDialog(null, "下载文件失败", "提示", JOptionPane.WARNING_MESSAGE);
                        displayMessage("\nDownload unsuccessfully");
                    }
                } else if (message.equalsIgnoreCase("Download file failed")) {
                    JOptionPane.showMessageDialog(null, "下载文件失败", "提示", JOptionPane.WARNING_MESSAGE);
                } else if (message.equalsIgnoreCase("Uploading file")) {
                    String filename = (String) input.readObject();

                    File src = new File("C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\uploadfile\\" + filename);

                    InputStream is = new FileInputStream(src);

                    byte[] bt = new byte[1024];
                    int len = 1024;
                    while ((is.read(bt, 0, len)) > 0) {
                        output.write(bt);
                    }
                    output.flush();

                    JOptionPane.showMessageDialog(null, "上传文件成功", "提示", JOptionPane.WARNING_MESSAGE);
                } else if (message.equalsIgnoreCase("Upload file failed")) {
                    JOptionPane.showMessageDialog(null, "上传文件失败", "提示", JOptionPane.WARNING_MESSAGE);
                }

            } // end try
            catch (ClassNotFoundException classNotFoundException) {
                displayMessage("\nUnknown object type received");
            } // end catch

        } while (!message.equals("TERMINATE"));
    } // end method processConnection

//    public static Socket getClient(){
//        return client;
//    }

    // close streams and socket
    public static void closeConnection() {
        displayMessage("\nClosing connection");
        setTextFieldEditable(false); // disable enterField

        try {
            output.close(); // close output stream
            input.close(); // close input stream
            client.close(); // close socket
        } // end try
        catch (IOException ioException) {
            ioException.printStackTrace();
        } // end catch
    } // end method closeConnection

    // send message to server
    public static void sendData(String message) {
        try // send object to server
        {
            output.writeObject(message);
            output.flush(); // flush data to output
            displayMessage("\nSend to Server:");
            displayMessage("\n" + message);
        } // end try
        catch (IOException ioException) {
            displayArea.append("\nError writing object");
        } // end catch
    } // end method sendData

    public static String received() {
        try {
            message = (String) input.readObject();
            displayMessage("\n" + message);
            return message;
        } catch (IOException e) {
            System.out.println("输入流异常");
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("访问异常");
            return null;
        }
    }

    // manipulates displayArea in the event-dispatch thread
    public static void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // updates displayArea
                    {
                        displayArea.append(messageToDisplay);
                    } // end method run
                }  // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method displayMessage

    // manipulates enterField in the event-dispatch thread
    public static void setTextFieldEditable(final boolean editable) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() // sets enterField's editability
                    {
                        enterField.setEditable(editable);
                    } // end method run
                } // end anonymous inner class
        ); // end call to SwingUtilities.invokeLater
    } // end method setTextFieldEditable

    public static void setUsername(String name) {
        username = name;
    }

    public static void setPassword(String password1) {
        password = password1;
    }


}// end class Client
