package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPAResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sjlor on 2019/10/24.
 */
@Repository
public interface IBPAResourceDao extends JpaRepository<BPAResource,String> {
     @Query(value = "select max(B.lastupdated) from BPAResource B")
     Object[] findMaxLastUpdatedItem();
     List<BPAResource> findByAttributeID(Integer id1);
     @Query(value = "SELECT A.resourceid,A.DisplayStatus,A.userID,temp.statusid FROM BPAResource A,(SELECT B.statusid,B.runningresourceid , Row_Number() OVER(partition BY runningresourceid ORDER BY startdatetime DESC) as rank from BPASession B) temp where temp.rank=1 and a.resourceid=temp.runningresourceid and a.AttributeID=0"
     ,nativeQuery = true)
     List<Object[]> findResourceFreshDatas();
     BPAResource findByResourceid(String id);
}
