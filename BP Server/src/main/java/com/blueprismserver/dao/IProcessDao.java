package com.blueprismserver.dao;

import com.blueprismserver.entity.BPAProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nelson on 2019/10/24.
 */
@Repository
public interface IProcessDao extends JpaRepository<BPAProcess,String> {
    List<BPAProcess> findByProcessType(String processType);
}
