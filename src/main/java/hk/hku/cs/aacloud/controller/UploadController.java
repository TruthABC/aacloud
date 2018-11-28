package hk.hku.cs.aacloud.controller;

import hk.hku.cs.aacloud.entity.response.CommonResponse;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

@RestController
public class UploadController {

    //1. get FileInfoList by relative path
    @RequestMapping("/upload")
    @CrossOrigin
    public String upload(@RequestParam(value="relativePath") String relativePath,
                         @RequestParam(value="id") String id,
                         HttpServletRequest request,
                         MultipartHttpServletRequest multiReq) {
        //Construct Response
        JSONObject jsonRet;

        //id cannot be empty
        if (id.length() == 0) {
            jsonRet = JSONObject.fromObject(new CommonResponse(4, "Id Cannot Be Empty"));
            return jsonRet.toString();
        }

        MultipartFile mFile = multiReq.getFile("file");
        if (mFile == null) {
            jsonRet = JSONObject.fromObject(new CommonResponse(5, "File Cannot Be Empty"));
            return jsonRet.toString();
        }

        //Construct absolute root path
        String absoluteRootPath = request.getRealPath("/");
        absoluteRootPath += "WEB-INF\\classes\\static\\data\\disk\\";
        absoluteRootPath += id + "\\files\\";

        try {
            // Get the file and save it somewhere
            String pathStr = absoluteRootPath + relativePath + mFile.getOriginalFilename();
            FileOutputStream fos = new FileOutputStream(new File(pathStr));
            FileInputStream fs = (FileInputStream) mFile.getInputStream();
            byte[] buffer=new byte[1024];
            int len = 0;
            while( (len = fs.read(buffer)) != -1){
                fos.write(buffer,  0, len);
            }
            fos.flush();
            fos.close();
            fs.close();
            jsonRet = JSONObject.fromObject(new CommonResponse(0, ""));
        } catch (Exception e) {
            e.printStackTrace();
            jsonRet = JSONObject.fromObject(new CommonResponse(6, "File Server Write Error"));
        }

        return jsonRet.toString();
    }

}
