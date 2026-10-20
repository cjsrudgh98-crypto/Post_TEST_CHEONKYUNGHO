package org.example.post;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.example.post.dto.Page;
import org.example.post.dto.Post;
import org.example.post.mapper.PostMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PostApplicationTests {

    @Autowired MockMvc mvc;
    @Autowired PostMapper postMapper;
    @Autowired JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM post");
    }

    @Test
    void crudFlow() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk())
           .andExpect(content().string(org.hamcrest.Matchers.containsString("/Post/list")));

        // 등록
        mvc.perform(post("/Post/insert").param("title", "제목1").param("writer", "작성자").param("content", "내용1"))
           .andExpect(redirectedUrl("/Post/list"));
        int postNo = postMapper.list().get(0).getPostNo();

        // 목록
        mvc.perform(get("/Post/list")).andExpect(status().isOk()).andExpect(view().name("Post/list"))
           .andExpect(content().string(org.hamcrest.Matchers.containsString("/Post/read/" + postNo)))
           .andExpect(content().string(org.hamcrest.Matchers.containsString("/Post/insert")));

        // 조회
        mvc.perform(get("/Post/read/" + postNo)).andExpect(status().isOk())
           .andExpect(content().string(org.hamcrest.Matchers.containsString("내용1")));

        // 등록 화면 / 수정 화면
        mvc.perform(get("/Post/insert")).andExpect(status().isOk());
        mvc.perform(get("/Post/update/" + postNo)).andExpect(status().isOk())
           .andExpect(content().string(org.hamcrest.Matchers.containsString("제목1")));

        // 수정
        mvc.perform(post("/Post/update").param("postNo", String.valueOf(postNo))
                .param("title", "수정제목").param("writer", "작성자").param("content", "수정내용"))
           .andExpect(redirectedUrl("/Post/read/" + postNo));
        assertThat(postMapper.select(postNo).getTitle()).isEqualTo("수정제목");

        // 삭제 (AJAX)
        mvc.perform(delete("/Post/" + postNo)).andExpect(status().isOk())
           .andExpect(jsonPath("$.result").value(true));
        assertThat(postMapper.select(postNo)).isNull();
    }

    @Test
    void paging() throws Exception {
        for (int i = 1; i <= 25; i++) {
            postMapper.insert(Post.builder().title("글" + i).writer("w").content("c").build());
        }

        Page page = new Page();
        page.setPage(3);
        page.setTotal(postMapper.count());
        List<Post> list = postMapper.page(page);
        assertThat(page.getLast()).isEqualTo(3);
        assertThat(list).hasSize(5);
        assertThat(list.get(0).getTitle()).isEqualTo("글5");

        mvc.perform(get("/Post/list").param("page", "2")).andExpect(status().isOk())
           .andExpect(content().string(org.hamcrest.Matchers.containsString("글15")))
           .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("글16<"))));
    }

}
