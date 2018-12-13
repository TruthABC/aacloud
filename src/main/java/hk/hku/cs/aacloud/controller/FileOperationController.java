package hk.hku.cs.aacloud.controller;

import hk.hku.cs.aacloud.Global;
import hk.hku.cs.aacloud.entity.response.CommonResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@RestController
public class FileOperationController {

    //1. create folder
    @RequestMapping("/create_folder")
    @CrossOrigin
    public String createFolder(@RequestParam(value="relativePath") String relativePath,
                               @RequestParam(value="id") String id,
                               HttpServletRequest request) {
        //Construct Response
        JSONObject jsonRet;

        //id cannot be empty
        if (id.length() == 0) {
            jsonRet = JSONObject.fromObject(new CommonResponse(4, "Id Cannot Be Empty"));
            return jsonRet.toString();
        }

        //Construct absolute root path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF\\classes\\static\\data\\disk\\";
        absoluteRootPath += id + "\\files\\";

        //Make sure the root directory exists
        File rootDir = new File(absoluteRootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        //Construct absolute path from RelativePath param
        String absolutePath = absoluteRootPath + relativePath;

        //Make sure the absolute file exists
        File absoluteFile = new File(absolutePath);
        if (!absoluteFile.exists()) {
            jsonRet = JSONObject.fromObject(new CommonResponse(2, "No Such File or Folder"));
            return jsonRet.toString();
        }

        //IF the file is not a directory or folder
        if (absoluteFile.isFile()) {
            jsonRet = JSONObject.fromObject(new CommonResponse(3, "Not a Folder"));
            return jsonRet.toString();
        }

        //ELSE the file is a folder
        //Create new folder in folder
        int counter = 1;
        String newFolderPath = absolutePath + "\\NewFolder" + counter;
        File newFolder = new File(newFolderPath);
        while (newFolder.exists()) {
            counter++;
            newFolderPath = absolutePath + "\\NewFolder" + counter;
            newFolder = new File(newFolderPath);
        }
        newFolder.mkdirs();

        //successful
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Create New Folder Successful"));
        return jsonRet.toString();
    }

    //2. delete file
    @RequestMapping("/delete_file")
    @CrossOrigin
    public String deleteFile(@RequestParam(value="relativePath") String relativePath,
                               @RequestParam(value="id") String id,
                               HttpServletRequest request) {
        //Construct Response
        JSONObject jsonRet;

        //relative path cannot be empty (or will delete all)
        if (relativePath.length() == 0) {
            jsonRet = JSONObject.fromObject(new CommonResponse(5, "Relative Path Cannot Be Empty"));
            return jsonRet.toString();
        }

        //id cannot be empty
        if (id.length() == 0) {
            jsonRet = JSONObject.fromObject(new CommonResponse(4, "Id Cannot Be Empty"));
            return jsonRet.toString();
        }

        //Construct absolute root path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF\\classes\\static\\data\\disk\\";
        absoluteRootPath += id + "\\files\\";

        //Make sure the root directory exists
        File rootDir = new File(absoluteRootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        //Construct absolute path from RelativePath param
        String absolutePath = absoluteRootPath + relativePath;

        //Make sure the absolute file exists
        File absoluteFile = new File(absolutePath);
        if (!absoluteFile.exists()) {
            jsonRet = JSONObject.fromObject(new CommonResponse(2, "No Such File or Folder"));
            return jsonRet.toString();
        }

        //Delete it
        Global.deleteDir(absoluteFile);

        //successful
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Delete Successful"));
        return jsonRet.toString();
    }

    //3. rename file
    @RequestMapping("/rename_file")
    @CrossOrigin
    public String renameFile(@RequestParam(value="relativePath") String relativePath,
                             @RequestParam(value="oldName") String oldName,
                             @RequestParam(value="newName") String newName,
                             @RequestParam(value="id") String id,
                             HttpServletRequest request) {
        //Construct Response
        JSONObject jsonRet;

        //newName cannot be empty
        if (newName.length() == 0 || oldName.length() == 0) {
            jsonRet = JSONObject.fromObject(new CommonResponse(5, "Old or New Name Cannot Be Empty"));
            return jsonRet.toString();
        }

        //id cannot be empty
        if (id.length() == 0) {
            jsonRet = JSONObject.fromObject(new CommonResponse(4, "Id Cannot Be Empty"));
            return jsonRet.toString();
        }

        //Construct absolute root path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF\\classes\\static\\data\\disk\\";
        absoluteRootPath += id + "\\files\\";

        //Make sure the root directory exists
        File rootDir = new File(absoluteRootPath);
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }

        //relativepath
//        relativePath = URLDecoder.decode(relativePath);
        if (relativePath.length() != 0) {
            relativePath = relativePath + "\\";
        }

        //Construct absolute path from RelativePath param
        String absolutePath = absoluteRootPath + relativePath;
        String oldAbsolutePath = absolutePath + oldName;
        String newAbsolutePath = absolutePath + newName;

        //Make sure the absolute file exists
        File oldAbsoluteFile = new File(oldAbsolutePath);
        if (!oldAbsoluteFile.exists()) {
            jsonRet = JSONObject.fromObject(new CommonResponse(2, "No Such File or Folder"));
            return jsonRet.toString();
        }

        //rename it
        if (!oldAbsoluteFile.renameTo(new File(newAbsolutePath))) {
            jsonRet = JSONObject.fromObject(new CommonResponse(1,"Rename Failed"));
            return jsonRet.toString();
        }

        //successful
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Rename Successful"));
        return jsonRet.toString();
    }

}
