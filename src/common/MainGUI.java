package common;

import javax.swing.*;

public class MainGUI {

    public static Client application;

    public static void main(String[] args) {
        //MainGUI

        // if no command line args
        if (args.length == 0)
            application = new Client("127.0.0.1"); // connect to localhost
        else
            application = new Client(args[0]); // use args to connect

        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.runClient(); // run client application
    }
}
