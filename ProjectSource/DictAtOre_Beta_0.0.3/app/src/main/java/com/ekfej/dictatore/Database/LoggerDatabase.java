package com.ekfej.dictatore.Database;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Buda Viktor on 2015.12.25..
 */
public class LoggerDatabase {
    private final static Logger logger = Logger.getLogger(LoggerDatabase.logger.getName());
    private static FileHandler fh = null;

    public static void init(){
        try {
            fh=new FileHandler("Elementlog.log", false);
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
        Logger l = Logger.getLogger("Element");
        fh.setFormatter(new SimpleFormatter());
        l.addHandler(fh);
        l.setLevel(Level.CONFIG);
    }

    public static void main(String[] args) {
        LoggerDatabase.init();

        logger.log(Level.INFO, "message 1");
        logger.log(Level.SEVERE, "message 2");
        logger.log(Level.FINE, "message 3");
        Elementary.thing();
    }
}
