package common;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Server extends ServerSocket {
    private static ObjectOutputStream output; // output stream to client
    private static ObjectInputStream input; // input stream from client
    private static ServerSocket server; // server socket
    private static String message;
    private static Socket connection;
    private static int counter = 1; // counter of number of connections
    private static String ID;
    private static String username;
    private static String password;
    private static String filename;
    private static String description;
    private static String role;

    private static User user;


    public static void main(String[] args) throws IOException {

        System.out.println("Waiting for connection\n");
        new Server();
    }

    public Server() throws IOException {
        super(8888, 100);
        try {
            while (true) {
                connection = accept();
                System.out.println("Connection " + " received from: " + connection.getInetAddress().getHostName());
                new ServerThread();
                counter++;
            }
        } catch (IOException e) {
            System.out.println("IO流异常");
        } finally {
            closeConnection();
        }
    }

//    private  void runServer() {
//        try {
//            server = new ServerSocket(8888, 100);
//            try {
//                while(true) {
//                    waitForConnection();
//                    getStreams();
//                    new ServerThread(connection,"Thread");
//                    counter++;
//                }
//            } catch (EOFException e1) {
//                System.out.println("\nServer terminated connection");
//            } catch (IOException e) {
//                System.out.println("输入输出流异常3");
//            } finally {
//                closeConnection();
//                counter++;
//            }
//        } catch (IOException e) {
//            System.out.println("输入输出流异常2");
//        }
//    }
//
//    private static void waitForConnection() throws IOException {
//        connection = server.accept();
//        System.out.println("Connection" + counter + "received from " +
//                connection.getInetAddress().getHostName());
//    }

    private static void getStreams() throws IOException {
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();

        input = new ObjectInputStream(connection.getInputStream());

        System.out.println("\nGot I/O streams\n");
    }


    class ServerThread extends Thread {
        public ServerThread() throws IOException {
            getStreams();
            start();
        }

        public void run() {
            message = "Connection successful";
            sendData(message);

            do // process messages sent from client
            {
                try // read message and display it
                {
                    message = (String) input.readObject(); // read new message
                    System.out.println("Received from Client:");
                    System.out.println(message); // display message

                    if (message.equalsIgnoreCase("Request Login")) {
                        username = (String) input.readObject();
                        System.out.println(username); // display message

                        password = (String) input.readObject();
                        System.out.println(password);
                        System.out.println();

                        try {
                            if (DataProcessing.searchUser(username) == null) {
                                sendData("LOGIN FALSE1");
                            } else if (DataProcessing.searchUser(username, password) == null) {
                                sendData("LOGIN FALSE2");
                            } else {
                                user = DataProcessing.searchUser(username, password);
                                sendData("LOGIN TRUE");
                                sendData(user.getRole());
                            }
                        } catch (SQLException e) {
                            System.out.println("数据库异常1");
                        }
                    } else if (message.equalsIgnoreCase("Request ChangeSelfInfo")) {
                        String old_password = (String) input.readObject();
                        String new_password = (String) input.readObject();
                        String jud_password = (String) input.readObject();

                        System.out.println(old_password);
                        System.out.println(new_password);
                        System.out.println(jud_password);
                        System.out.println();

                        if (!old_password.equalsIgnoreCase(user.getPassword())) {
                            sendData("ChangeSelfInfo FALSE1");
                        } else if (!new_password.equalsIgnoreCase(jud_password)) {
                            sendData("ChangeSelfInfo FALSE2");
                        } else {
                            try {
                                if (user.changeSelfInfo(new_password)) {
                                    sendData("ChangeSelfInfo TRUE");
                                }
                            } catch (SQLException e2) {
                                JOptionPane.showMessageDialog(null, "数据库异常", "提示", JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    } else if (message.equalsIgnoreCase("Request AddUser")) {
                        username = (String) input.readObject();
                        password = (String) input.readObject();
                        role = (String) input.readObject();

                        if (username.equalsIgnoreCase("")) {
                            sendData("AddUser FALSE1");
                        } else if (password.equalsIgnoreCase("")) {
                            sendData("AddUser FALSE2");
                        } else {
                            Administrator user1 = (Administrator) user;
                            int i = 0;
                            System.out.println(user);
                            i = user1.addUser(username, password, role);

                            if (i == 1) {
                                sendData("AddUser TRUE");
                            }
                            if (i == 0) {
                                sendData("AddUser FALSE3");
                            }
                            if (i == -1) {
                                sendData("AddUser FALSE4");
                            }
                        }

                    } else if (message.equalsIgnoreCase("Request ChangeUser")) {
                        username = (String) input.readObject();
                        password = (String) input.readObject();
                        role = (String) input.readObject();
                        Administrator user1 = (Administrator) user;
                        if (password.equalsIgnoreCase("")) {
                            sendData("ChangeUser FALSE1");
                        } else if (user1.changeUserInfo(username, password, role)) {
                            sendData("ChangeUser TRUE");
                        } else {
                            sendData("ChangeUser FALSE2");
                        }
                    } else if (message.equalsIgnoreCase("Request DeleteUser")) {
                        username = (String) input.readObject();
                        Administrator user1 = (Administrator) user;

                        if (user.getName() == username) {
                            sendData("DeleteUser FALSE1");
                        } else {
                            if (user1.delUser(username)) {
                                sendData("DeleteUser TRUE");
                            } else {
                                sendData("DeleteUser FALSE2");
                            }
                        }


                    } else if (message.equalsIgnoreCase("Request Downloading")) {
                        ID = (String) input.readObject();

                        sendData("Downloading file");

                        try {

                            sendData(DataProcessing.searchDoc(ID).getFilename());

                            File src = new File("C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\uploadfile\\" + DataProcessing.searchDoc(ID).getFilename());
                            FileInputStream is = new FileInputStream(src);

                            byte[] bt = new byte[1024];
                            int len = 1024;
                            while ((is.read(bt, 0, len)) > 0) {
                                output.write(bt);
                                output.flush();
                            }

                        } catch (SQLException e) {
                            System.out.println("数据库异常");
                        } catch (IOException e) {
                            System.out.println("IO流异常");
                        }
                    } else if (message.equalsIgnoreCase("Request Uploading")) {
                        username = (String) input.readObject();
                        ID = (String) input.readObject();
                        filename = (String) input.readObject();
                        description = (String) input.readObject();
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

                        sendData("Uploading file");

                        sendData(username);
                        sendData(ID);
                        sendData(filename);
                        sendData(description);

                        File aim = new File("C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\uploadfile\\" + filename);

                        OutputStream out = new FileOutputStream(aim);

                        byte[] bt = new byte[1024];
                        int len = 1024;
                        while ((input.read(bt, 0, len)) != -1) {
                            out.write(bt);
                            out.flush();
                        }


                        if (aim.exists()) {
                            try {
                                DataProcessing.insertDoc(ID, username, timestamp, description, filename);
                            } catch (SQLException e) {
                                System.out.println("数据库异常");
                            }
                            JOptionPane.showMessageDialog(null, "上传文件成功", "提示", JOptionPane.WARNING_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "上传文件失败", "提示", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                } // end try
                catch (ClassNotFoundException classNotFoundException) {
                    System.out.println("\nUnknown object type received");
                } // end catch
                catch (IOException e) {
                    System.out.println("IO流异常");
                }
            } while (!message.equals("TERMINATE"));
        }
    }

    private static void closeConnection() {
        System.out.println("\nTerminating connection\n");
        try {
            output.close();
            input.close();
            server.close();
        } catch (IOException e1) {
            System.out.println("输入输出流异常1");
        }
    }

    private static void sendData(String message) {
        try {
            output.writeObject(message);
            output.flush();
            System.out.println();
            System.out.println("Send to Client:");
            System.out.println(message);
        } catch (IOException e) {
            System.out.println("输入输出流异常");
        }
    }

}
