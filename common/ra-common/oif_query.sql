-- 기존 MySQL 구문
-- CREATE DATABASE OIF;
-- CREATE USER 'com_dev'@'%' IDENTIFIED BY 'qwer1234!';
-- GRANT ALL PRIVILEGES ON OIF.* TO 'com_dev'@'%';
-- FLUSH PRIVILEGES;

-- PostgreSQL 변환 구문
CREATE DATABASE oif;
CREATE USER com_dev WITH PASSWORD 'qwer1234!';
GRANT ALL PRIVILEGES ON DATABASE oif TO com_dev;
CREATE SCHEMA IF NOT EXISTS oif AUTHORIZATION com_dev;

-- 기존 MySQL 구문
-- CREATE TABLE OIF.OIF21300 (
--     BSSMACD VARCHAR(2) NOT NULL,
--     BSS_HQ_NM VARCHAR(50) NOT NULL,
--     USE_YN VARCHAR(1) NOT NULL,
--     CREATED_BY VARCHAR(255),
--     CREATED_AT TIMESTAMP,
--     LAST_MODIFIED_BY VARCHAR(255),
--     LAST_MODIFIED_AT TIMESTAMP,
--     PRIMARY KEY (BSSMACD)
-- ) ENGINE=InnoDB;

CREATE TABLE oif.oif21300 (
    bssmacd VARCHAR(2) NOT NULL, -- BSS본부코드
    bss_hq_nm VARCHAR(50) NOT NULL, -- BSS본부명
    use_yn VARCHAR(1) NOT NULL, -- 사용여부
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP,
    PRIMARY KEY (bssmacd)
);

-- 기존 MySQL 구문
-- CREATE TABLE OIF.OIF21200 (
--     DEPTCD VARCHAR(8) NOT NULL,
--     MGMT_DEPTCD VARCHAR(6),
--     DEPT_NM VARCHAR(50),
--     ENGSH_DEPT_NM VARCHAR(50),
--     ENGSH_DEPT_ABBR_NM VARCHAR(50),
--     BSSMACD VARCHAR(2),
--     SUPER_DEPTCD VARCHAR(6),
--     DEPT_GRADE_CD VARCHAR(1),
--     DEPT_CRAT_DT VARCHAR(8),
--     DEPT_ABOL_DT VARCHAR(8),
--     FEMP_DEPTCD VARCHAR(6),
--     WHTAX_BZPL_CD VARCHAR(6),
--     SALS_DEPTCD VARCHAR(6),
--     INV_DEPTCD VARCHAR(6),
--     INVNT_WRHUS_CD VARCHAR(6),
--     VAT_BZPL_CD VARCHAR(6),
--     ZIPCD VARCHAR(6),
--     BASIC_ADDR VARCHAR(200),
--     DETIL_ADDR VARCHAR(200),
--     PHNO VARCHAR(14),
--     FAX_NO VARCHAR(14),
--     BZNO VARCHAR(10),
--     BZMAN_ACQ_DT VARCHAR(8),
--     TXOFC_CD VARCHAR(3),
--     TXOFC_NM VARCHAR(20),
--     COM_BSCND_NM VARCHAR(30),
--     COM_ITM_NM VARCHAR(30),
--     COM_CORP_NM VARCHAR(50),
--     USE_YN VARCHAR(1),
--     CCNTR_CD VARCHAR(10),
--     RPRTT_NM VARCHAR(30),
--     EXTSN_NO VARCHAR(4),
--     CREATED_BY VARCHAR(255),
--     CREATED_AT TIMESTAMP,
--     LAST_MODIFIED_BY VARCHAR(255),
--     LAST_MODIFIED_AT TIMESTAMP,
--     PRIMARY KEY (DEPTCD)
-- ) ENGINE=InnoDB;

