package hk.hku.cs.aacloud.controller;

import hk.hku.cs.aacloud.entity.response.CommonResponse;
import hk.hku.cs.aacloud.service.UserService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    //1.登录
    @RequestMapping("/login")
    @CrossOrigin
    public String login(@RequestParam(value="id") String id, @RequestParam(value="password") String password){

        JSONObject jsonRet;

        //登陆失败
        if (!userService.login(id, password)) {
            jsonRet = JSONObject.fromObject(new CommonResponse(1,"Authentication Failed"));
            return jsonRet.toString();
        }

        //登陆成功
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Login Successful"));
        return jsonRet.toString();
    }

}
