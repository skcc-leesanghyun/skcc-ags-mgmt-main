package com.skcc.ra.common.service;

import com.skcc.ra.common.api.dto.domainDto.CmmnCdDtlDto;
import com.skcc.ra.common.api.dto.domainDto.CmmnCdDto;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

public interface CmmnCdService {

    CmmnCdDto create(CmmnCdDto cmmnCdDto);
    CmmnCdDto update(CmmnCdDto cmmnCdDto);

    List<CmmnCdDto> findCmmnCdList(String chrgTaskGroupCd, String cmmnCdNm);
    void excelDownloadCmmnCd(String chrgTaskGroupCd, String cmmnCdNm) throws IOException, InvocationTargetException, IllegalAccessException, NoSuchMethodException;

    CmmnCdDtlDto createDtl(CmmnCdDtlDto cmmnCdDtlDto);

    CmmnCdDtlDto updateDtl(CmmnCdDtlDto cmmnCdDtlDto);

    List<CmmnCdDtlDto> findByCmmnCd(String cmmnCd);
    HashMap<String, Object> findByCmmnCdDtlList(List<String> cmmnCd);
    void updateDtlList(List<CmmnCdDtlDto> cmmnCdDtlDtoList);

    List<CmmnCdDtlDto> findCmmnCdDtlByCondition(String chrgTaskGroupCd, String cmmnCdNm);
    List<CmmnCdDtlDto> searchCmmnCdDtlBySuperfindByCmmnCd(String cmmnCd, String superCmmnCd, String superCmmnCdVal);
}
