package com.cms.world.repository;

import com.cms.world.domain.dto.CommissionDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommissionRepository extends JpaRepository<CommissionDto, String> {

    List<CommissionDto> findByDelYnContaining(String delYn);

    @Query(value ="SELECT\n" +
            "    c.*,\n" +
            "    COALESCE(cp_status_cnt.CM02_count, 0) AS prsCnt,\n" +
            "    COALESCE(cp_status_cnt.CM08_count, 0) AS rsvCnt \n" +
            "FROM\n" +
            "    commission c\n" +
            "        LEFT JOIN\n" +
            "    (\n" +
            "        SELECT\n" +
            "            cp.cms_id,\n" +
            "            SUM(CASE WHEN cp.status = 'CM02' THEN 1 ELSE 0 END) AS CM02_count,\n" +
            "            SUM(CASE WHEN cp.status = 'CM08' THEN 1 ELSE 0 END) AS CM08_count\n" +
            "        FROM\n" +
            "            cms_aply cp\n" +
            "        GROUP BY\n" +
            "            cp.cms_id\n" +
            "    ) cp_status_cnt ON c.id = cp_status_cnt.cms_id and c.del_yn ='N'", nativeQuery = true)
    List<CommissionDto> selectList ();
}
