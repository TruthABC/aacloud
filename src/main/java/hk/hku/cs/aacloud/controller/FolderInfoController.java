package hk.hku.cs.aacloud.controller;

import hk.hku.cs.aacloud.entity.response.FileInfo;
import hk.hku.cs.aacloud.entity.response.FolderInfoResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;

@RestController
public class FolderInfoController {

    //1. get FileInfoList by relative path
    @RequestMapping("/getFolderInfoByRelativePath")
    @CrossOrigin
    public String getFolderInfoByRelativePath(@RequestParam(value="relativePath") String relativePath,
                                                @RequestParam(value="id") String id,
                                                HttpServletRequest request) {
        //Construct Response
        JSONObject jsonRet;

        //id cannot be empty
        if (id.length() == 0) {
            jsonRet = JSONObject.fromObject(new FolderInfoResponse(4, "Id Cannot Be Empty", new ArrayList<>()));
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
            jsonRet = JSONObject.fromObject(new FolderInfoResponse(2, "No Such File or Folder", new ArrayList<>()));
            return jsonRet.toString();
        }

        //IF the file is not a directory or folder
        if (absoluteFile.isFile()) {
            jsonRet = JSONObject.fromObject(new FolderInfoResponse(3, "Not a Folder", new ArrayList<>()));
            return jsonRet.toString();
        }

        //ELSE the file is a folder
        // Construct Response according to folder's files
        // 1. init list
        File[] fileList = absoluteFile.listFiles();
        if (fileList == null)
            fileList = new File[0];
        ArrayList<FileInfo> fileInfoList = new ArrayList<>();
        // 2. fill list with folders first
        for (File file: fileList) {
            if (file.isFile())
                continue;
            FileInfo fileInfo = new FileInfo(file.getName(),
                    file.getAbsolutePath().substring(absoluteRootPath.length()),
                    1);
            fileInfoList.add(fileInfo);
        }
        // 3. fill list with non-folder files
        for (File file: fileList) {
            if (file.isDirectory())
                continue;
            FileInfo fileInfo = new FileInfo(file.getName(),
                    file.getAbsolutePath().substring(absoluteRootPath.length()),
                    0);
            fileInfoList.add(fileInfo);
        }
        // 4. Construct JSONOnject
        jsonRet = JSONObject.fromObject(new FolderInfoResponse(0, "", fileInfoList));

        return jsonRet.toString();
    }

}
