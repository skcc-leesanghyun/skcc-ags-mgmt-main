-- PostgreSQL 변환 구문
CREATE DATABASE oco;
CREATE USER com_dev WITH PASSWORD 'qwer1234!';
GRANT ALL PRIVILEGES ON DATABASE oco TO com_dev;
CREATE SCHEMA IF NOT EXISTS oco AUTHORIZATION com_dev;

-- 테이블 생성
CREATE TABLE oco.oco10100 (
  userid           VARCHAR(10)  NOT NULL, -- 사용자ID
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  user_nm          VARCHAR(30),           -- 사용자명
  conn_psswd       VARCHAR(256),          -- 접속비밀번호S
  psswd_expir_dt   VARCHAR(8),            -- 비밀번호만료일자
  user_cont_phno   VARCHAR(14),           -- 사용자연락전화번호
  user_emailaddr   VARCHAR(100),          -- 사용자이메일주소
  deptcd           VARCHAR(6),            -- 부서코드
  reofo_cd         VARCHAR(3),            -- 직책코드
  vctn_cd          VARCHAR(3),            -- 직능코드
  cucen_team_cd    VARCHAR(6),            -- 고객센터팀코드
  ofcps_cd         VARCHAR(4),            -- 직위코드
  user_group_cd    VARCHAR(1),            -- 사용자그룹코드
  inner_user_cl_cd VARCHAR(1),            -- 내부사용자구분코드
  user_ident_no    VARCHAR(10),           -- 사용자식별번호
  userid_sts_cd    VARCHAR(1),            -- 사용자ID상태코드
  fst_reg_dtmd     TIMESTAMP,             -- 최초등록일시D
  psswd_err_frqy   INTEGER DEFAULT 0,     -- 비밀번호오류횟수
  user_ipaddr      VARCHAR(16),           -- 사용자IP주소
  PRIMARY KEY (userid)
);
CREATE INDEX oco10100_n1 ON oco.oco10100 (user_ident_no);
CREATE INDEX oco10100_n2 ON oco.oco10100 (cucen_team_cd);
COMMENT ON COLUMN oco.oco10100.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10100.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10100.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10100.user_nm IS '사용자명';
COMMENT ON COLUMN oco.oco10100.conn_psswd IS '접속비밀번호S';
COMMENT ON COLUMN oco.oco10100.psswd_expir_dt IS '비밀번호만료일자';
COMMENT ON COLUMN oco.oco10100.user_cont_phno IS '사용자연락전화번호';
COMMENT ON COLUMN oco.oco10100.user_emailaddr IS '사용자이메일주소';
COMMENT ON COLUMN oco.oco10100.deptcd IS '부서코드';
COMMENT ON COLUMN oco.oco10100.reofo_cd IS '직책코드';
COMMENT ON COLUMN oco.oco10100.vctn_cd IS '직능코드';
COMMENT ON COLUMN oco.oco10100.cucen_team_cd IS '고객센터팀코드';
COMMENT ON COLUMN oco.oco10100.ofcps_cd IS '직위코드';
COMMENT ON COLUMN oco.oco10100.user_group_cd IS '사용자그룹코드';
COMMENT ON COLUMN oco.oco10100.inner_user_cl_cd IS '내부사용자구분코드';
COMMENT ON COLUMN oco.oco10100.user_ident_no IS '사용자식별번호';
COMMENT ON COLUMN oco.oco10100.userid_sts_cd IS '사용자ID상태코드';
COMMENT ON COLUMN oco.oco10100.fst_reg_dtmd IS '최초등록일시D';
COMMENT ON COLUMN oco.oco10100.psswd_err_frqy IS '비밀번호오류횟수';
COMMENT ON COLUMN oco.oco10100.user_ipaddr IS '사용자IP주소';

CREATE TABLE oco.oco10101 (
  user_role_id     VARCHAR(4)   NOT NULL,
  userid           VARCHAR(10)  NOT NULL,
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  athrty_reqst_seq  BIGINT,
  PRIMARY KEY (user_role_id, userid)
);
CREATE INDEX oco10101_test ON oco.oco10101 (userid);
COMMENT ON COLUMN oco.oco10101.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10101.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10101.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10101.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10101.athrty_reqst_seq IS '권한신청순번';

CREATE TABLE oco.oco10103 (
  userid           VARCHAR(10)  NOT NULL,
  chng_col_engsh_nm VARCHAR(50)  NOT NULL, -- 변경컬럼영문명
  chng_dtm        VARCHAR(14)  NOT NULL,
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  chng_col_val     VARCHAR(600),
  PRIMARY KEY (userid, chng_col_engsh_nm, chng_dtm)
);
COMMENT ON COLUMN oco.oco10103.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10103.chng_col_engsh_nm IS '변경컬럼영문명';
COMMENT ON COLUMN oco.oco10103.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10103.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10103.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10103.chng_col_val IS '변경컬럼값';

CREATE TABLE oco.oco10104 (
  userid           VARCHAR(10)  NOT NULL,
  menu_id          VARCHAR(12)  NOT NULL, -- 메뉴ID
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  sort_seqn        INTEGER      DEFAULT 0, -- 정렬순서
  PRIMARY KEY (userid, menu_id)
);
COMMENT ON COLUMN oco.oco10104.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10104.menu_id IS '메뉴ID';
COMMENT ON COLUMN oco.oco10104.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10104.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10104.sort_seqn IS '정렬순서';

CREATE TABLE oco.oco10105 (
  userid           VARCHAR(10)  NOT NULL,
  menu_id          VARCHAR(12)  NOT NULL, -- 메뉴ID
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  sort_seqn        INTEGER      DEFAULT 0, -- 정렬순서
  PRIMARY KEY (userid, menu_id)
);
COMMENT ON COLUMN oco.oco10105.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10105.menu_id IS '메뉴ID';
COMMENT ON COLUMN oco.oco10105.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10105.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10105.sort_seqn IS '정렬순서';

