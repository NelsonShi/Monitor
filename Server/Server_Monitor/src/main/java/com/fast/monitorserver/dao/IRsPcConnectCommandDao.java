package com.fast.monitorserver.dao;

import com.fast.monitorserver.entity.RsPcConnectCommand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/10.
 */
@Repository
public interface IRsPcConnectCommandDao extends JpaRepository<RsPcConnectCommand,String>{
}
