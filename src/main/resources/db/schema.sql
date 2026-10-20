-- 게시판 DB / 테이블 생성 (MySQL)
CREATE DATABASE IF NOT EXISTS post_test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE post_test;

DROP TABLE IF EXISTS post;

CREATE TABLE post (
    post_no   INT          NOT NULL AUTO_INCREMENT COMMENT '글번호',
    title     VARCHAR(200) NOT NULL                COMMENT '제목',
    writer    VARCHAR(100) NOT NULL                COMMENT '작성자',
    content   TEXT                                 COMMENT '내용',
    reg_date  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP                             COMMENT '등록일자',
    upd_date  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일자',
    PRIMARY KEY (post_no)
) COMMENT '게시판';

-- 샘플 데이터
INSERT INTO post (title, writer, content) VALUES ('첫 번째 게시글', '천경호', '첫 번째 게시글 내용입니다.');
INSERT INTO post (title, writer, content) VALUES ('두 번째 게시글', '천경호', '두 번째 게시글 내용입니다.');
INSERT INTO post (title, writer, content) VALUES ('세 번째 게시글', '천경호', '세 번째 게시글 내용입니다.');