CREATE TABLE oco.oco10106 (
  userid           VARCHAR(10)  NOT NULL,
  menu_id          VARCHAR(12)  NOT NULL, -- 메뉴ID
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  athrty_reqst_seq  BIGINT,
  PRIMARY KEY (userid, menu_id)
);
COMMENT ON COLUMN oco.oco10106.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10106.menu_id IS '메뉴ID';
COMMENT ON COLUMN oco.oco10106.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10106.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10106.athrty_reqst_seq IS '권한신청순번';

CREATE TABLE oco.oco10107 (
  userid           VARCHAR(10)  NOT NULL,
  scren_id         VARCHAR(10)  NOT NULL, -- 화면ID
  bttn_id          VARCHAR(30)  NOT NULL, -- 버튼ID
  last_chngr_id    VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd   TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  athrty_reqst_seq  BIGINT,
  PRIMARY KEY (userid, scren_id, bttn_id)
);
COMMENT ON COLUMN oco.oco10107.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10107.scren_id IS '화면ID';
COMMENT ON COLUMN oco.oco10107.bttn_id IS '버튼ID';
COMMENT ON COLUMN oco.oco10107.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10107.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10107.athrty_reqst_seq IS '권한신청순번';

CREATE TABLE oco.oco10109 (
  userid_reqst_seq  BIGINT       GENERATED ALWAYS AS IDENTITY, -- 사용자ID신청순번
  last_chngr_id     VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd    TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  userid             VARCHAR(10), -- 사용자ID
  userid_reqst_dt   VARCHAR(8), -- 사용자ID신청일자
  userid_reqst_sts_cd VARCHAR(1), -- 사용자ID신청상태코드
  rvw_userid         VARCHAR(10), -- 검토사용자ID
  rvw_dt             VARCHAR(8), -- 검토일자
  authz_userid       VARCHAR(10), -- 승인사용자ID
  authz_dt           VARCHAR(8), -- 승인일자
  gvbk_reson_cntnt   VARCHAR(1000), -- 반려사유내용
  authz_user_ipaddr  VARCHAR(16), -- 승인사용자IP주소
  reqst_reson_cntnt  VARCHAR(1000), -- 신청사유내용
  PRIMARY KEY (userid_reqst_seq)
);
COMMENT ON COLUMN oco.oco10109.userid_reqst_seq IS '사용자ID신청순번';
COMMENT ON COLUMN oco.oco10109.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10109.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10109.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10109.userid_reqst_dt IS '사용자ID신청일자';
COMMENT ON COLUMN oco.oco10109.userid_reqst_sts_cd IS '사용자ID신청상태코드';
COMMENT ON COLUMN oco.oco10109.rvw_userid IS '검토사용자ID';
COMMENT ON COLUMN oco.oco10109.rvw_dt IS '검토일자';
COMMENT ON COLUMN oco.oco10109.authz_userid IS '승인사용자ID';
COMMENT ON COLUMN oco.oco10109.authz_dt IS '승인일자';
COMMENT ON COLUMN oco.oco10109.gvbk_reson_cntnt IS '반려사유내용';
COMMENT ON COLUMN oco.oco10109.authz_user_ipaddr IS '승인사용자IP주소';
COMMENT ON COLUMN oco.oco10109.reqst_reson_cntnt IS '신청사유내용';

CREATE TABLE oco.oco10110 (
  user_role_id       VARCHAR(4)   NOT NULL,
  last_chngr_id      VARCHAR(10)  NOT NULL,
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  user_role_nm       VARCHAR(50), -- 사용자역할명
  user_role_desc     VARCHAR(200), -- 사용자역할설명
  user_athrty_group_cd VARCHAR(3), -- 사용자역할그룹코드
  lock_athrty_cl_cd  VARCHAR(1), -- 잠금권한구분코드
  use_yn             VARCHAR(1)   NOT NULL DEFAULT 'Y',
  PRIMARY KEY (user_role_id)
);
COMMENT ON COLUMN oco.oco10110.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10110.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10110.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10110.user_role_nm IS '사용자역할명';
COMMENT ON COLUMN oco.oco10110.user_role_desc IS '사용자역할설명';
COMMENT ON COLUMN oco.oco10110.user_athrty_group_cd IS '사용자역할그룹코드';
COMMENT ON COLUMN oco.oco10110.lock_athrty_cl_cd IS '잠금권한구분코드';
COMMENT ON COLUMN oco.oco10110.use_yn IS '사용여부';

CREATE TABLE oco.oco10111 (
  role_dept_team_cd VARCHAR(6)   NOT NULL,
  role_mapp_reofo_cd VARCHAR(4)   NOT NULL,
  last_chngr_id      VARCHAR(10)  NOT NULL,
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  role_dept_team_cl_cd VARCHAR(1), -- 역할부서팀구분코드
  accnt_crat_auto_yn VARCHAR(1)   NOT NULL DEFAULT 'Y',
  use_yn             VARCHAR(1)   NOT NULL DEFAULT 'Y',
  PRIMARY KEY (role_dept_team_cd, role_mapp_reofo_cd)
);
COMMENT ON COLUMN oco.oco10111.role_dept_team_cd IS '역할부서팀코드';
COMMENT ON COLUMN oco.oco10111.role_mapp_reofo_cd IS '역할매핑직책코드';
COMMENT ON COLUMN oco.oco10111.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10111.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10111.role_dept_team_cl_cd IS '역할부서팀구분코드';
COMMENT ON COLUMN oco.oco10111.accnt_crat_auto_yn IS '계정생성자동여부';
COMMENT ON COLUMN oco.oco10111.use_yn IS '사용여부';

