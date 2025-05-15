-- CREATE TABLE OCO.OCO30100 (
--   ANNCE_NO bigint NOT NULL AUTO_INCREMENT COMMENT '공지번호',
--   LAST_CHNGR_ID varchar(10) NOT NULL COMMENT '최종변경자ID',
--   LAST_CHNG_DTMD datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경일시D',
--   ANNCE_TITLE_NM varchar(100) DEFAULT NULL COMMENT '공지제목명',
--   ANNCE_CNTNT longtext COMMENT '공지내용',
--   ANNCE_TASK_CL_CD varchar(2) DEFAULT NULL COMMENT '공지업무구분코드',
--   ANNCE_CTGR_CL_CD varchar(2) DEFAULT NULL COMMENT '공지카테고리구분코드',
--   ANNCE_START_DTM varchar(14) DEFAULT NULL COMMENT '공지시작일시',
--   ANNCE_END_DTM varchar(14) DEFAULT NULL COMMENT '공지종료일시',
--   SUPER_ANNCE_START_DTM varchar(14) DEFAULT NULL COMMENT '상위공지시작일시',
--   SUPER_ANNCE_END_DTM varchar(14) DEFAULT NULL COMMENT '상위공지종료일시',
--   REG_USERID varchar(10) DEFAULT NULL COMMENT '등록사용자ID',
--   REG_DTMD datetime DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시D',
--   DEL_YN varchar(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
--   PRIMARY KEY (ANNCE_NO)
-- ) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COMMENT='공통_공지내역';
CREATE TABLE oco.oco30100 (
  annce_no bigint GENERATED ALWAYS AS IDENTITY,
  last_chngr_id varchar(10) NOT NULL,
  last_chng_dtmd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  annce_title_nm varchar(100) DEFAULT NULL,
  annce_cntnt text,
  annce_task_cl_cd varchar(2) DEFAULT NULL,
  annce_ctgr_cl_cd varchar(2) DEFAULT NULL,
  annce_start_dtm varchar(14) DEFAULT NULL,
  annce_end_dtm varchar(14) DEFAULT NULL,
  super_annce_start_dtm varchar(14) DEFAULT NULL,
  super_annce_end_dtm varchar(14) DEFAULT NULL,
  reg_userid varchar(10) DEFAULT NULL,
  reg_dtmd timestamp DEFAULT CURRENT_TIMESTAMP,
  del_yn varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (annce_no)
);

COMMENT ON TABLE oco.oco30100 IS '공통_공지내역';
COMMENT ON COLUMN oco.oco30100.annce_no IS '공지번호';
COMMENT ON COLUMN oco.oco30100.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco30100.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco30100.annce_title_nm IS '공지제목명';
COMMENT ON COLUMN oco.oco30100.annce_cntnt IS '공지내용';
COMMENT ON COLUMN oco.oco30100.annce_task_cl_cd IS '공지업무구분코드';
COMMENT ON COLUMN oco.oco30100.annce_ctgr_cl_cd IS '공지카테고리구분코드';
COMMENT ON COLUMN oco.oco30100.annce_start_dtm IS '공지시작일시';
COMMENT ON COLUMN oco.oco30100.annce_end_dtm IS '공지종료일시';
COMMENT ON COLUMN oco.oco30100.super_annce_start_dtm IS '상위공지시작일시';
COMMENT ON COLUMN oco.oco30100.super_annce_end_dtm IS '상위공지종료일시';
COMMENT ON COLUMN oco.oco30100.reg_userid IS '등록사용자ID';
COMMENT ON COLUMN oco.oco30100.reg_dtmd IS '등록일시D';
COMMENT ON COLUMN oco.oco30100.del_yn IS '삭제여부';

-- CREATE TABLE OCO.OCO30101 (
--   ANNCE_NO bigint NOT NULL COMMENT '공지번호',
--   USERID varchar(10) NOT NULL COMMENT '사용자ID',
--   LAST_CHNGR_ID varchar(10) NOT NULL COMMENT '최종변경자ID',
--   LAST_CHNG_DTMD datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경일시D',
--   PRIMARY KEY (ANNCE_NO,USERID)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='공통_공지조회이력';
CREATE TABLE oco.oco30101 (
  annce_no bigint NOT NULL,
  userid varchar(10) NOT NULL,
  last_chngr_id varchar(10) NOT NULL,
  last_chng_dtmd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (annce_no,userid)
);

COMMENT ON TABLE oco.oco30101 IS '공통_공지조회이력';
COMMENT ON COLUMN oco.oco30101.annce_no IS '공지번호';
COMMENT ON COLUMN oco.oco30101.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco30101.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco30101.last_chng_dtmd IS '최종변경일시D';

-- CREATE TABLE OCO.OCO30102 (
--   ANNCE_NO bigint NOT NULL COMMENT '공지번호',
--   ANNCE_OBJ_SEQ int NOT NULL COMMENT '공지대상순번',
--   LAST_CHNGR_ID varchar(10) NOT NULL COMMENT '최종변경자ID',
--   LAST_CHNG_DTMD datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경일시D',
--   ANNCE_OBJ_DEPT_CL_CD varchar(2) DEFAULT NULL COMMENT '공지대상부서구분코드',
--   ANNCE_OBJ_DEPT_TEAM_CD varchar(6) DEFAULT NULL COMMENT '공지대상부서팀코드',
--   PRIMARY KEY (ANNCE_NO,ANNCE_OBJ_SEQ)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='공통_공지대상목록';
CREATE TABLE oco.oco30102 (
  annce_no bigint NOT NULL,
  annce_obj_seq int NOT NULL,
  last_chngr_id varchar(10) NOT NULL,
  last_chng_dtmd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  annce_obj_dept_cl_cd varchar(2) DEFAULT NULL,
  annce_obj_dept_team_cd varchar(6) DEFAULT NULL,
  PRIMARY KEY (annce_no,annce_obj_seq)
);

COMMENT ON TABLE oco.oco30102 IS '공통_공지대상목록';
COMMENT ON COLUMN oco.oco30102.annce_no IS '공지번호';
COMMENT ON COLUMN oco.oco30102.annce_obj_seq IS '공지대상순번';
COMMENT ON COLUMN oco.oco30102.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco30102.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco30102.annce_obj_dept_cl_cd IS '공지대상부서구분코드';
COMMENT ON COLUMN oco.oco30102.annce_obj_dept_team_cd IS '공지대상부서팀코드';

-- CREATE TABLE OCO.OCO30110 (
--   ANNCE_NO bigint NOT NULL COMMENT '공지번호',
--   ATAC_FILE_NO bigint NOT NULL DEFAULT '0' COMMENT '첨부파일번호',
--   LAST_CHNGR_ID varchar(10) NOT NULL COMMENT '최종변경자ID',
--   LAST_CHNG_DTMD datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경일시D',
--   DEL_YN varchar(1) NOT NULL DEFAULT 'N' COMMENT '삭제여부',
--   PRIMARY KEY (ANNCE_NO,ATAC_FILE_NO)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='공통_공지첨부파일목록';
CREATE TABLE oco.oco30110 (
  annce_no bigint NOT NULL,
  atac_file_no bigint NOT NULL DEFAULT '0',
  last_chngr_id varchar(10) NOT NULL,
  last_chng_dtmd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  del_yn varchar(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (annce_no,atac_file_no)
);

COMMENT ON TABLE oco.oco30110 IS '공통_공지첨부파일목록';
COMMENT ON COLUMN oco.oco30110.annce_no IS '공지번호';
COMMENT ON COLUMN oco.oco30110.atac_file_no IS '첨부파일번호';
COMMENT ON COLUMN oco.oco30110.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco30110.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco30110.del_yn IS '삭제여부';