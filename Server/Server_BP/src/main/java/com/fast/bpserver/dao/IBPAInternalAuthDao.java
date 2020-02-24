package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAInternalAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/9.
 */
@Repository
public interface IBPAInternalAuthDao extends JpaRepository<BPAInternalAuth,String> {
}