CREATE TABLE oco.oco10112 (
  userid             VARCHAR(10)  NOT NULL,
  user_role_id       VARCHAR(4)   NOT NULL, -- 사용자역할ID
  chng_dtm           VARCHAR(14)  NOT NULL, -- 변경일시
  crud_cl_cd         VARCHAR(1)   NOT NULL, -- CRUD구분코드
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  athrty_reqst_seq    BIGINT,
  deptcd             VARCHAR(6), -- 부서코드
  dept_nm            VARCHAR(50), -- 부서명
  cucen_team_cd      VARCHAR(3), -- 고객센터팀코드
  cucen_team_nm      VARCHAR(100), -- 고객센터팀명
  chng_user_ipaddr   VARCHAR(16), -- 변경사용자IP주소
  chng_reson_cntnt   VARCHAR(4000), -- 변경사유내용
  PRIMARY KEY (userid, user_role_id, chng_dtm, crud_cl_cd)
);
COMMENT ON COLUMN oco.oco10112.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10112.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10112.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10112.crud_cl_cd IS 'CRUD구분코드';
COMMENT ON COLUMN oco.oco10112.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10112.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10112.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10112.deptcd IS '부서코드';
COMMENT ON COLUMN oco.oco10112.dept_nm IS '부서명';
COMMENT ON COLUMN oco.oco10112.cucen_team_cd IS '고객센터팀코드';
COMMENT ON COLUMN oco.oco10112.cucen_team_nm IS '고객센터팀명';
COMMENT ON COLUMN oco.oco10112.chng_user_ipaddr IS '변경사용자IP주소';
COMMENT ON COLUMN oco.oco10112.chng_reson_cntnt IS '변경사유내용';

CREATE TABLE oco.oco10113 (
  role_dept_team_cd  VARCHAR(6)   NOT NULL, -- 역할부서팀코드
  role_mapp_reofo_cd  VARCHAR(4)   NOT NULL, -- 역할매핑직책코드
  user_role_id        VARCHAR(4)   NOT NULL, -- 사용자역할ID
  last_chngr_id       VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  PRIMARY KEY (role_dept_team_cd, role_mapp_reofo_cd, user_role_id)
);
COMMENT ON COLUMN oco.oco10113.role_dept_team_cd IS '역할부서팀코드';
COMMENT ON COLUMN oco.oco10113.role_mapp_reofo_cd IS '역할매핑직책코드';
COMMENT ON COLUMN oco.oco10113.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10113.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10113.last_chng_dtmd IS '최종변경일시D';

CREATE TABLE oco.oco10114 (
  role_dept_team_cd  VARCHAR(6)   NOT NULL, -- 역할부서팀코드
  role_mapp_reofo_cd  VARCHAR(4)   NOT NULL, -- 역할매핑직책코드
  user_role_id        VARCHAR(4)   NOT NULL, -- 사용자역할ID
  chng_dtm            VARCHAR(14)  NOT NULL, -- 변경일시
  last_chngr_id        VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  crud_cl_cd           VARCHAR(1),
  chng_user_ipaddr     VARCHAR(16), -- 변경사용자IP주소
  athrty_reqst_seq      BIGINT,
  chng_reson_cntnt      VARCHAR(4000), -- 변경사유내용
  PRIMARY KEY (role_dept_team_cd, role_mapp_reofo_cd, user_role_id, chng_dtm)
);
COMMENT ON COLUMN oco.oco10114.role_dept_team_cd IS '역할부서팀코드';
COMMENT ON COLUMN oco.oco10114.role_mapp_reofo_cd IS '역할매핑직책코드';
COMMENT ON COLUMN oco.oco10114.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10114.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10114.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10114.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10114.crud_cl_cd IS 'CRUD구분코드';
COMMENT ON COLUMN oco.oco10114.chng_user_ipaddr IS '변경사용자IP주소';
COMMENT ON COLUMN oco.oco10114.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10114.chng_reson_cntnt IS '변경사유내용';

CREATE TABLE oco.oco10116 (
  userid             VARCHAR(10)  NOT NULL,
  menu_id            VARCHAR(12)  NOT NULL, -- 메뉴ID
  chng_dtm           VARCHAR(14)  NOT NULL, -- 변경일시
  crud_cl_cd         VARCHAR(1)   NOT NULL, -- CRUD구분코드
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  athrty_reqst_seq    VARCHAR(18), -- 권한신청순번
  chng_user_ipaddr    VARCHAR(18), -- 변경사용자IP주소
  chng_reson_cntnt    VARCHAR(400), -- 변경사유내용
  PRIMARY KEY (userid, menu_id, chng_dtm, crud_cl_cd)
);
COMMENT ON COLUMN oco.oco10116.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10116.menu_id IS '메뉴ID';
COMMENT ON COLUMN oco.oco10116.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10116.crud_cl_cd IS 'CRUD구분코드';
COMMENT ON COLUMN oco.oco10116.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10116.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10116.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10116.chng_user_ipaddr IS '변경사용자IP주소';
COMMENT ON COLUMN oco.oco10116.chng_reson_cntnt IS '변경사유내용';

CREATE TABLE oco.oco10117 (
  userid             VARCHAR(10)  NOT NULL,
  scren_id           VARCHAR(10)  NOT NULL,
  bttn_id            VARCHAR(30)  NOT NULL, -- 버튼ID
  chng_dtm           VARCHAR(14)  NOT NULL, -- 변경일시
  crud_cl_cd         VARCHAR(1)   NOT NULL, -- CRUD구분코드
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  athrty_reqst_seq    VARCHAR(18), -- 권한신청순번
  chng_user_ipaddr    VARCHAR(18), -- 변경사용자IP주소
  chng_reson_cntnt    VARCHAR(400), -- 변경사유내용
  PRIMARY KEY (userid, scren_id, bttn_id, chng_dtm, crud_cl_cd)
);
COMMENT ON COLUMN oco.oco10117.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10117.scren_id IS '화면ID';
COMMENT ON COLUMN oco.oco10117.bttn_id IS '버튼ID';
COMMENT ON COLUMN oco.oco10117.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10117.crud_cl_cd IS 'CRUD구분코드';
COMMENT ON COLUMN oco.oco10117.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10117.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10117.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10117.chng_user_ipaddr IS '변경사용자IP주소';
COMMENT ON COLUMN oco.oco10117.chng_reson_cntnt IS '변경사유내용';

