# PostgreSQL Database Guide

이 문서는 AGS 운영관리 시스템의 PostgreSQL 데이터베이스 객체 생성 시 적용해야 할 가이드라인을 설명합니다.

## 스키마
- 사용할 스키마명은 `ags`이고 테이블 소유자는 `ags_admin`이며 `ags_developer`가 사용함

## 테이블 공통
- 반드시 코멘트를 부여함
- 반드시 PK를 가져야함
- 반드시 생성자, 생성일자, 수정자, 수정일자를 가져야함
- 테이블명은 단수 소문자로 생성함

## 속성 공통
- 모든속성은 반드시 코멘트를 부여함
- 속성명은 두단어 이상의 소문자로 생성하고 언더바(_)로 연결함
- 속성명은 최대한 중복되지 않고 명확하게 작성함
- 기본키는 SERIAL타입으로 생성하며 속성명끝에 테이블명_id를 붙여서 생성함
- 코드속성은 공통코드 테이블의 코드속성을 사용하고 NOT NULL로 설정함
- 일자는 timestamp타입으로 생성하고 속성명_dt를 붙여서 생성함
- 500자 이상의 문자열은 TEXT타입으로 생성함
- 문자열 기본타입은 VARCHAR를 사용하고 char는 사용하지 않음
- 숫자는 기본타입은 NUMERIC을 사용하고 정수는 INT타입을 사용함
- 논리타입은 사용하지 않음
- 문자열 속성은 반드시 최대길이를 지정함
- 숫자 속성은 반드시 최대길이를 지정함

## 인덱스
- 인덱스명은 테이블명_ix1로 생성하고 두번째는 테이블명_ix2로 생성함
- 유니크 인덱스는 테이블명_ux1로 생성하고 두번째는 테이블명_ux2로 생성함

## 테이블 생성시 참조키 제약조건 추가
- 테이블 생성시 참조키 제약조건 추가
- 참조키 제약조건 명은 테이블명_fk1로 생성하고 두번째는 테이블명_fk2로 생성함
- 참조키 제약조건은 반드시 코멘트를 부여함

## 트리거
- 기본적으로 사용하지 않음 필요시 사용
- 트리거명은 테이블명_tg1로 생성하고 두번째는 테이블명_tg2로 생성함
- 트리거는 반드시 코멘트를 부여함

## 프로시저
- 기본적으로 사용하지 않음 필요시 사용
- 프로시저명은 테이블명_prc1로 생성하고 두번째는 테이블명_prc2로 생성함
- 프로시저는 반드시 코멘트를 부여함

## 뷰
- 기본적으로 사용하지 않음 필요시 사용
- 뷰명은 테이블명_vw1로 생성하고 두번째는 테이블명_vw2로 생성함
- 뷰는 반드시 코멘트를 부여함

## 타입
- 타입은 사용하지 않음
- 타입은 반드시 공통코드 테이블의 코드그룹에 속하도록하고 공통코드 테이블에 등록하여 사용

## 시퀀스
- 시퀀스명은 테이블명_seq1로 생성하고 두번째는 테이블명_seq2로 생성함
- 시퀀스는 반드시 코멘트를 부여함

## 예시
```sql
-- 테이블 생성 예시
CREATE TABLE ags.example_table (
    example_id SERIAL PRIMARY KEY,
    example_name VARCHAR(100) NOT NULL,
    example_code VARCHAR(50) NOT NULL,
    example_desc TEXT,
    use_yn VARCHAR(1) NOT NULL DEFAULT 'Y',
    created_by VARCHAR(50) NOT NULL,
    created_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(50) NOT NULL,
    modified_dt TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT example_table_ux1 UNIQUE (example_code)
);

-- 테이블 코멘트
COMMENT ON TABLE ags.example_table IS '예시 테이블';

-- 컬럼 코멘트
COMMENT ON COLUMN ags.example_table.example_id IS '예시ID';
COMMENT ON COLUMN ags.example_table.example_name IS '예시명';
COMMENT ON COLUMN ags.example_table.example_code IS '예시코드';
COMMENT ON COLUMN ags.example_table.example_desc IS '예시설명';
COMMENT ON COLUMN ags.example_table.use_yn IS '사용여부';
COMMENT ON COLUMN ags.example_table.created_by IS '생성자';
COMMENT ON COLUMN ags.example_table.created_dt IS '생성일시';
COMMENT ON COLUMN ags.example_table.modified_by IS '수정자';
COMMENT ON COLUMN ags.example_table.modified_dt IS '수정일시';

-- 인덱스 생성
CREATE INDEX example_table_ix1 ON ags.example_table (example_code);
CREATE INDEX example_table_ix2 ON ags.example_table (use_yn);
``` 