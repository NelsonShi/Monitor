package com.blueprismserver.dao;

import com.blueprismserver.entity.BPAResource;
import com.blueprismserver.entity.BPASchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Nelson on 2019/11/11.
 */
@Repository
public interface IBPAScheduleDao extends JpaRepository<BPASchedule,String> {
    List<BPASchedule> findByRetired(boolean id);
}