CREATE TABLE oco.oco10120 (
  user_role_id       VARCHAR(4)   NOT NULL, -- 사용자역할ID
  menu_id            VARCHAR(12)  NOT NULL, -- 메뉴ID
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  PRIMARY KEY (user_role_id, menu_id)
);
COMMENT ON COLUMN oco.oco10120.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10120.menu_id IS '메뉴ID';
COMMENT ON COLUMN oco.oco10120.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10120.last_chng_dtmd IS '최종변경일시D';

CREATE TABLE oco.oco10121 (
  user_role_id       VARCHAR(4)   NOT NULL, -- 사용자역할ID
  scren_id           VARCHAR(10)  NOT NULL, -- 화면ID
  bttn_id            VARCHAR(30)  NOT NULL, -- 버튼ID
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  PRIMARY KEY (user_role_id, scren_id, bttn_id)
);
COMMENT ON COLUMN oco.oco10121.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10121.scren_id IS '화면ID';
COMMENT ON COLUMN oco.oco10121.bttn_id IS '버튼ID';
COMMENT ON COLUMN oco.oco10121.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10121.last_chng_dtmd IS '최종변경일시D';

CREATE TABLE oco.oco10122 (
  user_role_id       VARCHAR(4)   NOT NULL, -- 사용자역할ID
  menu_id            VARCHAR(12)  NOT NULL, -- 메뉴ID
  chng_dtm           VARCHAR(14)  NOT NULL, -- 변경일시
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  crud_cl_cd         VARCHAR(1),
  chng_user_ipaddr    VARCHAR(16), -- 변경사용자IP주소
  athrty_reqst_seq    BIGINT,
  chng_reson_cntnt    VARCHAR(4000), -- 변경사유내용
  PRIMARY KEY (user_role_id, menu_id, chng_dtm)
);
COMMENT ON COLUMN oco.oco10122.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10122.menu_id IS '메뉴ID';
COMMENT ON COLUMN oco.oco10122.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10122.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10122.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10122.crud_cl_cd IS 'CRUD구분코드';
COMMENT ON COLUMN oco.oco10122.chng_user_ipaddr IS '변경사용자IP주소';
COMMENT ON COLUMN oco.oco10122.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10122.chng_reson_cntnt IS '변경사유내용';

CREATE TABLE oco.oco10123 (
  user_role_id       VARCHAR(4)   NOT NULL, -- 사용자역할ID
  scren_id           VARCHAR(10)  NOT NULL, -- 화면ID
  bttn_id            VARCHAR(30)  NOT NULL, -- 버튼ID
  chng_dtm           VARCHAR(14)  NOT NULL, -- 변경일시
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  crud_cl_cd         VARCHAR(1),
  chng_user_ipaddr    VARCHAR(16), -- 변경사용자IP주소
  athrty_reqst_seq    BIGINT,
  chng_reson_cntnt    VARCHAR(4000), -- 변경사유내용
  PRIMARY KEY (user_role_id, scren_id, bttn_id, chng_dtm)
);
COMMENT ON COLUMN oco.oco10123.user_role_id IS '사용자역할ID';
COMMENT ON COLUMN oco.oco10123.scren_id IS '화면ID';
COMMENT ON COLUMN oco.oco10123.bttn_id IS '버튼ID';
COMMENT ON COLUMN oco.oco10123.chng_dtm IS '변경일시';
COMMENT ON COLUMN oco.oco10123.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10123.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10123.crud_cl_cd IS 'CRUD구분코드';
COMMENT ON COLUMN oco.oco10123.chng_user_ipaddr IS '변경사용자IP주소';
COMMENT ON COLUMN oco.oco10123.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10123.chng_reson_cntnt IS '변경사유내용';

CREATE TABLE oco.oco10130 (
  athrty_reqst_seq    BIGINT       GENERATED ALWAYS AS IDENTITY,
  last_chngr_id        VARCHAR(10)  NOT NULL,
  last_chng_dtmd       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  athrty_reqst_sts_cd   VARCHAR(1), -- 권한신청상태코드
  userid               VARCHAR(10),
  reqst_reson_cntnt    VARCHAR(1000),
  indiv_info_yn        VARCHAR(1)   DEFAULT 'N',
  rpa_user_yn          VARCHAR(1)   DEFAULT 'N',
  reqst_user_ipaddr    VARCHAR(16), -- 신청사용자IP주소
  PRIMARY KEY (athrty_reqst_seq)
);
COMMENT ON COLUMN oco.oco10130.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10130.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10130.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10130.athrty_reqst_sts_cd IS '권한신청상태코드';
COMMENT ON COLUMN oco.oco10130.userid IS '사용자ID';
COMMENT ON COLUMN oco.oco10130.reqst_reson_cntnt IS '신청사유내용';
COMMENT ON COLUMN oco.oco10130.indiv_info_yn IS '개인정보여부';
COMMENT ON COLUMN oco.oco10130.rpa_user_yn IS 'RPA사용자여부';
COMMENT ON COLUMN oco.oco10130.reqst_user_ipaddr IS '신청사용자IP주소';

