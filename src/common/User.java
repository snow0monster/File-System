package common;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Logger;


public abstract class User {
    private String name;
    private String password;
    private String role;

    String uploadpath = "C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\uploadfile\\";
    String downloadpath = "C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\downloadfile\\";

    User(String name, String password, String role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public boolean changeSelfInfo(String password) throws SQLException {
        //写用户信息到存储
        if (DataProcessing.updateUser(name, password, role)) {
            this.password = password;
            System.out.println("修改成功");
            return true;
        } else
            return false;
    }

    public boolean downloadFile(String ID) throws SQLException {
        Doc doc = DataProcessing.searchDoc(ID);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (doc == null) {
            System.out.println("档案不存在！");
            return false;
        }

        try {
            File src = new File(uploadpath + doc.getFilename());
            File aim = new File(downloadpath + doc.getFilename());

            if (!src.exists()) {
                System.out.println("未找到该文件!");
                return false;
            }
            if (!src.isFile()) {
                System.out.println("目标为目录，无法下载!");
                return false;
            }

            InputStream is = new FileInputStream(src);
            OutputStream os = new FileOutputStream(aim);

            byte[] bt = new byte[1024];
            int len = 0;
            while ((len = is.read(bt)) != -1) {
                os.write(bt, 0, len);
            }
            os.flush();
            is.close();
            os.close();


            if (aim.exists()) {

                File file=new File("C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\下载序列化\\"+doc.getFilename());
                ObjectOutputStream out=new  ObjectOutputStream(new FileOutputStream(file));
                Doc newdoc=new Doc(doc.getID(),name,timestamp,doc.getDescription(),doc.getFilename());

                out.writeObject(newdoc);
                out.close();

                System.out.println(newdoc);
                System.out.println("下载文件成功！");
                return true;
            } else {
                System.out.println("下载文件失败！");
                return false;
            }

        } catch (FileNotFoundException e) {
            System.out.println("下载失败！1");
            return false;
        } catch (IOException e) {
            System.out.println("数据库错误，下载失败!");
            return false;
        }
    }


    public void showFileList() {
        ArrayList<Doc> arrayList = new ArrayList<>();
        try {
            Enumeration<Doc> allDocs = DataProcessing.getAllDocs();
            while (allDocs.hasMoreElements()) {
                arrayList.add(allDocs.nextElement());
            }
        } catch (SQLException e) {
            System.out.println("数据库异常" + e.getMessage());
        }
        for (int i = 0; i < arrayList.size(); i++) {
            System.out.println("档案号:" + arrayList.get(i).getID());
            System.out.println("创建者:" + arrayList.get(i).getCreator());
            System.out.println(" 时间 :" + arrayList.get(i).getTimestamp());
            System.out.println("文件名:" + arrayList.get(i).getFilename());
            System.out.println(" 描述 :" + arrayList.get(i).getDescription());
            System.out.println("----------------------------");
        }
        System.out.println();
    }

    public abstract void showMenu();

    public void exitSystem() {
        System.out.println("系统退出, 谢谢使用 ! ");
        System.exit(0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
