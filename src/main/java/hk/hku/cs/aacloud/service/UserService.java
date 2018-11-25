package hk.hku.cs.aacloud.service;

import hk.hku.cs.aacloud.entity.mapping.User;
import hk.hku.cs.aacloud.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    //1.根据id与password登陆
    public boolean login(String id, String password) {
        List<User> user = userMapper.getUserByIdPassword(id, password);
        return user.size() > 0;
    }

}
