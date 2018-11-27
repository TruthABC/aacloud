package hk.hku.cs.aacloud.entity.filesystem;

import java.io.File;
import java.util.ArrayList;

/**
 * Discarded
 */
public class FileNode {

    private File file;
    private ArrayList<File> fileList;

    public FileNode() {}

    public FileNode(File file, ArrayList<File> fileList) {
        this.file = file;
        this.fileList = fileList;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ArrayList<File> getFileList() {
        return fileList;
    }

    public void setFileList(ArrayList<File> fileList) {
        this.fileList = fileList;
    }

}
