-- Delete existing data
-- DELETE FROM OCO50200;
DELETE FROM oco.oco50200;
-- DELETE FROM OCO20101;
DELETE FROM oco.oco20101;
-- DELETE FROM OCO50100;
DELETE FROM oco.oco50100;
-- DELETE FROM OCO10100;
DELETE FROM oco.oco10100;

-- Insert into OCO50200
-- INSERT INTO OCO50200 (
--     DEPTCD, LAST_CHNGR_ID, LAST_CHNG_DTMD, MGMT_DEPTCD, DEPT_NM, ENGSH_DEPT_NM,
--     ENGSH_DEPT_ABBR_NM, BSSMACD, SUPER_DEPTCD, DEPT_GRADE_CD, DEPT_CRAT_DT,
--     DEPT_ABOL_DT, FEMP_DEPTCD, WHTAX_BZPL_CD, SALS_DEPTCD, INV_DEPTCD,
--     INVNT_WRHUS_CD, VAT_BZPL_CD, ZIPCD, BASIC_ADDR, DETIL_ADDR, EXTSN_NO,
--     PHNO, FAX_NO, BZNO, BZMAN_ACQ_DT, TXOFC_CD, TXOFC_NM, COM_BSCND_NM,
--     COM_ITM_NM, COM_CORP_NM, USE_YN, CCNTR_CD, RPRTT_NM
-- ) VALUES
-- ('D001', 'system', NOW(), 'D000', 'Tech Support', 'Tech Support', 'TS', '01', 'D000', '1', '20230414',
--  '20230414', 'F001', '001', 'S001', 'INV001', 'WH001', 'VAT001', '100000', '123 Tech St', '456 Details Ave',
--  '0010', '123-456-7890', '123-456-7891', '1234567890', '20230414', 'TX1', 'Tech Office', 'Tech Company',
--  'Item A', 'Corp A', 'Y', 'CN001', 'Report A'),
INSERT INTO oco.oco50200 (
    deptcd, last_chngr_id, last_chng_dtmd, mgmt_deptcd, dept_nm, engsh_dept_nm,
    engsh_dept_abbr_nm, bssmacd, super_deptcd, dept_grade_cd, dept_crat_dt,
    dept_abol_dt, femp_deptcd, whtax_bzpl_cd, sals_deptcd, inv_deptcd,
    invnt_wrhus_cd, vat_bzpl_cd, zipcd, basic_addr, detil_addr, extsn_no,
    phno, fax_no, bzno, bzman_acq_dt, txofc_cd, txofc_nm, com_bscnd_nm,
    com_itm_nm, com_corp_nm, use_yn, ccntr_cd, rprtt_nm
) VALUES
('D001', 'system', CURRENT_TIMESTAMP, 'D000', 'Tech Support', 'Tech Support', 'TS', '01', 'D000', '1', '20230414',
 '20230414', 'F001', '001', 'S001', 'INV001', 'WH001', 'VAT001', '100000', '123 Tech St', '456 Details Ave',
 '0010', '123-456-7890', '123-456-7891', '1234567890', '20230414', 'TX1', 'Tech Office', 'Tech Company',
 'Item A', 'Corp A', 'Y', 'CN001', 'Report A'),

-- ('D002', 'system', NOW(), 'D000', 'Sales Team', 'Sales Team', 'ST', '02', 'D001', '2', '20230414',
--  '20230414', 'F002', '002', 'S002', 'INV002', 'WH002', 'VAT002', '200000', '789 Sales Rd', '101 Details St',
--  '0011', '234-567-8901', '234-567-8902', '2345678901', '20230414', 'TX2', 'Sales Office', 'Sales Company',
--  'Item B', 'Corp B', 'Y', 'CN002', 'Report B'),
('D002', 'system', CURRENT_TIMESTAMP, 'D000', 'Sales Team', 'Sales Team', 'ST', '02', 'D001', '2', '20230414',
 '20230414', 'F002', '002', 'S002', 'INV002', 'WH002', 'VAT002', '200000', '789 Sales Rd', '101 Details St',
 '0011', '234-567-8901', '234-567-8902', '2345678901', '20230414', 'TX2', 'Sales Office', 'Sales Company',
 'Item B', 'Corp B', 'Y', 'CN002', 'Report B'),

-- ('D003', 'system', NOW(), 'D000', 'R&D', 'Research and Development', 'RD', '03', 'D002', '3', '20230414',
--  '20230414', 'F003', '003', 'S003', 'INV003', 'WH003', 'VAT003', '300000', '456 Research Ave', '789 Details Blvd',
--  '0012', '345-678-9012', '345-678-9013', '3456789012', '20230414', 'TX3', 'R&D Office', 'R&D Company',
--  'Item C', 'Corp C', 'Y', 'CN003', 'Report C');
('D003', 'system', CURRENT_TIMESTAMP, 'D000', 'R&D', 'Research and Development', 'RD', '03', 'D002', '3', '20230414',
 '20230414', 'F003', '003', 'S003', 'INV003', 'WH003', 'VAT003', '300000', '456 Research Ave', '789 Details Blvd',
 '0012', '345-678-9012', '345-678-9013', '3456789012', '20230414', 'TX3', 'R&D Office', 'R&D Company',
 'Item C', 'Corp C', 'Y', 'CN003', 'Report C');

