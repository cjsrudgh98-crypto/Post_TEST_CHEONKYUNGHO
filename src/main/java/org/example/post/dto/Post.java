package org.example.post.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private int postNo;         // 글번호
    private String title;       // 제목
    private String writer;      // 작성자
    private String content;     // 내용
    private Date regDate;       // 등록일자
    private Date updDate;       // 수정일자

}
