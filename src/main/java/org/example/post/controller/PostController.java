package org.example.post.controller;

import java.util.List;
import java.util.Map;

import org.example.post.dto.Page;
import org.example.post.dto.Post;
import org.example.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/Post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 게시글 목록 - [페이징]
     * [GET] /Post/list?page=1&size=10
     */
    @GetMapping("/list")
    public String list(Model model, Page page) throws Exception {
        List<Post> list = postService.page(page);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return "Post/list";
    }

    /**
     * 게시글 조회
     * [GET] /Post/read/{postNo}
     */
    @GetMapping("/read/{postNo}")
    public String read(@PathVariable("postNo") int postNo, Model model) throws Exception {
        Post post = postService.select(postNo);
        if (post == null) {
            return "redirect:/Post/list";
        }
        model.addAttribute("post", post);
        return "Post/read";
    }

    /**
     * 게시글 등록 화면
     * [GET] /Post/insert
     */
    @GetMapping("/insert")
    public String insert() {
        return "Post/insert";
    }

    /**
     * 게시글 등록 처리
     * [POST] /Post/insert
     */
    @PostMapping("/insert")
    public String insertPro(Post post) throws Exception {
        boolean result = postService.insert(post);
        if (!result) {
            return "redirect:/Post/insert?error";
        }
        return "redirect:/Post/list";
    }

    /**
     * 게시글 수정 화면
     * [GET] /Post/update/{postNo}
     */
    @GetMapping("/update/{postNo}")
    public String update(@PathVariable("postNo") int postNo, Model model) throws Exception {
        Post post = postService.select(postNo);
        if (post == null) {
            return "redirect:/Post/list";
        }
        model.addAttribute("post", post);
        return "Post/update";
    }

    /**
     * 게시글 수정 처리
     * [POST] /Post/update
     */
    @PostMapping("/update")
    public String updatePro(Post post) throws Exception {
        boolean result = postService.update(post);
        if (!result) {
            return "redirect:/Post/update/" + post.getPostNo() + "?error";
        }
        return "redirect:/Post/read/" + post.getPostNo();
    }

    /**
     * 게시글 삭제 처리 - [AJAX]
     * [DELETE] /Post/{postNo}
     */
    @ResponseBody
    @DeleteMapping("/{postNo}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("postNo") int postNo) throws Exception {
        boolean result = postService.delete(postNo);
        log.info("게시글 삭제 - postNo : {}, result : {}", postNo, result);
        if (!result) {
            return ResponseEntity.badRequest().body(Map.of("result", false, "message", "게시글 삭제 실패"));
        }
        return ResponseEntity.ok(Map.of("result", true, "message", "게시글 삭제 성공"));
    }

}
