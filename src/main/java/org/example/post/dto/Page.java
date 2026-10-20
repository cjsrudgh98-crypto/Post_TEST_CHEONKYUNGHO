package org.example.post.dto;

import lombok.Getter;
import lombok.ToString;

/**
 * 페이징 처리 DTO
 */
@Getter
@ToString
public class Page {

    private static final int DEFAULT_SIZE = 10;     // 페이지당 게시글 수
    private static final int DEFAULT_COUNT = 10;    // 노출 페이지 수

    private int page;       // 현재 페이지 번호
    private int size;       // 페이지당 게시글 수
    private int count;      // 노출 페이지 수
    private long total;     // 전체 게시글 수

    private int start;      // 시작 페이지 번호
    private int end;        // 끝 페이지 번호
    private int first;      // 첫 페이지 번호
    private int last;       // 마지막 페이지 번호
    private int prev;       // 이전 페이지 번호
    private int next;       // 다음 페이지 번호

    private int index;      // 데이터 순서 번호 (OFFSET)

    public Page() {
        this(1, DEFAULT_SIZE, DEFAULT_COUNT, 0);
    }

    public Page(int page, int size, int count, long total) {
        this.page = page;
        this.size = size;
        this.count = count;
        this.total = total;
        calc();
    }

    public void setPage(int page) {
        this.page = page;
        calc();
    }

    public void setSize(int size) {
        this.size = size;
        calc();
    }

    public void setCount(int count) {
        this.count = count;
        calc();
    }

    public void setTotal(long total) {
        this.total = total;
        calc();
    }

    /**
     * 페이징 정보 계산
     */
    private void calc() {
        if (size <= 0) size = DEFAULT_SIZE;
        if (count <= 0) count = DEFAULT_COUNT;

        // 마지막 페이지 번호 (게시글이 없어도 1페이지)
        last = (int) Math.max(1, (total - 1) / size + 1);
        first = 1;

        // 현재 페이지 범위 보정 (전체 게시글 수가 정해진 후에만 마지막 페이지로 보정)
        if (page < 1) page = 1;
        if (total > 0 && page > last) page = last;

        // 시작/끝 페이지 번호
        start = ((page - 1) / count) * count + 1;
        end = Math.min(start + count - 1, last);

        // 이전/다음 페이지 번호
        prev = Math.max(page - 1, first);
        next = Math.min(page + 1, last);

        // 데이터 순서 번호 (OFFSET)
        index = (page - 1) * size;
    }

}