CREATE TABLE oif.oif21200 (
    deptcd VARCHAR(8) NOT NULL,
    mgmt_deptcd VARCHAR(6),
    dept_nm VARCHAR(50),
    engsh_dept_nm VARCHAR(50),
    engsh_dept_abbr_nm VARCHAR(50),
    bssmacd VARCHAR(2),
    super_deptcd VARCHAR(6),
    dept_grade_cd VARCHAR(1),
    dept_crat_dt VARCHAR(8),
    dept_abol_dt VARCHAR(8),
    femp_deptcd VARCHAR(6),
    whtax_bzpl_cd VARCHAR(6),
    sals_deptcd VARCHAR(6),
    inv_deptcd VARCHAR(6),
    invnt_wrhus_cd VARCHAR(6),
    vat_bzpl_cd VARCHAR(6),
    zipcd VARCHAR(6),
    basic_addr VARCHAR(200),
    detil_addr VARCHAR(200),
    phno VARCHAR(14),
    fax_no VARCHAR(14),
    bzno VARCHAR(10),
    bzman_acq_dt VARCHAR(8),
    txofc_cd VARCHAR(3),
    txofc_nm VARCHAR(20),
    com_bscnd_nm VARCHAR(30),
    com_itm_nm VARCHAR(30),
    com_corp_nm VARCHAR(50),
    use_yn VARCHAR(1),
    ccntr_cd VARCHAR(10),
    rprtt_nm VARCHAR(30),
    extsn_no VARCHAR(4),
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP,
    PRIMARY KEY (deptcd)
);

-- 기존 MySQL 구문
-- CREATE TABLE OIF.OIF21100 (
--     EMPNO VARCHAR(8) NOT NULL,
--     EMP_KRN_NM VARCHAR(30) NOT NULL,
--     EMP_ENGLNM VARCHAR(20) NOT NULL,
--     EMP_ENGFNM VARCHAR(20),
--     DEPTCD VARCHAR(6),
--     BTHDY VARCHAR(8),
--     OWHM_PHNO VARCHAR(14),
--     MPHNO VARCHAR(14),
--     GROUP_ENTCP_DT VARCHAR(8),
--     TCOM_ENTCP_DT VARCHAR(8),
--     CURTP_OFORD_DT VARCHAR(8),
--     CURTP_OFORD_CD VARCHAR(3),
--     RESG_DT VARCHAR(8),
--     COM_CL_CD VARCHAR(1),
--     CLOFP_CD VARCHAR(3),
--     REOFO_CD VARCHAR(3),
--     JGP_CD VARCHAR(1),
--     VCTN_CD VARCHAR(3),
--     TEAMBR_CL_CD VARCHAR(3),
--     CLOFP_NM VARCHAR(20),
--     JGP_NM VARCHAR(20),
--     VCTN_NM VARCHAR(20),
--     EMAILADDR VARCHAR(100),
--     BEFO_EMPNO VARCHAR(8),
--     CREATED_BY VARCHAR(255),
--     CREATED_AT TIMESTAMP,
--     LAST_MODIFIED_BY VARCHAR(255),
--     LAST_MODIFIED_AT TIMESTAMP,
--     PRIMARY KEY (EMPNO)
-- ) ENGINE=InnoDB;

CREATE TABLE oif.oif21100 (
    empno VARCHAR(8) NOT NULL,
    emp_krn_nm VARCHAR(30) NOT NULL,
    emp_englnm VARCHAR(20) NOT NULL,
    emp_engfnm VARCHAR(20),
    deptcd VARCHAR(6),
    bthdy VARCHAR(8),
    owhm_phno VARCHAR(14),
    mphno VARCHAR(14),
    group_entcp_dt VARCHAR(8),
    tcom_entcp_dt VARCHAR(8),
    curtp_oford_dt VARCHAR(8),
    curtp_oford_cd VARCHAR(3),
    resg_dt VARCHAR(8),
    com_cl_cd VARCHAR(1),
    clofp_cd VARCHAR(3),
    reofo_cd VARCHAR(3),
    jgp_cd VARCHAR(1),
    vctn_cd VARCHAR(3),
    teambr_cl_cd VARCHAR(3),
    clofp_nm VARCHAR(20),
    jgp_nm VARCHAR(20),
    vctn_nm VARCHAR(20),
    emailaddr VARCHAR(100),
    befo_empno VARCHAR(8),
    created_by VARCHAR(255),
    created_at TIMESTAMP,
    last_modified_by VARCHAR(255),
    last_modified_at TIMESTAMP,
    PRIMARY KEY (empno)
);