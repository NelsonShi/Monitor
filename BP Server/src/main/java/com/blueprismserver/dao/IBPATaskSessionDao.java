package com.blueprismserver.dao;

import com.blueprismserver.entity.BPATaskSession;
import com.blueprismserver.entity.BPAUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sjlor on 2019/11/11.
 */
@Repository
public interface IBPATaskSessionDao extends JpaRepository<BPATaskSession,String> {
}
