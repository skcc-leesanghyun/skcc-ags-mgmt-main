package com.skcc.ra.common.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.skcc.ra.common.api.dto.domainDto.DeptDto;
import com.skcc.ra.common.domain.dept.Bssmacd;
import com.skcc.ra.common.exception.ServiceException;
import com.skcc.ra.common.service.DeptService;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "[기타] 부서 조회(DeptResource)", description = "외부에서 연동받은 부서 정보 조회를 위한 API")
@RestController
@RequestMapping("/v1/com/common/dept")
@Slf4j
public class DeptResource {
    @Autowired
    private DeptService deptService;

    @Operation(summary = "부서 리스트 조회(tree) - 상위코드 기준")
    @GetMapping
    public ResponseEntity<List<DeptDto>> searchDeptList(@RequestParam(required = false) String superDeptcd) {
        return new ResponseEntity<>(deptService.searchDeptList(superDeptcd), HttpStatus.OK);
    }
    
    @Operation(summary = "본부 조회 - 사용 중인 본부")
    @GetMapping("/bssmacd/use")
    public ResponseEntity<List<Bssmacd>> searchUseBssmacd() {
        return new ResponseEntity<>(deptService.searchUseBssmacd(), HttpStatus.OK);
    }

    @Operation(summary = "부서 조회 - 조건별")
    @GetMapping("/search")
    public ResponseEntity<Page<DeptDto>> searchDeptPage(@RequestParam(required = false) String bssmacd,
                                                        @RequestParam(required = false) String deptNm,
                                                        @RequestParam(required = false) String useYn,
                                                        Pageable pageable) {
        return new ResponseEntity<>(deptService.searchDeptPage(bssmacd, deptNm, useYn, pageable), HttpStatus.OK);
    }

    @Operation(summary = "부서 조회 - 부서 코드 기준")
    @GetMapping("/deptcd")
    public ResponseEntity<DeptDto> searchDeptcd(@RequestParam String deptcd) {
        // return new ResponseEntity<>(deptService.searchDeptcd(deptcd), HttpStatus.OK);
        try {
            DeptDto deptDto = deptService.searchDeptcd(deptcd);
            if (deptDto == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(deptDto, HttpStatus.OK);
        } catch (ServiceException e) {
            log.error("Service error in searchDeptcd: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            log.error("Unexpected error in searchDeptcd: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
