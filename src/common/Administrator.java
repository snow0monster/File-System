package common;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;

public class Administrator extends User {

    Administrator(String name, String password, String role) {
        super(name, password, role);
    }

    public int addUser(String username, String password, String role) {
        try {
            User user = DataProcessing.searchUser(username);
            if (user != null) {
                System.out.println("用户名已存在，无法添加");
                return 0;
            } else {
                if(DataProcessing.insertUser(username,password,role)){
                    System.out.println("添加用户成功");
                    return 1;
                } else{
                    System.out.println("添加用户失败");
                    return -1;
                }
            }
        } catch (SQLException e) {
            System.out.println("数据库异常a");
            return -1;
        }
    }

    public boolean delUser(String username) {
        try {
            User user = DataProcessing.searchUser(username);
            if (user == null) {
                System.out.println("用户名不存在，无法删除");
                return false;
            } else if (user.getName().equalsIgnoreCase(this.getName())) {
                System.out.println("用户名为当前用户，无法删除");
                return false;
            } else {
                    if (DataProcessing.deleteUser(username)) {
                        return true;
                    } else {
                        System.out.println("删除用户失败.");
                        return false;
                    }
            }
        }catch(SQLException e){
            System.out.println("数据库异常");
            return false;
            }
    }

    public boolean changeUserInfo(String username, String password,String role) {
        try{
            User user=DataProcessing.searchUser(username);
            if(user==null){
                System.out.println("用户名不存在，无法修改");
                return false;
            }
            else{
                if(DataProcessing.updateUser(user.getName(),password,role)){
                    System.out.println("修改成功");
                    return true;
                } else{
                    System.out.println("修改失败");
                    return false;
                }
            }
        } catch (SQLException e){
            System.out.println("数据库异常b");
            return false;
        }

    }

    public void listUser() {
        ArrayList<User> arrayList = new ArrayList<User>();
        Enumeration<User> allUser = null;
        try {
            allUser = DataProcessing.getAllUser();
            while (allUser.hasMoreElements()) {
                arrayList.add(allUser.nextElement());
            }
        } catch (SQLException e) {
            System.out.println("数据库异常" + e.getMessage());
        }


        for (int i = 0; i < arrayList.size(); i++) {
            //如果把arrayList.get(i)改成allUser.nextElement()的话就会输出不同用户的信息了...
            System.out.print("用户名:" + arrayList.get(i).getName() + "      ");
            System.out.print("密码:" + arrayList.get(i).getPassword() + "       ");
            System.out.print("权限:" + arrayList.get(i).getRole());
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public void showMenu() {
        System.out.println("Administrator");
        System.out.println("****欢迎进入档案系统****");
        System.out.println("*******请选择选项*******");
        System.out.println("1.新增用户");
        System.out.println("2.删除用户");
        System.out.println("3.修改用户");
        System.out.println("4.用户列表");
        System.out.println("5.下载档案");
        System.out.println("6.档案列表");
        System.out.println("7.修改个人密码");
        System.out.println("8.返回主菜单");
        System.out.println("9.退出");
    }
}
