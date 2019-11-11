package com.blueprismserver.dao;

import com.blueprismserver.entity.BPAScheduleTrigger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Nelson on 2019/11/11.
 */
@Repository
public interface IBPAScheduleTriggerDao extends JpaRepository<BPAScheduleTrigger,String> {

}
