package org.example.post.service;

import java.util.List;

import org.example.post.dto.Page;
import org.example.post.dto.Post;
import org.example.post.mapper.PostMapper;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    @Override
    public List<Post> list() throws Exception {
        return postMapper.list();
    }

    @Override
    public List<Post> page(Page page) throws Exception {
        // 전체 게시글 수를 반영하여 페이징 정보 계산
        long total = postMapper.count();
        page.setTotal(total);
        return postMapper.page(page);
    }

    @Override
    public Post select(int postNo) throws Exception {
        return postMapper.select(postNo);
    }

    @Override
    public boolean insert(Post post) throws Exception {
        return postMapper.insert(post) > 0;
    }

    @Override
    public boolean update(Post post) throws Exception {
        return postMapper.update(post) > 0;
    }

    @Override
    public boolean delete(int postNo) throws Exception {
        return postMapper.delete(postNo) > 0;
    }

}
