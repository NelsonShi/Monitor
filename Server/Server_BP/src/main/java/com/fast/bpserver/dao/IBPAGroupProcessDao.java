package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAGroupProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/13.
 */
@Repository
public interface IBPAGroupProcessDao extends JpaRepository<BPAGroupProcess,String>{
}