CREATE TABLE oco.oco10131 (
  athrty_reqst_seq    BIGINT       NOT NULL, -- 권한신청순번
  athrty_reqst_op_dtm  VARCHAR(14)  NOT NULL, -- 권한신청처리일시
  athrty_reqst_sts_cd   VARCHAR(1)   NOT NULL, -- 권한신청상태코드
  last_chngr_id        VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시D
  settl_userid          VARCHAR(10), -- 결제사용자ID
  settl_user_ipaddr     VARCHAR(16), -- 결제사용자IP주소
  gvbk_reson_cntnt      VARCHAR(1000), -- 반려사유내용
  PRIMARY KEY (athrty_reqst_seq, athrty_reqst_op_dtm, athrty_reqst_sts_cd)
);
COMMENT ON COLUMN oco.oco10131.athrty_reqst_seq IS '권한신청순번';
COMMENT ON COLUMN oco.oco10131.athrty_reqst_op_dtm IS '권한신청처리일시';
COMMENT ON COLUMN oco.oco10131.athrty_reqst_sts_cd IS '권한신청상태코드';
COMMENT ON COLUMN oco.oco10131.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10131.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10131.settl_userid IS '결제사용자ID';
COMMENT ON COLUMN oco.oco10131.settl_user_ipaddr IS '결제사용자IP주소';
COMMENT ON COLUMN oco.oco10131.gvbk_reson_cntnt IS '반려사유내용';

CREATE TABLE oco.oco10210 (
  menu_id            VARCHAR(12)  NOT NULL, -- 메뉴ID
  last_chngr_id      VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd     TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  chrg_task_group_cd VARCHAR(2)   NOT NULL DEFAULT '99', -- 담당업무그룹코드
  menu_type_cd       VARCHAR(1), -- 메뉴유형코드
  scren_excut_cl_cd  VARCHAR(1), -- 화면실행구분코드
  menu_nm            VARCHAR(100), -- 메뉴명
  menu_desc          VARCHAR(500), -- 메뉴설명
  menu_step_val      INTEGER      NOT NULL DEFAULT 0, -- 메뉴단계값
  sort_seqn          INTEGER      DEFAULT 0, -- 정렬순서
  use_yn             VARCHAR(1)   NOT NULL DEFAULT 'Y', -- 사용여부
  menu_expse_yn      VARCHAR(1)   NOT NULL DEFAULT 'Y', -- 메뉴노출여부
  super_menu_id      VARCHAR(12), -- 상위메뉴ID
  scren_id           VARCHAR(10), -- 화면ID
  linka_systm_tag_cntnt VARCHAR(500), -- 연동시스템태그내용
  PRIMARY KEY (menu_id) -- 메뉴ID
);
CREATE INDEX oco10210_n1 ON oco.oco10210 (scren_id); -- 화면ID


CREATE TABLE oco.oco10220 (
  scren_id VARCHAR(10) NOT NULL,
  last_chngr_id VARCHAR(10) NOT NULL,
  last_chng_dtmd TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  chrg_task_group_cd VARCHAR(2) NOT NULL DEFAULT '99',
  scren_nm VARCHAR(100) DEFAULT NULL,
  scren_desc VARCHAR(100) DEFAULT NULL,
  scren_urladdr VARCHAR(100) DEFAULT NULL,
  use_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
  scren_cl_cd VARCHAR(1) DEFAULT NULL,
  scren_size_cl_cd VARCHAR(1) DEFAULT NULL,
  scren_width_size INTEGER DEFAULT NULL,
  scren_vrtln_size INTEGER DEFAULT NULL,
  scren_start_top_codn INTEGER DEFAULT NULL,
  scren_start_left_codn INTEGER DEFAULT NULL,
  PRIMARY KEY (scren_id)
);

-- 테이블 및 컬럼 설명 추가
COMMENT ON TABLE oco.oco10220 IS '공통_화면기본';
COMMENT ON COLUMN oco.oco10220.scren_id IS '화면ID';
COMMENT ON COLUMN oco.oco10220.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10220.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10220.chrg_task_group_cd IS '담당업무그룹코드';
COMMENT ON COLUMN oco.oco10220.scren_nm IS '화면명';
COMMENT ON COLUMN oco.oco10220.scren_desc IS '화면설명';
COMMENT ON COLUMN oco.oco10220.scren_urladdr IS '화면URL주소';
COMMENT ON COLUMN oco.oco10220.use_yn IS '사용여부';
COMMENT ON COLUMN oco.oco10220.scren_cl_cd IS '화면구분코드';
COMMENT ON COLUMN oco.oco10220.scren_size_cl_cd IS '화면크기구분코드';
COMMENT ON COLUMN oco.oco10220.scren_width_size IS '화면가로크기';
COMMENT ON COLUMN oco.oco10220.scren_vrtln_size IS '화면세로크기';
COMMENT ON COLUMN oco.oco10220.scren_start_top_codn IS '화면시작상단좌표';
COMMENT ON COLUMN oco.oco10220.scren_start_left_codn IS '화면시작좌측좌표';

CREATE TABLE oco.oco10230 (
  scren_id VARCHAR(10) NOT NULL,
  bttn_id VARCHAR(30) NOT NULL,
  last_chngr_id VARCHAR(10) NOT NULL,
  last_chng_dtmd TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  bttn_nm VARCHAR(100) DEFAULT NULL,
  bttn_desc VARCHAR(500) DEFAULT NULL,
  use_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
  api_id INTEGER DEFAULT NULL,
  crud_cl_cd VARCHAR(1) NOT NULL DEFAULT 'R',
  PRIMARY KEY (scren_id, bttn_id)
);

-- 테이블 및 컬럼 설명 추가
COMMENT ON TABLE oco.oco10230 IS '공통_화면버튼기본';
COMMENT ON COLUMN oco.oco10230.scren_id IS '화면ID';
COMMENT ON COLUMN oco.oco10230.bttn_id IS '버튼ID';
COMMENT ON COLUMN oco.oco10230.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10230.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10230.bttn_nm IS '버튼명';
COMMENT ON COLUMN oco.oco10230.bttn_desc IS '버튼설명';
COMMENT ON COLUMN oco.oco10230.use_yn IS '사용여부';
COMMENT ON COLUMN oco.oco10230.api_id IS 'APIID';
COMMENT ON COLUMN oco.oco10230.crud_cl_cd IS 'CRUD구분코드';

