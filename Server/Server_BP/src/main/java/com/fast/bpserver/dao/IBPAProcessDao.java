package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nelson on 2019/12/6.
 */
@Repository
public interface IBPAProcessDao extends JpaRepository<BPAProcess,String> {
    List<BPAProcess> findByProcessType(String processType);
}
