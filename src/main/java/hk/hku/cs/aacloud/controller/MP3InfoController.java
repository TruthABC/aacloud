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
public class MP3InfoController {

    //1. get all mp3 info by id
    @RequestMapping("/getAllMP3InfoById")
    @CrossOrigin
    public String getAllMP3InfoById(@RequestParam(value="id") String id,
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

        //recursively get all mp3 files of one user
        ArrayList<FileInfo> fileInfoList = getAllMP3InfoOfFolder(rootDir, absoluteRootPath);

        //Construct JSONOnject
        jsonRet = JSONObject.fromObject(new FolderInfoResponse(0, "", fileInfoList));

        return jsonRet.toString();
    }

    //recursively get all mp3 files of one folder
    private ArrayList<FileInfo> getAllMP3InfoOfFolder(File folder, String absoluteRootPath) {
        // 1. init list
        File[] fileList = folder.listFiles();
        if (fileList == null)
            fileList = new File[0];
        ArrayList<FileInfo> fileInfoList = new ArrayList<>();
        // 2. fill list with non-folder files
        for (File file: fileList) {
            //2.1. recursive if folder comes
            if (file.isDirectory()) {
                ArrayList<FileInfo> tempList = getAllMP3InfoOfFolder(file, absoluteRootPath);
                fileInfoList.addAll(tempList);
                continue;
            }
            //2.2. skip if not mp3
            if (!file.getName().endsWith(".mp3")) {
                continue;
            }
            //2.3. mp3 comes, add it to list
            FileInfo fileInfo = new FileInfo(file.getName(),
                    file.getAbsolutePath().substring(absoluteRootPath.length()),
                    0);
            fileInfoList.add(fileInfo);
        }
        return fileInfoList;
    }

}
