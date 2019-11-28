package com.blueprismserver.dao;

import com.blueprismserver.entity.BPASession;
import com.blueprismserver.entity.QueryVo.BPASessionLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by sjlor on 2019/10/29.
 */
@Repository
public interface IBPASessionDao extends JpaRepository<BPASession,String>{
    @Query(value="Select * FROM (SELECT *,Row_Number() OVER (PARTITION BY runningresourceid ORDER BY startdatetime DESC ) as rank from BPASession ) temp where rank=1", nativeQuery=true)
    List<BPASession> findRecentSession();

    @Query(value = "SELECT a.sessionid,a.startdatetime,a.enddatetime,a.processid,a.starterresourceid,a.statusid,a.stoprequested,a.lastupdated,a.laststage,b.stagename,b.pagename,b.objectname,b.actionname,b.result " +
            "FROM BPASession a RIGHT JOIN BPASessionLog_NonUnicode b on a.sessionnumber=b.sessionnumber where a.startdatetime>=?1 and(a.statusid=2 or a.statusid= 3 or b.stagename like '%BE:%'or b.stagename like '%SE:%' or b.result like '%ERROR:%')",nativeQuery = true)
    List<Object[]> findErrorSessionAndLogs(String date);
}
