package com.skcc.ra.common.repository;

import jakarta.persistence.QueryHint;
import com.skcc.ra.common.api.dto.domainDto.BttnDto;
import com.skcc.ra.common.domain.menu.Bttn;
import com.skcc.ra.common.domain.menu.pk.BttnPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.hibernate.annotations.QueryHints.COMMENT;

@Repository
public interface BttnRepository extends JpaRepository<Bttn, BttnPK> {

    @QueryHints(@QueryHint(name=COMMENT, value="공통, common, 나태관"))
    @Query(value= "SELECT new com.skcc.ra.common.api.dto.domainDto.BttnDto( t, l.cmmnCdValNm, '' AS gbn) " +
            " FROM Bttn t " +
            " LEFT OUTER JOIN CmmnCdDtl l ON (l.cmmnCd = 'CRUD_CL_CD' AND t.crudClCd = l.cmmnCdVal) " +
            " WHERE t.screnId = :screnId ")
    List<BttnDto> findByScrenId(String screnId);
}