-- Insert into OCO20101 - USERID_STS_CD
-- INSERT INTO OCO20101 (
--     CMMN_CD, CMMN_CD_VAL, CMMN_CD_VAL_NM, SORT_SEQN, LAST_CHNGR_ID, LAST_CHNG_DTMD
-- ) VALUES
-- ('USERID_STS_CD', 'O', 'Active', 1, 'system', NOW()),
-- ('USERID_STS_CD', 'L', 'Locked', 2, 'system', NOW());
INSERT INTO oco.oco20101 (
    cmmn_cd, cmmn_cd_val, cmmn_cd_val_nm, sort_seqn, last_chngr_id, last_chng_dtmd
) VALUES
('USERID_STS_CD', 'O', 'Active', 1, 'system', CURRENT_TIMESTAMP),
('USERID_STS_CD', 'L', 'Locked', 2, 'system', CURRENT_TIMESTAMP);

-- Insert into OCO20101 - REOFO_CD
-- INSERT INTO OCO20101 (
--     CMMN_CD, CMMN_CD_VAL, CMMN_CD_VAL_NM, SORT_SEQN, LAST_CHNGR_ID, LAST_CHNG_DTMD
-- ) VALUES
-- ('REOFO_CD', '001', 'Manager', 1, 'system', NOW()),
-- ('REOFO_CD', '002', 'Engineer', 2, 'system', NOW());
INSERT INTO oco.oco20101 (
    cmmn_cd, cmmn_cd_val, cmmn_cd_val_nm, sort_seqn, last_chngr_id, last_chng_dtmd
) VALUES
('REOFO_CD', '001', 'Manager', 1, 'system', CURRENT_TIMESTAMP),
('REOFO_CD', '002', 'Engineer', 2, 'system', CURRENT_TIMESTAMP);

-- Insert into OCO50100
-- INSERT INTO OCO50100 (
--     EMPNO, EMP_KRN_NM, EMP_ENGLNM, EMP_ENGFNM, DEPTCD, CLOFP_NM, VCTN_NM,
--     LAST_CHNGR_ID, LAST_CHNG_DTMD, OWHM_PHNO, GROUP_ENTCP_DT, TCOM_ENTCP_DT,
--     CURTP_OFORD_DT, CURTP_OFORD_CD, COM_CL_CD, CLOFP_CD, REOFO_CD, JGP_CD,
--     VCTN_CD, TEAMBR_CL_CD, JGP_NM, EMAILADDR, MPHNO, RESG_DT
-- ) VALUES
-- (
--     'E0001', 'John Smith', 'Smith', 'John', 'D001', 'Senior', 'IT',
--     'system', NOW(), '123-456-7890', '20240414', '20240415',
--     '20240416', 'C01', 'A', 'B01', 'C01', 'R', 'J',
--     'V01', 'T01', 'J', 'john.smith', NULL
-- ),
INSERT INTO oco.oco50100 (
    empno, emp_krn_nm, emp_englnm, emp_engfnm, deptcd, clofp_nm, vctn_nm,
    last_chngr_id, last_chng_dtmd, owhm_phno, group_entcp_dt, tcom_entcp_dt,
    curtp_oford_dt, curtp_oford_cd, com_cl_cd, clofp_cd, reofo_cd, jgp_cd,
    vctn_cd, teambr_cl_cd, jgp_nm, emailaddr, mphno, resg_dt
) VALUES
(
    'E0001', 'John Smith', 'Smith', 'John', 'D001', 'Senior', 'IT',
    'system', CURRENT_TIMESTAMP, '123-456-7890', '20240414', '20240415',
    '20240416', 'C01', 'A', 'B01', 'C01', 'R', 'J',
    'V01', 'T01', 'J', 'john.smith', NULL
),
-- (
--     'E0002', 'Alice Brown', 'Brown', 'Alice', 'D002', 'Manager', 'Sales',
--     'system', NOW(), '234-567-8901', '20240414', '20240415',
--     '20240418', 'C02', 'B', 'B02', 'C02', 'R', 'J',
--     'V02', 'T02', 'J', 'alice.brown', NULL
-- );
(
    'E0002', 'Alice Brown', 'Brown', 'Alice', 'D002', 'Manager', 'Sales',
    'system', CURRENT_TIMESTAMP, '234-567-8901', '20240414', '20240415',
    '20240418', 'C02', 'B', 'B02', 'C02', 'R', 'J',
    'V02', 'T02', 'J', 'alice.brown', NULL
);

