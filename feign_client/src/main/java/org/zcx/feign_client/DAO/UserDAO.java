package org.zcx.feign_client.DAO;

import org.springframework.stereotype.Repository;
import org.zcx.feign_client.pojo.User;

import java.util.List;

/**
 * 类说明
 *
 * @author zcx
 * @version 创建时间：2018/10/11  11:09
 */
//@Mapper
@Repository
public interface UserDAO {

    void saveUser(List<User> User);

    void createTable();

    List<User> getSavedUserList();

}
