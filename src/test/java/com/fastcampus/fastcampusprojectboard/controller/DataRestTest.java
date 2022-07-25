package com.fastcampus.fastcampusprojectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest
@Disabled("Spring Data REST 통합테스트는 불필요하므로 제외시킴")
@Transactional
@DisplayName("Data Rest - API 테스트")
@AutoConfigureMockMvc // SpringBootTest만으로는 MovcMvc 테스트를 감지할 수 없어 AutoconfigureMockmvc Annotation을 달아줘서 감지하게함.
@SpringBootTest
public class DataRestTest {

    private final MockMvc mvc;


    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[api] 게시글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticles_thenReturnsArticlesJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
//                .andDo(print());
//        존재하는 경로이지만 404 Error가 나오면서 테스트 검증이 실패함. 웹 mvc 테스트는 슬라이스 테스트임 컨트롤러 웨 빌드를 로드하지 않는다.
//        컨트롤러와 연관된것들 최소한을 로드함. 이 테스트를 인테그레이션으로 바꾸는 방법이 존재함.
//        아래와 같이 이 테스트를 진행하면 쿼리도 같이 나오는데 이건 디비에 영향을 주는 것을 보여주므로 @Trensactional 을 추가하여 영향을 최소한을 하자
//        select
//        article0_.id as id1_0_,
//                article0_.created_at as created_2_0_,
//        article0_.created_by as created_3_0_,
//                article0_.modified_at as modified4_0_,
//        article0_.modified_by as modified5_0_,
//                article0_.content as content6_0_,
//        article0_.hashtag as hashtag7_0_,
//                article0_.title as title8_0_
//        from
//        article article0_ limit ?
//                Hibernate:
//                select
//        count(article0_.id) as col_0_0_
//        from
//        article article0_
    }
    @DisplayName("[api] 게시글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticle_thenReturnsArticleJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 게시글 -> 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleCommentsFromArticle_thenReturnsArticleCommentsJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articles/1/articleComments"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 리스트 조회")
    @Test
    void givenNothing_whenRequestingArticleComments_thenReturnsArticleCommentsJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articleComments"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("[api] 댓글 단건 조회")
    @Test
    void givenNothing_whenRequestingArticleComment_thenReturnsArticleCommentJsonResponse() throws Exception {
        // Given

        // When & Then
        mvc.perform(get("/api/articleComments/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.valueOf("application/hal+json")));
    }
}
