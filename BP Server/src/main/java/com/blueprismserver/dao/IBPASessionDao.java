package com.blueprismserver.dao;

import com.blueprismserver.entity.BPASession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sjlor on 2019/10/29.
 */
@Repository
public interface IBPASessionDao extends JpaRepository<BPASession,String>{
}
