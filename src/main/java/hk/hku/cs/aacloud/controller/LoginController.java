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

    //1. login
    @RequestMapping("/login")
    @CrossOrigin
    public String login(@RequestParam(value="id") String id, @RequestParam(value="password") String password){

        JSONObject jsonRet;

        //login failed
        if (!userService.login(id, password)) {
            jsonRet = JSONObject.fromObject(new CommonResponse(1,"Authentication Failed"));
            return jsonRet.toString();
        }

        //login successful
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Login Successful"));
        return jsonRet.toString();
    }

    //2. register
    @RequestMapping("/register")
    @CrossOrigin
    public String register(@RequestParam(value="id") String id, @RequestParam(value="password") String password){

        JSONObject jsonRet;

        //register failed
        if (!userService.register(id, password)) {
            jsonRet = JSONObject.fromObject(new CommonResponse(1,"Register Failed"));
            return jsonRet.toString();
        }

        //register successful
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Register Successful"));
        return jsonRet.toString();
    }

    //3. update user password
    @RequestMapping("/update_password")
    @CrossOrigin
    public String updatePassword(@RequestParam(value="id") String id, @RequestParam(value="password") String password){

        JSONObject jsonRet;

        //update password failed
        if (!userService.update(id, password)) {
            jsonRet = JSONObject.fromObject(new CommonResponse(1,"Update Password Failed"));
            return jsonRet.toString();
        }

        //update password successful
        jsonRet = JSONObject.fromObject(new CommonResponse(0,"Update Password Successful"));
        return jsonRet.toString();
    }

}
