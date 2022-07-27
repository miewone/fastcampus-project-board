package com.fastcampus.fastcampusprojectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class) // 테스트 되는 대상으로만 빈으로 잃어 드린다.
class ArticleControllerTest {
    private final MockMvc mvc;

    public ArticleControllerTest(@Autowired MockMvc mvc){
        this.mvc = mvc;
    }

//    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticlesView_thenReturnArticlesView() throws Exception {
           // Given

           // When & Then
        mvc.perform(get("/articles"))
                .andExpect(status().isOk()) // 상태 반환 값 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // 미디어 타입 검사
                .andExpect(view().name("articles/index")) // view name 검사
                .andExpect(model().attributeExists("articles")); // modelAttribute 맵에 articles라는 키가 있는지 검사
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleView_thenReturnArticleView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/1"))
                .andExpect(status().isOk()) // 상태 반환 값 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // 미디어 타입 검사
                .andExpect(view().name("articles/detail")) // view name 검사
                .andExpect(model().attributeExists("articles")) // modelAttribute 맵에 articles라는 키가 있는지 검사
                .andExpect(model().attributeExists("articleComments"));
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleSearchView_thenReturnArticleSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search"))
                .andExpect(status().isOk()) // 상태 반환 값 검사
                .andExpect(view().name("articles/search")) // view name 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)); // 미디어 타입 검사
    }

    @Disabled("구현 중")
    @DisplayName("[view][GET] 게시글 해시태그 검색 페이지 - 정상 호출")
    @Test
    public void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnArticleHashtagSearchView() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/articles/search-hashtag"))
                .andExpect(status().isOk()) // 상태 반환 값 검사
                .andExpect(view().name("articles/search-hashtags")) // view name 검사
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML)); // 미디어 타입 검사
    }
}