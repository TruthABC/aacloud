package hk.hku.cs.aacloud.entity.response;

public class FileInfo {

    private String name;
    private String relativePath;
    private boolean isDir;

    public FileInfo() {}

    public FileInfo(String name, String relativePath, boolean isDir) {
        this.name = name;
        this.relativePath = relativePath;
        this.isDir = isDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }
}
