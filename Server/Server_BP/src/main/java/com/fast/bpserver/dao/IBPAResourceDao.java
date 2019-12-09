package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sjlor on 2019/10/24.
 */
@Repository
public interface IBPAResourceDao extends JpaRepository<BPAResource,String> {
}