CREATE TABLE oco.oco10240 (
  linka_systm_nm VARCHAR(10) NOT NULL,
  last_chngr_id VARCHAR(10) NOT NULL,
  last_chng_dtmd TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  fst_regr_id VARCHAR(10) NOT NULL,
  fst_reg_dtmd TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  conn_psbty_yn VARCHAR(1) NOT NULL DEFAULT 'N',
  PRIMARY KEY (linka_systm_nm)
);

-- 테이블 및 컬럼 설명 추가
COMMENT ON TABLE oco.oco10240 IS '인터페이스_연동시스템절체기준';
COMMENT ON COLUMN oco.oco10240.linka_systm_nm IS '연동시스템명';
COMMENT ON COLUMN oco.oco10240.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco10240.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco10240.fst_regr_id IS '최초등록자ID';
COMMENT ON COLUMN oco.oco10240.fst_reg_dtmd IS '최초등록일시D';
COMMENT ON COLUMN oco.oco10240.conn_psbty_yn IS '접속가능여부';

CREATE TABLE oco.oco20100 (
  cmmn_cd VARCHAR(30) NOT NULL,
  last_chngr_id VARCHAR(10) NOT NULL,
  last_chng_dtmd TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  chrg_task_group_cd VARCHAR(2) NOT NULL DEFAULT '99',
  cmmn_cd_nm VARCHAR(50) NOT NULL,
  cmmn_cd_desc VARCHAR(100) DEFAULT NULL,
  cmmn_cd_val_lenth INTEGER DEFAULT 0,
  use_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
  refrn_attr_nm1 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm2 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm3 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm4 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm5 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm6 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm7 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm8 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm9 VARCHAR(200) DEFAULT NULL,
  refrn_attr_nm10 VARCHAR(200) DEFAULT NULL,
  befo_cmmn_type_cd VARCHAR(6) DEFAULT NULL,
  befo_cmmn_group_cd VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (cmmn_cd)
);

-- 테이블 및 컬럼 설명 추가
COMMENT ON TABLE oco.oco20100 IS '공통_공통코드';
COMMENT ON COLUMN oco.oco20100.cmmn_cd IS '공통코드';
COMMENT ON COLUMN oco.oco20100.last_chngr_id IS '최종변경자ID';
COMMENT ON COLUMN oco.oco20100.last_chng_dtmd IS '최종변경일시D';
COMMENT ON COLUMN oco.oco20100.chrg_task_group_cd IS '담당업무그룹코드';
COMMENT ON COLUMN oco.oco20100.cmmn_cd_nm IS '공통코드명';
COMMENT ON COLUMN oco.oco20100.cmmn_cd_desc IS '공통코드설명';
COMMENT ON COLUMN oco.oco20100.cmmn_cd_val_lenth IS '공통코드값길이';
COMMENT ON COLUMN oco.oco20100.use_yn IS '사용여부';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm1 IS '참조속성명1';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm2 IS '참조속성명2';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm3 IS '참조속성명3';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm4 IS '참조속성명4';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm5 IS '참조속성명5';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm6 IS '참조속성명6';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm7 IS '참조속성명7';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm8 IS '참조속성명8';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm9 IS '참조속성명9';
COMMENT ON COLUMN oco.oco20100.refrn_attr_nm10 IS '참조속성명10';
COMMENT ON COLUMN oco.oco20100.befo_cmmn_type_cd IS '이전공통유형코드';
COMMENT ON COLUMN oco.oco20100.befo_cmmn_group_cd IS '이전공통그룹코드';

CREATE TABLE oco.oco20101 (
  cmmn_cd            VARCHAR(30)  NOT NULL, -- 공통코드
  cmmn_cd_val        VARCHAR(40)  NOT NULL, -- 공통코드값
  last_chngr_id       VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  cmmn_cd_val_nm      VARCHAR(100) NOT NULL, -- 공통코드값명
  cmmn_cd_val_desc    VARCHAR(300), -- 공통코드값설명
  sort_seqn           INTEGER      DEFAULT 0, -- 정렬순서
  use_yn             VARCHAR(1)   NOT NULL DEFAULT 'Y', -- 사용여부
  refrn_attr_val1     VARCHAR(100), -- 참조속성값1
  refrn_attr_val2     VARCHAR(100), -- 참조속성값2
  refrn_attr_val3     VARCHAR(100), -- 참조속성값3
  refrn_attr_val4     VARCHAR(100), -- 참조속성값4
  refrn_attr_val5     VARCHAR(100), -- 참조속성값5
  refrn_attr_val6     VARCHAR(100), -- 참조속성값6
  refrn_attr_val7     VARCHAR(100), -- 참조속성값7
  refrn_attr_val8     VARCHAR(100), -- 참조속성값8
  refrn_attr_val9     VARCHAR(100), -- 참조속성값9
  refrn_attr_val10    VARCHAR(100), -- 참조속성값10
  super_cmmn_cd       VARCHAR(30), -- 상위공통코드
  super_cmmn_cd_val   VARCHAR(30), -- 상위공통코드값
  PRIMARY KEY (cmmn_cd, cmmn_cd_val) -- 공통코드, 공통코드값
);
CREATE INDEX oco20101_n1 ON oco.oco20101 (refrn_attr_val1, cmmn_cd_val); -- 참조속성값1, 공통코드값


