package hk.hku.cs.aacloud.mapper;

import hk.hku.cs.aacloud.entity.mapping.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //1. get user by id and password
    @Select(" SELECT *" +
            " FROM `user`" +
            " WHERE id = #{id}" +
            " AND password = #{password}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "password",column = "password")
    })
    List<User> getUserByIdPassword(@Param("id") String id, @Param("password") String password);


    //2. register: insert a new user with password
    @Insert(" INSERT INTO `user`(id, password)" +
            " VALUES (#{id}, #{password})")
    void createUser(User user);
//    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id") // when use another AutoIncrease key

    //3. update user (password)
    @Update(" UPDATE `user`" +
            " SET password = #{password}" +
            " WHERE id = #{id}")
    void updateUser(User user);

}
