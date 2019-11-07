package com.blueprismserver.dao;

import com.blueprismserver.entity.BPAUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2019/10/29.
 */
@Repository
public interface IBPAUserDao extends JpaRepository<BPAUser,String> {
}
