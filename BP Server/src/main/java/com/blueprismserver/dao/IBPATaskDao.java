package com.blueprismserver.dao;

import com.blueprismserver.entity.BPATask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by sjlor on 2019/11/11.
 */
@Repository
public interface IBPATaskDao extends JpaRepository<BPATask,String> {
}
