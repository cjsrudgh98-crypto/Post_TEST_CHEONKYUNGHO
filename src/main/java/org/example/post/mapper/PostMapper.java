package org.example.post.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.example.post.dto.Page;
import org.example.post.dto.Post;

@Mapper
public interface PostMapper {

    // 게시글 목록
    public List<Post> list() throws Exception;
    // 게시글 목록 - [페이징]
    public List<Post> page(Page page) throws Exception;
    // 게시글 수
    public long count() throws Exception;
    // 게시글 조회
    public Post select(int postNo) throws Exception;
    // 게시글 등록
    public int insert(Post post) throws Exception;
    // 게시글 수정
    public int update(Post post) throws Exception;
    // 게시글 삭제
    public int delete(int postNo) throws Exception;

}
