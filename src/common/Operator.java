package common;

import java.io.*;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Operator extends User {

    Operator(String name, String password, String role) {
        super(name, password, role);
    }

    public boolean uploadFile(String ID, String filename, String description, File src) throws SQLException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try {

            if (DataProcessing.searchDoc(ID) != null) {
                System.out.println("档案号已存在!");
                return false;
            }

            File aim = new File(uploadpath + filename);

            if (!src.exists()) {
                System.out.println("未找到该文件！");
                return false;
            }
            if (!src.isFile()) {
                System.out.println("目标为目录，无法上传!");
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
            os.close();
            is.close();

            if (aim.exists()) {
                if (DataProcessing.insertDoc(ID, this.getName(), timestamp, description, filename)) {

                    File file = new File("C:\\Users\\a's\\Desktop\\实验文件\\Java多线程实验\\上传序列化\\" + filename);
                    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                    Doc doc = new Doc(ID, this.getName(), timestamp, description, filename);

                    out.writeObject(doc);
                    out.close();

                    System.out.println("上传成功!");
                    return true;
                } else {
                    aim.delete();
                    return false;
                }
            } else {
                System.out.println("上传失败1!");
                return false;
            }
        } catch (IOException e) {
            System.out.println("上传失败2!");
        }

        return true;
    }

    @Override
    public void showMenu() {
        System.out.println("Operator");
        System.out.println("****欢迎进入档案系统****");
        System.out.println("*******请选择选项*******");
        System.out.println("1.上传档案");
        System.out.println("2.下载档案");
        System.out.println("3.档案列表");
        System.out.println("4.修改密码");
        System.out.println("5.返回主菜单");
        System.out.println("6.退出");
    }
}
