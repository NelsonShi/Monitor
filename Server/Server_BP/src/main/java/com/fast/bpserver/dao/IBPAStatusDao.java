package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/21.
 */
@Repository
public interface IBPAStatusDao extends JpaRepository<BPAStatus,String> {
}
