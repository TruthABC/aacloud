package hk.hku.cs.aacloud.mapper;

import hk.hku.cs.aacloud.entity.mapping.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    //1.根据id与password得到用户
    @Select(" SELECT *" +
            " FROM `user`" +
            " WHERE id = #{id}" +
            " AND password = #{password}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "password",column = "password")
    })
    List<User> getUserByIdPassword(@Param("id") String id, @Param("password") String password);

}
