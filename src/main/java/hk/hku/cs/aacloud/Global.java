package hk.hku.cs.aacloud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Global {

//    public static final String SERVICE_ROOT_URL = "http://123.207.6.234:8080/aacloud";
//    public static final String DATA_ROOT_URL = "http://123.207.6.234:8080/aacloud/data";
//    public static final String DISK_ROOT_URL = "http://123.207.6.234:8080/aacloud/data/disk";//"/?user?/files"

    public static void deleteAndMkdirs(File dir) {
        if (dir.exists()) {
            deleteDir(dir);
        }
        dir.mkdirs();
    }

    public static void deleteDir(File dir) {
        if (!dir.exists()) {
            return;
        }
        if (dir.isDirectory()) {
            for (File file: dir.listFiles()) {
                deleteDir(file);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }

    public static Scanner getFileScanner(File file) {
        if (!file.exists()) {
            return null;
        }

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        return scanner;
    }

    public static PrintStream getFilePrintStream(File file) {
        if (file.exists()) {
            Global.deleteDir(file);
        }

        boolean initIOSucc;
        PrintStream output = null;
        try {
            initIOSucc = file.createNewFile();
            output = new PrintStream(file);
        } catch (IOException e) {
            e.printStackTrace();
            initIOSucc =false;
        }

        if (!initIOSucc) {
            return null;
        }

        return output;
    }

}