CREATE TABLE oco.oco40110 (
  api_id             INTEGER      GENERATED ALWAYS AS IDENTITY,
  last_chngr_id       VARCHAR(10)  NOT NULL,
  last_chng_dtmd      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  apro_group_id        INTEGER, -- 응용프로그램그룹ID
  api_nm             VARCHAR(100), -- API명
  api_desc           VARCHAR(200), -- API설명
  api_loc_urladdr    VARCHAR(300), -- API위치URL주소
  htt_method_val     VARCHAR(10), -- HTT메소드값
  api_req_cntnt      TEXT, -- API요청내용
  api_resp_cntnt      TEXT, -- API응답내용
  use_yn             VARCHAR(1)   NOT NULL DEFAULT 'Y',
  PRIMARY KEY (api_id)
);
CREATE INDEX oco40110_n1 ON oco.oco40110 (htt_method_val, api_loc_urladdr);

CREATE TABLE oco.oco40111 (
  api_exect_dtl_seq   BIGINT       GENERATED ALWAYS AS IDENTITY, -- API수행내역순번
  api_exect_start_dtmt TIMESTAMP    NOT NULL, -- API수행시작일시
  last_chngr_id         VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  api_resp_time          INTEGER, -- API응답시간
  api_id                 INTEGER      NOT NULL,
  api_resp_sts_val       INTEGER, -- API응답상태값
  err_cntnt             TEXT, -- 오류내용
  api_exect_userid       VARCHAR(10), -- API수행사용자ID
  conn_ipaddr            VARCHAR(16), -- 접속IP주소
  PRIMARY KEY (api_exect_dtl_seq, api_exect_start_dtmt)
);
CREATE INDEX oco40111_n2 ON oco.oco40111 (api_id); -- APIID
CREATE INDEX oco40111_n1 ON oco.oco40111 (api_exect_start_dtmt, api_resp_sts_val); -- API수행시작일시, API응답상태값

CREATE TABLE oco.oco50100 (
  empno              VARCHAR(8)   NOT NULL, -- 사원번호
  last_chngr_id       VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  emp_krn_nm          VARCHAR(30), -- 사원한글명
  emp_englnm          VARCHAR(20)  NOT NULL, -- 사원영문이름
  emp_engfnm          VARCHAR(20), -- 사원영문이름
  deptcd             VARCHAR(6)   NOT NULL,
  bthdy              VARCHAR(8), -- 생년월일
  owhm_phno          VARCHAR(14)  NOT NULL,
  mphno              VARCHAR(14), -- 휴대전화번호
  group_entcp_dt     VARCHAR(8)   NOT NULL,
  tcom_entcp_dt      VARCHAR(8)   NOT NULL,
  curtp_oford_dt     VARCHAR(8)   NOT NULL,
  curtp_oford_cd      VARCHAR(3)   NOT NULL,
  resg_dt             VARCHAR(8), -- 퇴사일자
  com_cl_cd           VARCHAR(1)   NOT NULL,
  clofp_cd            VARCHAR(3)   NOT NULL,
  reofo_cd            VARCHAR(3)   NOT NULL,
  jgp_cd              VARCHAR(1)   NOT NULL,
  vctn_cd             VARCHAR(3), -- 직능코드
  teambr_cl_cd        VARCHAR(3)   NOT NULL,
  clofp_nm            VARCHAR(20)  NOT NULL,
  jgp_nm              VARCHAR(20)  NOT NULL,
  vctn_nm             VARCHAR(20)  NOT NULL,
  emailaddr          VARCHAR(100) NOT NULL,
  befo_empno          VARCHAR(8), -- 이전사원번호
  PRIMARY KEY (empno)
);
CREATE INDEX oco50100_n1 ON oco.oco50100 (jgp_nm); -- 직급명
CREATE INDEX oco50100_n2 ON oco.oco50100 (vctn_cd); -- 직능코드
CREATE INDEX oco50100_n3 ON oco.oco50100 (empno, deptcd); -- 사원번호, 부서코드
CREATE INDEX oco50100_n4 ON oco.oco50100 (empno, emp_krn_nm);

CREATE TABLE oco.oco50200 (
  deptcd             VARCHAR(6)   NOT NULL, -- 부서코드
  last_chngr_id       VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  mgmt_deptcd         VARCHAR(6), -- 관리부서코드
  dept_nm            VARCHAR(50), -- 부서명
  engsh_dept_nm       VARCHAR(50)  NOT NULL, -- 영상부서명
  engsh_dept_abbr_nm  VARCHAR(50)  NOT NULL, -- 영상부서약칭명
  bssmacd            VARCHAR(2)   NOT NULL, -- 업무코드
  super_deptcd        VARCHAR(6)   NOT NULL, -- 상위부서코드
  dept_grade_cd       VARCHAR(1)   NOT NULL, -- 부서등급코드
  dept_crat_dt        VARCHAR(8)   NOT NULL,
  dept_abol_dt        VARCHAR(8), -- 부서폐지일자
  femp_deptcd         VARCHAR(6), -- 상근부서코드
  whtax_bzpl_cd       VARCHAR(6)   NOT NULL, -- 세무법인코드
  sals_deptcd         VARCHAR(6)   NOT NULL, -- 영업부서코드
  inv_deptcd          VARCHAR(6)   NOT NULL, -- 입고부서코드
  invnt_wrhus_cd      VARCHAR(6)   NOT NULL, -- 재고위치코드
  vat_bzpl_cd         VARCHAR(6)   NOT NULL, -- 부가세법인코드
  zipcd              VARCHAR(6)   NOT NULL, -- 우편번호
  basic_addr         VARCHAR(200) NOT NULL, -- 기본주소
  detil_addr         VARCHAR(200) NOT NULL, -- 상세주소
  extsn_no           VARCHAR(4), -- 내선번호
  phno               VARCHAR(14)  NOT NULL, -- 전화번호
  fax_no             VARCHAR(14)  NOT NULL, -- 팩스번호
  bzno               VARCHAR(10)  NOT NULL, -- 사업자번호
  bzman_acq_dt       VARCHAR(8)   NOT NULL, -- 사업자취득일자
  txofc_cd           VARCHAR(3)   NOT NULL, -- 통신사업자코드
  txofc_nm           VARCHAR(20)  NOT NULL, -- 통신사업자명
  com_bscnd_nm       VARCHAR(30)  NOT NULL, -- 회사법인명
  com_itm_nm         VARCHAR(30)  NOT NULL, -- 회사품목명
  com_corp_nm        VARCHAR(50)  NOT NULL, -- 회사법인명
  use_yn             VARCHAR(1)   NOT NULL DEFAULT 'Y',
  ccntr_cd           VARCHAR(10), -- 코스트센터코드
  rprtt_nm           VARCHAR(30), -- 대표자명
  PRIMARY KEY (deptcd)
);
CREATE INDEX oco50200_n1 ON oco.oco50200 (mgmt_deptcd); -- 관리부서코드
CREATE INDEX oco50200_n2 ON oco.oco50200 (deptcd, super_deptcd); -- 부서코드, 상위부서코드


