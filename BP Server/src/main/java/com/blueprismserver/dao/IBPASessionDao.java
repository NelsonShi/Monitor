package com.blueprismserver.dao;

import com.blueprismserver.entity.BPASession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sjlor on 2019/10/29.
 */
@Repository
public interface IBPASessionDao extends JpaRepository<BPASession,String>{
    List<BPASession> findByStatusid(Integer status);

    @Query(value="Select * FROM (SELECT *,Row_Number() OVER (PARTITION BY runningresourceid ORDER BY startdatetime DESC ) as rank from BPASession ) temp where rank=1", nativeQuery=true)
    List<BPASession> findRecentSession();
}
