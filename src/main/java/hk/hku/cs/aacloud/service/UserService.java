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

    //1. login with id&password
    public boolean login(String id, String password) {
        List<User> user = userMapper.getUserByIdPassword(id, password);
        return user.size() > 0;
    }

    //2. register with id&password
    public boolean register(String id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        try {
            userMapper.createUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //3. update user (password)
    public boolean update(String id, String password) {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        try {
            userMapper.updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
