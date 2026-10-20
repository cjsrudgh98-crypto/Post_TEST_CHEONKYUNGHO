package org.example.post.service;

import java.util.List;

import org.example.post.dto.Page;
import org.example.post.dto.Post;

public interface PostService {

    // 게시글 목록
    public List<Post> list() throws Exception;
    // 게시글 목록 - [페이징]
    public List<Post> page(Page page) throws Exception;
    // 게시글 조회
    public Post select(int postNo) throws Exception;
    // 게시글 등록
    public boolean insert(Post post) throws Exception;
    // 게시글 수정
    public boolean update(Post post) throws Exception;
    // 게시글 삭제
    public boolean delete(int postNo) throws Exception;

}
