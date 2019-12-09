package com.fast.bpserver.dao;

import com.fast.bpserver.entity.BPASessionLogNonUnicode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by sjlor on 2019/11/26.
 */
@Repository
public interface IBPASessionLogNonUnicodeDao extends JpaRepository<BPASessionLogNonUnicode,String> {
    List<BPASessionLogNonUnicode> findByStagenameContainsAndStartdatetimeAfterOrResultContainsAndStartdatetimeAfter(String code, Date date1, String error, Date date2);
}