CREATE TABLE oco.oco30900 (
  atac_file_no        BIGINT       GENERATED ALWAYS AS IDENTITY, -- 첨부파일번호
  last_chngr_id        VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd       TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  atac_file_type_cd    VARCHAR(5)   NOT NULL, -- 첨부파일유형코드
  atac_file_task_cl_cd VARCHAR(3), -- 첨부파일업무구분코드
  atac_file_nm         VARCHAR(100) NOT NULL, -- 첨부파일명
  atac_file_size       BIGINT, -- 첨부파일크기
  atac_file_path_nm    VARCHAR(200) NOT NULL, -- 첨부파일경로명
  atac_file_loc_urladdr VARCHAR(100), -- 첨부파일위치URL주소
  atac_file_sts_cd     VARCHAR(1), -- 첨부파일상태코드
  PRIMARY KEY (atac_file_no)
);

CREATE TABLE oco.oco40100 (
  apro_group_id        INTEGER      GENERATED ALWAYS AS IDENTITY, -- 응용프로그램그룹ID
  last_chngr_id         VARCHAR(10)  NOT NULL, -- 최종변경자ID
  last_chng_dtmd        TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP, -- 최종변경일시
  apro_type_cl_cd       VARCHAR(1), -- 응용프로그램유형구분코드
  apro_task_cl_cd       VARCHAR(3), -- 응용프로그램업무구분코드
  apro_group_cl_nm      VARCHAR(30), -- 응용프로그램그룹구분명
  apro_group_desc       VARCHAR(1000), -- 응용프로그램그룹설명
  PRIMARY KEY (apro_group_id)
);

CREATE TABLE oco.oco10195 (
  sumr_dt            VARCHAR(8)   NOT NULL,
  menu_id            VARCHAR(255) NOT NULL,
  conn_qty           BIGINT,
  last_modified_by   VARCHAR(255),
  last_modified_at   TIMESTAMP,
  PRIMARY KEY (sumr_dt, menu_id)
);


CREATE TABLE oco.oco10190 (
  user_actvy_seq bigserial,                         -- 사용자 활동 순번 (자동 증가)
  user_actvy_dtm varchar(14) NOT NULL,              -- 사용자 활동 일시
  userid varchar(10) NOT NULL,                      -- 사용자 ID
  last_chngr_id varchar(10) NOT NULL,               -- 최종 변경자 ID
  last_chng_dtmd timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,  -- 최종 변경 일시
  user_actvy_type_cd varchar(3),                    -- 사용자 활동 유형 코드
  conn_ipaddr varchar(16),                          -- 접속 IP 주소
  scren_id varchar(10),                             -- 화면 ID
  systm_ctgry_cd varchar(3),                        -- 시스템 카테고리 코드
  dwnld_reson_cntnt varchar(200),                   -- 다운로드 사유 내용
  accss_token_val varchar(1000),                    -- 액세스 토큰 값
  refsh_token_val varchar(36),                      -- 리프레시 토큰 값
  PRIMARY KEY (user_actvy_seq, user_actvy_dtm, userid)
);

-- 테이블 및 컬럼 설명 추가
COMMENT ON TABLE oco.oco10190 IS '사용자 활동 로그';
COMMENT ON COLUMN oco.oco10190.user_actvy_seq IS '사용자 활동 순번';
COMMENT ON COLUMN oco.oco10190.user_actvy_dtm IS '사용자 활동 일시';
COMMENT ON COLUMN oco.oco10190.userid IS '사용자 ID';
COMMENT ON COLUMN oco.oco10190.last_chngr_id IS '최종 변경자 ID';
COMMENT ON COLUMN oco.oco10190.last_chng_dtmd IS '최종 변경 일시';
COMMENT ON COLUMN oco.oco10190.user_actvy_type_cd IS '사용자 활동 유형 코드';
COMMENT ON COLUMN oco.oco10190.conn_ipaddr IS '접속 IP 주소';
COMMENT ON COLUMN oco.oco10190.scren_id IS '화면 ID';
COMMENT ON COLUMN oco.oco10190.systm_ctgry_cd IS '시스템 카테고리 코드';
COMMENT ON COLUMN oco.oco10190.dwnld_reson_cntnt IS '다운로드 사유 내용';
COMMENT ON COLUMN oco.oco10190.accss_token_val IS '액세스 토큰 값';
COMMENT ON COLUMN oco.oco10190.refsh_token_val IS '리프레시 토큰 값';

-- 인덱스 생성
CREATE INDEX idx_oco10190_userid ON oco.oco10190(userid);
CREATE INDEX idx_oco10190_actvy_dtm ON oco.oco10190(user_actvy_dtm);
CREATE INDEX idx_oco10190_actvy_type ON oco.oco10190(user_actvy_type_cd);
CREATE INDEX idx_oco10190_scren ON oco.oco10190(scren_id);
CREATE INDEX idx_oco10190_ipaddr ON oco.oco10190(conn_ipaddr);
CREATE INDEX idx_oco10190_composite ON oco.oco10190(userid, user_actvy_dtm);