-- Insert into OCO10100
-- INSERT INTO OCO10100 (
--     USERID, USER_NM, CONN_PSSWD, PSSWD_EXPIR_DT, USER_CONT_PHNO, USER_EMAILADDR,
--     DEPTCD, REOFO_CD, VCTN_CD, USER_GROUP_CD, INNER_USER_CL_CD, USER_IDENT_NO,
--     FST_REG_DTMD, PSSWD_ERR_FRQY, USERID_STS_CD, USER_IPADDR,
--     LAST_CHNGR_ID, LAST_CHNG_DTMD
-- ) VALUES
-- ('user01', 'JohnS', '0CF554A809A7C2DC476BE6983C7E676E45EDDD583EBB080EFF01FFB07B498A6971879667A2A8FC4F4AA9DC1D0F9349F1897E176A411806F3D1391E73C9250F37', '20250601', '01012345678', 'john@example.com', 'D001', '001', '001', 'A', '1', 'E0001', NOW(), 0, 'O', '192.168.0.1', 'system', NOW()),
INSERT INTO oco.oco10100 (
    userid, user_nm, conn_psswd, psswd_expir_dt, user_cont_phno, user_emailaddr,
    deptcd, reofo_cd, vctn_cd, user_group_cd, inner_user_cl_cd, user_ident_no,
    fst_reg_dtmd, psswd_err_frqy, userid_sts_cd, user_ipaddr,
    last_chngr_id, last_chng_dtmd
) VALUES
('user01', 'JohnS', '0CF554A809A7C2DC476BE6983C7E676E45EDDD583EBB080EFF01FFB07B498A6971879667A2A8FC4F4AA9DC1D0F9349F1897E176A411806F3D1391E73C9250F37', '20250601', '01012345678', 'john@example.com', 'D001', '001', '001', 'A', '1', 'E0001', CURRENT_TIMESTAMP, 0, 'O', '192.168.0.1', 'system', CURRENT_TIMESTAMP),
-- ('user02', 'AliceB', 'pass456!@#', '20250601', '01087654321', 'alice@example.com', 'D002', '002', '002', 'A', '1', 'E0002', NOW(), 0, 'O', '192.168.0.2', 'system', NOW()),
('user02', 'AliceB', 'pass456!@#', '20250601', '01087654321', 'alice@example.com', 'D002', '002', '002', 'A', '1', 'E0002', CURRENT_TIMESTAMP, 0, 'O', '192.168.0.2', 'system', CURRENT_TIMESTAMP),
-- ('user03', 'BobK', 'pass789!@#', '20250601', '01011112222', 'bob@example.com', 'D001', '001', '001', 'A', '2', NULL, NOW(), 1, 'L', '192.168.0.3', 'system', NOW()),
('user03', 'BobK', 'pass789!@#', '20250601', '01011112222', 'bob@example.com', 'D001', '001', '001', 'A', '2', NULL, CURRENT_TIMESTAMP, 1, 'L', '192.168.0.3', 'system', CURRENT_TIMESTAMP),
-- ('user04', 'CarolM', 'carolPass!@#', '20250601', '01033334444', 'carol@example.com', 'D003', '002', '002', 'B', '3', NULL, NOW(), 2, 'O', '192.168.0.4', 'system', NOW()),
('user04', 'CarolM', 'carolPass!@#', '20250601', '01033334444', 'carol@example.com', 'D003', '002', '002', 'B', '3', NULL, CURRENT_TIMESTAMP, 2, 'O', '192.168.0.4', 'system', CURRENT_TIMESTAMP),
-- ('user05', 'DavidL', 'davidPass!@#', '20250601', '01055556666', 'david@example.com', 'D004', '001', '001', 'C', '9', NULL, NOW(), 5, 'O', '192.168.0.5', 'system', NOW()),
('user05', 'DavidL', 'davidPass!@#', '20250601', '01055556666', 'david@example.com', 'D004', '001', '001', 'C', '9', NULL, CURRENT_TIMESTAMP, 5, 'O', '192.168.0.5', 'system', CURRENT_TIMESTAMP),
-- ('user06', 'CarolM', 'carolPass!@#', '20250601', '01033334444', 'carol@example.com', 'D003', '002', '002', 'B', '3', NULL, NOW(), 2, 'D', '192.168.0.4', 'system', NOW());
('user06', 'CarolM', 'carolPass!@#', '20250601', '01033334444', 'carol@example.com', 'D003', '002', '002', 'B', '3', NULL, CURRENT_TIMESTAMP, 2, 'D', '192.168.0.4', 'system', CURRENT_TIMESTAMP);
