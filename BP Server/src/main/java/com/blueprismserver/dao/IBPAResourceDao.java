package com.blueprismserver.dao;

import com.blueprismserver.entity.BPAResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sjlor on 2019/10/24.
 */
@Repository
public interface IBPAResourceDao extends JpaRepository<BPAResource,String> {
}
