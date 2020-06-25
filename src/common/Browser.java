package common;

public class Browser extends User{
    Browser(String name, String password, String role) {
        super(name, password, role);
    }

    @Override
    public void showMenu(){
        System.out.println("Browser");
        System.out.println("****欢迎进入档案系统****");
        System.out.println("*******请选择选项*******");
        System.out.println("1.下载档案");
        System.out.println("2.档案列表");
        System.out.println("3.修改密码");
        System.out.println("4.返回主菜单");
        System.out.println("5.退出");
    }
            }
