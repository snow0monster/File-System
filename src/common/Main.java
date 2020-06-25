package common;

import java.sql.SQLException;
import java.util.Scanner;

import static common.DataProcessing.*;

//一开始编写的是命令行格式，之后改写为GUI的形式
public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String menu = "****欢迎进入档案系统****\n" + "*******请选择选项*******\n" + "1.登录\n" + "2.退出\n";
        String username;
        String password;

        while (true) {

            try {
                System.out.println(menu);
                String input = in.nextLine();
                boolean judge = true;

                if (!(input).matches("1||2")) {
                    System.out.println("输入错误，请重新输入！");
                    System.out.println();
                }

                //普通用户登录
                else if (input.matches("1")) {
                    System.out.println("请输入用户名");
                    username = in.nextLine();
                    System.out.println("请输入密码");
                    password = in.nextLine();

                    if (users.containsKey(username)) {
                        if (users.get(username).getPassword().equals(password)) {
                            if (searchUser(username, password).getRole().equalsIgnoreCase("Browser")) {
                                Browser browser = (Browser) searchUser(username, password);

                                while (judge) {
                                    browser.showMenu();
                                    input = in.nextLine();

                                    if (!(input).matches("1||2||3||4||5")) {
                                        browser.showMenu();
                                    }//下载档案
                                    else if (input.matches("1")) {
                                        System.out.println("请输入档案号:");
                                        input = in.nextLine();
                                        /*
                                        try {
                                            browser.downloadFile(input);
                                        } catch (SQLException e) {
                                            System.out.println("");
                                        }

                                         */
                                    }//档案列表
                                    else if (input.matches("2")) {
                                        browser.showFileList();
                                    }//修改密码
                                    else if (input.matches("3")) {
                                        System.out.println("请输入修改后的密码:");
                                        input = in.nextLine();
                                        try {
                                            browser.changeSelfInfo(input);
                                        } catch (SQLException e) {
                                            System.out.println("数据库异常");
                                        }
                                    }//返回主菜单
                                    else if (input.matches("4")) {
                                        judge = false;
                                    }//退出
                                    else if (input.matches("5")) {
                                        in.close();
                                        browser.exitSystem();
                                    } else {
                                        System.out.println("输入错误，请重新输入！");
                                        System.out.println();
                                    }
                                }
                            }

                            //档案管理员登录
                            else if (searchUser(username, password).getRole().equalsIgnoreCase("Operator")) {
                                Operator operator = (Operator) searchUser(username, password);
                                while (judge) {
                                    operator.showMenu();
                                    input = in.nextLine();

                                    if (!(input).matches("1||2||3||4||5||6")) {
                                        operator.showMenu();
                                    }//上传档案
                                    else if (input.matches("1")) {
                                        System.out.println("请输入源文件名:");
                                        String filename = in.nextLine();
                                        System.out.println("请输入档案号:");
                                        String ID = in.nextLine();
                                        System.out.println("请输入档案描述:");
                                        String description = in.nextLine();
                                        /*
                                        try {
                                            operator.uploadFile(ID, filename, description,src);
                                        } catch (SQLException e) {
                                            System.out.println("数据库异常");
                                        }
                                         */
                                    }  //下载档案
                                    else if (input.matches("2")) {
                                        System.out.println("请输入要下载的档案号");
                                        input = in.nextLine();
                                        /*
                                        try {
                                            operator.downloadFile(input);
                                        } catch (SQLException e) {
                                            System.out.println("数据库异常");//档案列表
                                        }

                                         */
                                    } else if (input.matches("3")) {
                                        operator.showFileList();
                                    }//修改密码
                                    else if (input.matches("4")) {
                                        System.out.println("请输入修改后的密码");
                                        input = in.nextLine();
                                        try {
                                            operator.changeSelfInfo(input);
                                        } catch (SQLException e) {
                                            System.out.println("数据库异常");
                                        }
                                    }//返回主菜单
                                    else if (input.matches("5")) {
                                        judge = false;
                                    }//退出
                                    else if (input.matches("6")) {
                                        in.close();
                                        operator.exitSystem();
                                    } else {
                                        System.out.println("输入错误，请重新输入！");
                                        System.out.println();
                                    }
                                }
                            }

                            //系统管理员登录
                            else if (searchUser(username, password).getRole().equalsIgnoreCase("Administrator")) {
                                Administrator administrator = (Administrator) searchUser(username, password);
                                while (judge) {
                                    administrator.showMenu();
                                    input = in.nextLine();

                                    if (!(input).matches("1||2||3||4||5||6||7||8||9")) {
                                        administrator.showMenu();
                                    }//新增用户
                                    else if (input.matches("1")) {
                                        System.out.println("请输入要新增的用户信息:");
                                        System.out.println("请输入用户的用户名");
                                        String input1 = in.nextLine();
                                        System.out.println("请输入用户的密码");
                                        String input2 = in.nextLine();
                                        System.out.println("请输入用户的权限");
                                        String input3 = in.nextLine();
                                        administrator.addUser(input1, input2, input3);
                                    }//删除用户
                                    else if (input.matches("2")) {
                                        System.out.println("请输入要删除的用户名");
                                        input = in.nextLine();
                                        administrator.delUser(input);
                                    }//修改用户密码
                                    else if (input.matches("3")) {
                                        System.out.println("请输入要修改的用户名");
                                        String input1 = in.nextLine();
                                        System.out.println("请输入修改后的用户密码");
                                        String input2 = in.nextLine();
                                        //administrator.changeUserInfo(input1, input2);
                                    }//用户列表
                                    else if (input.matches("4")) {
                                        administrator.listUser();
                                    }//下载档案
                                    else if (input.matches("5")) {
                                        System.out.println("请输入档案号:");
                                        input = in.nextLine();
                                        /*
                                        try {
                                            administrator.downloadFile(input);
                                        } catch (SQLException e) {
                                            System.out.println("数据库下载出错");
                                        }

                                         */
                                    }//档案列表
                                    else if (input.matches("6")) {
                                        administrator.showFileList();
                                    }//修改个人密码
                                    else if (input.matches("7")) {
                                        System.out.println("请输入修改后的密码");
                                        input = in.nextLine();
                                        try {
                                            administrator.changeSelfInfo(input);
                                        } catch (SQLException e) {
                                            System.out.println("数据库修改密码出错");
                                        }
                                    }//返回主菜单
                                    else if (input.matches("8")) {
                                        judge = false;
                                    }//退出
                                    else if (input.matches("9")) {
                                        in.close();
                                        administrator.exitSystem();
                                    } else {
                                        System.out.println("输入错误，请重新输入！");
                                        System.out.println();
                                    }
                                }
                            }
                        }
                        //密码出错的情况
                        else {
                            System.out.println("密码错误，请重新输入!");
                        }
                    }
                    //用户名不存在的情况
                    else {
                        System.out.println("用户名不存在，请重新输入!");
                    }
                } else if (input.matches("2")) {
                    System.out.println("系统退出，谢谢使用！");
                    in.close();
                    System.exit(0);
                }
            } catch (SQLException e) {
                System.out.println("数据库查找用户出错");
            }
        }
    }
}
