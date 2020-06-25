package common;

import java.sql.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class DataProcessing {

    public static String Id;
    public static String creator;
    public static Timestamp timestamp;
    public static String description;
    public static String filename;

    public static String username;
    public static String password;
    public static String role;


    private static boolean connectToDB = false;

    public static Hashtable<String, User> users;
    public static Hashtable<String, Doc> docs = new Hashtable<String, Doc>();

    // TODO Auto-generated method stub
    public static Connection conn;
    public static PreparedStatement statement;
    public static ResultSet result_doc;
    public static ResultSet result_user;

    public static String driverName = "com.mysql.cj.jdbc.Driver";               // 加载数据库驱动类
    // 声明数据库的URL
    public static String url0 = "jdbc:mysql://localhost:3306/mysql?useUnicode=true&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    public static String user0 = "root";                                      // 数据库用户
    public static String password0 = "zhx572004821";


    static {
        //users = new Hashtable<String, User>();
        //users.put("jack", new Operator("jack","123","operator"));
        //users.put("rose", new Browser("rose","123","browser"));
        //users.put("kate", new Administrator("kate","123","administrator"));

        Init();

        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //docs = new Hashtable<String,Doc>();
        //docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));

    }

    public static void Init() {
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url0, user0, password0);   // 建立数据库连接
        } catch (ClassNotFoundException e) {
            System.out.println("数据驱动错误1");
        } catch (SQLException e) {
            System.out.println("数据库错误");
        }

    }

    public static Doc searchDoc(String ID) throws SQLException {
        String search = "select * from document.doc_info where Id=(?)";
        statement = conn.prepareStatement(search);
        statement.setObject(1, ID);
        result_doc = statement.executeQuery();
        while (result_doc.next()) {
            creator = result_doc.getString("creator");
            timestamp = Timestamp.valueOf(result_doc.getString("timestamp"));
            description = result_doc.getString("description");
            filename = result_doc.getString("filename");
            Doc temp = new Doc(ID, creator, timestamp, description, filename);
            return temp;
        }
        /*
        if (docs.containsKey(ID)) {
            Doc temp =docs.get(ID);
            return temp;
        }
         */
        return null;
    }

    public static Enumeration<Doc> getAllDocs() throws SQLException {

        docs = new Hashtable<String, Doc>();
        String search = "select * from document.doc_info";
        statement = conn.prepareStatement(search);
        result_doc = statement.executeQuery();
        while (result_doc.next()) {
            Id = result_doc.getString("Id");
            creator = result_doc.getString("creator");
            timestamp = Timestamp.valueOf(result_doc.getString("timestamp"));
            description = result_doc.getString("description");
            filename = result_doc.getString("filename");
            docs.put(Id, new Doc(Id, creator, timestamp, description, filename));
        }
        Enumeration<Doc> e = docs.elements();
        return e;
    }

    public static boolean insertDoc(String ID, String creator, Timestamp timestamp, String description, String filename) throws SQLException {
        Doc doc;
        if (docs.containsKey(ID))
            return false;
        else {
            //String insert="insert into document.doc_info(Id,creator,timestamp,description,filename) values(\""+ID+"\",\""+creator+"\",\""+timestamp+"\",\""+description+"\",\""+filename+"\")";
            String insert = "insert into document.doc_info(Id,creator,timestamp,description,filename) values((?),(?),(?),(?),(?))";
            statement = conn.prepareStatement(insert);
            statement.setObject(1, ID);
            statement.setObject(2, creator);
            statement.setObject(3, timestamp);
            statement.setObject(4, description);
            statement.setObject(5, filename);
            int i = statement.executeUpdate();
            if (i == 1) {
                doc = new Doc(ID, creator, timestamp, description, filename);
                docs.put(ID, doc);
                return true;
            } else {
                return false;
            }
        }
    }

    public static User searchUser(String name) throws SQLException {

        String search = "select * from document.user_info where username=(?)";
        statement = conn.prepareStatement(search);
        statement.setObject(1, name);
        result_user = statement.executeQuery();
        while (result_user.next()) {
            username = result_user.getString("username");
            password = result_user.getString("password");
            role = result_user.getString("role");
            if (role.equalsIgnoreCase("browser")) {
                Browser user = new Browser(username, password, role);
                return user;
            } else if (role.equalsIgnoreCase("Operator")) {
                Operator user = new Operator(username, password, role);
                return user;
            } else if (role.equalsIgnoreCase("Administrator")) {
                Administrator user = new Administrator(username, password, role);
                return user;
            }
        }
        return null;
        /*
        if (users.containsKey(name)) {
            return users.get(name);
        }
         */
    }

    public static User searchUser(String name, String password) throws SQLException {
        //String search="select * from document.user_info where username=\""+name+"\" and password=\""+password+"\"";
        String search = "select * from document.user_info where username=(?) and password=(?)";
        statement = conn.prepareStatement(search);
        statement.setObject(1, name);
        statement.setObject(2, password);
        result_user = statement.executeQuery();
        while (result_user.next()) {
            role = result_user.getString("role");
            if (role.equalsIgnoreCase("browser")) {
                Browser user = new Browser(name, password, role);
                return user;
            } else if (role.equalsIgnoreCase("Administrator")) {
                Administrator user = new Administrator(name, password, role);
                return user;
            } else if (role.equalsIgnoreCase("Operator")) {
                Operator user = new Operator(name, password, role);
                return user;
            }
        }
        return null;
        /*
        if (users.containsKey(name)) {
            User temp =users.get(name);
            if ((temp.getPassword()).equals(password))
                return temp;
        }
         */
    }

    public static Enumeration<User> getAllUser() throws SQLException {
        users = new Hashtable<String, User>();
        String search = "select * from document.user_info";
        statement = conn.prepareStatement(search);
        result_user = statement.executeQuery();
        while (result_user.next()) {
            username = result_user.getString("username");
            password = result_user.getString("password");
            role = result_user.getString("role");
            if (role.equalsIgnoreCase("Browser")) {
                users.put(username, new Browser(username, password, role));
            } else if (role.equalsIgnoreCase("Operator")) {
                users.put(username, new Operator(username, password, role));
            } else if (role.equalsIgnoreCase("Administrator")) {
                users.put(username, new Administrator(username, password, role));
            }
        }
        //users.put("jack", new Operator("jack","123","operator"));
        Enumeration<User> e = users.elements();
        return e;
    }

    public static boolean updateUser(String name, String password, String role) throws SQLException {
        User user = DataProcessing.searchUser(name);
        if (user == null) {
            return false;
        } else {
            String update = "update document.user_info set password=(?),role=(?) where username=(?)";
            statement = conn.prepareStatement(update);
            statement.setObject(1, password);
            statement.setObject(2, role);
            statement.setObject(3, name);
            int i = statement.executeUpdate();
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        }
        /*
        User user;
        if (users.containsKey(name)) {
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name,password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name,password, role);
            else
                user = new Browser(name,password, role);
            users.put(name, user);
            return true;
        }else
         */
    }

    public static boolean insertUser(String name, String password, String role) throws SQLException {
        User user = DataProcessing.searchUser(name);
        if (user != null) {
            return false;
        } else {
            String insert = "insert into document.user_info values((?),(?),(?))";
            statement = conn.prepareStatement(insert);
            statement.setObject(1, name);
            statement.setObject(2, password);
            statement.setObject(3, role);
            int i = statement.executeUpdate();  //i为数据库中更新的条数
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        }
        /*
        User user;
        if (users.containsKey(name))
            return false;
        else{
            if (role.equalsIgnoreCase("administrator"))
                user = new Administrator(name,password, role);
            else if (role.equalsIgnoreCase("operator"))
                user = new Operator(name,password, role);
            else
                user = new Browser(name,password, "browser");
            users.put(name, user);
            return true;
        }
         */
    }

    public static boolean deleteUser(String name) throws SQLException {
        User user = DataProcessing.searchUser(name);
        if (user == null) {
            return false;
        } else {
            String delete = "delete from document.user_info where username=(?)";
            statement = conn.prepareStatement(delete);
            statement.setObject(1, name);
            int i = statement.executeUpdate();
            if (i == 1) {
                return true;
            } else {
                return false;
            }
        }
        /*
        if (users.containsKey(name)){
            users.remove(name);
            return true;
        }else
            return false;
         */
    }

    public static void disconnectFromDB() {
        if (connectToDB) {
            // close Statement and Connection
            try {
                result_doc.close();
                result_user.close();
                statement.close();
                conn.close();
            } catch (SQLException e) {
                System.out.println("数据驱动错误");
            } finally {
                connectToDB = false;
            }
        }
    }
}
