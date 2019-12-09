package com.fast.monitorserver.dao;

import com.fast.monitorserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2019/12/6.
 */
@Repository
public interface IUserDao extends JpaRepository<User,String>{
    User findByLoginNameAndPassword(String loginName,String password);
}
