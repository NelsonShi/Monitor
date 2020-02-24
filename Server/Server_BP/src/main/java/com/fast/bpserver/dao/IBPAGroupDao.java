package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2020/1/13.
 */
@Repository
public interface IBPAGroupDao extends JpaRepository<BPAGroup,String> {
}
