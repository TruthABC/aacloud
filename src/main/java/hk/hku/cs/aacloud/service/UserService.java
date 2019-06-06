package hk.hku.cs.aacloud.service;

import hk.hku.cs.aacloud.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserProcessor userProcessor;

    @Autowired
    public UserService(UserProcessor userProcessor) {
        this.userProcessor = userProcessor;
    }

    //1. login with id&password
    public boolean login(String id, String password) throws Exception {
        String truePassword = userProcessor.readPasswordById(id);
        return truePassword.equals(password);
    }

    //2. register with id&password
    public void register(String id, String password) throws Exception {
        boolean existed = userProcessor.checkExistById(id);
        if (existed) {
            throw new Exception("User Id Existed.");
        }
        userProcessor.writePasswordById(id, password);
    }

    //3. update user password
    public void updatePassword(String id, String password) throws Exception {
        boolean existed = userProcessor.checkExistById(id);
        if (!existed) {
            throw new Exception("User Id Not Existed.");
        }
        userProcessor.writePasswordById(id, password);
    }

}
