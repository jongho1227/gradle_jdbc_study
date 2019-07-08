-- 프로그램언어과제
DROP SCHEMA IF EXISTS ncs_erp;

-- 프로그램언어과제
CREATE SCHEMA ncs_erp;

-- 부서
CREATE TABLE ncs_erp.department (
	deptno   INT(11)  NOT NULL COMMENT '부서번호', -- 부서번호
	deptname CHAR(20) NOT NULL COMMENT '부서명', -- 부서명
	floor    INT(11)  NULL     DEFAULT 1 COMMENT '위치' -- 위치
)
COMMENT '부서';

-- 부서
ALTER TABLE ncs_erp.department
	ADD CONSTRAINT
		PRIMARY KEY (
			deptno -- 부서번호
		);

-- 사원
CREATE TABLE ncs_erp.employee (
	empno     INT(11)     NOT NULL COMMENT '사원번호', -- 사원번호
	empname   VARCHAR(20) NOT NULL COMMENT '사원명', -- 사원명
	title     int(11)     NULL     COMMENT '직책', -- 직책
	manager   INT(11)     NULL     COMMENT '직속상사', -- 직속상사
	salary    INT(11)     NULL     COMMENT '급여', -- 급여
	gender    TINYINT(1)  NULL     COMMENT '성별', -- 성별
	dno       INT(11)     NULL     COMMENT '부서', -- 부서
	hire_date DATE        NULL     COMMENT '입사일' -- 입사일
)
COMMENT '사원';

-- 사원
ALTER TABLE ncs_erp.employee
	ADD CONSTRAINT
		PRIMARY KEY (
			empno -- 사원번호
		);

-- 직책
CREATE TABLE ncs_erp.title (
	tno   int(11)     NOT NULL COMMENT '직책번호', -- 직책번호
	tname VARCHAR(20) NOT NULL COMMENT '직책명' -- 직책명
)
COMMENT '직책';

-- 직책
ALTER TABLE ncs_erp.title
	ADD CONSTRAINT PK_title -- 직책 기본키
		PRIMARY KEY (
			tno -- 직책번호
		);

-- 사원
ALTER TABLE ncs_erp.employee
	ADD CONSTRAINT fk_emp_dno -- fk_emp_dno
		FOREIGN KEY (
			dno -- 부서
		)
		REFERENCES ncs_erp.department ( -- 부서
			deptno -- 부서번호
		),
	ADD INDEX fk_emp_dno (
		dno -- 부서
	);

-- 사원
ALTER TABLE ncs_erp.employee
	ADD CONSTRAINT fk_emp_mgn -- fk_emp_mgn
		FOREIGN KEY (
			manager -- 직속상사
		)
		REFERENCES ncs_erp.employee ( -- 사원
			empno -- 사원번호
		),
	ADD INDEX fk_emp_mgn (
		manager -- 직속상사
	);

-- 사원
ALTER TABLE ncs_erp.employee
	ADD CONSTRAINT FK_title_TO_employee -- 직책 -> 사원
		FOREIGN KEY (
			title -- 직책
		)
		REFERENCES ncs_erp.title ( -- 직책
			tno -- 직책번호
		);



-- 계정과 권한부여
grant all privileges 
on ncs_erp.* 
to 'user_ncs_erp'@'localhost'
identified by 'rootroot';