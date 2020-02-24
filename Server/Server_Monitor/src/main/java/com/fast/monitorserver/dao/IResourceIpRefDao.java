package com.fast.monitorserver.dao;

import com.fast.monitorserver.entity.ResourceIpRef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/20.
 */
@Repository
public interface IResourceIpRefDao extends JpaRepository<ResourceIpRef,String>{
    ResourceIpRef findByResourceId(String id);
}
