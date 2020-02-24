package com.fast.monitorserver.dao;

import com.fast.monitorserver.entity.RsPcUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/10.
 */
@Repository
public interface IRsPcUserDao extends JpaRepository<RsPcUser,String>{
    RsPcUser findById(Integer id);
}
