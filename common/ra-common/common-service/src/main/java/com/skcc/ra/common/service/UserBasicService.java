package com.skcc.ra.common.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.skcc.ra.common.api.dto.domainDto.UserBasicDto;
import com.skcc.ra.common.api.dto.responseDto.InnerCallInfoDto;

public interface UserBasicService {

    List<UserBasicDto> searchUserBasic(String userNm, List<String> deptList, String userid);

    Page<UserBasicDto> searchUserBasicPage(String bssmacd, String deptcd, String reofoCd, String clofpNm,
                                                 String vctnNm, String empno, String deptNm,
                                                 List<String> deptcdList, Pageable pageable);

    String searchUserPhone(String empno);

    UserBasicDto searchTeamLeader(String deptcd, String empno);

    List<UserBasicDto> searchUserList(List<String> empnoList, List<String> empNmList, List<String> deptcdList, String empNm, String deptNm);

    List<UserBasicDto> searchUserListNoMasking(List<String> empnoList, List<String> empNmList, List<String> deptcdList, String empNm, String deptNm);

    List<InnerCallInfoDto> searchUserByTelno(String telno);
}
