package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.config.JpaConfig;
import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.*;


@DisplayName("JPA 연결 테스트")
@DataJpaTest
@Import(JpaConfig.class) // 생성,수정 날짜를 자동으로 기입해주는 EnableJpaAuditing 기능을 설정한 클래스 파일을 잡아주지 위해서 사용
class JpaArticleRepositoryTest {

    // @Autowired를 이용하여 필드 주입을 하여 사용을 많이들 한다.
    // Junit5, 최신형 Spring Boot를 사용하면 테스트에서도 생성자 주입 패턴 사용이 가능하다.
    // 이유는 @DataJpaTest 안에 들어가보면 모든 슬라이스 테스트가 가지고 있는 ExtendWidth spring Extendsion.class를 가지고 있기때문임.
    // spring Extendsion 에서 autoWired 어노테이션 기능이 들어가있다.
    // 그러해서 우리는 생성자 주입 패턴을 사용한다.
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaArticleRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine()
    {
        // Given

        // When
        List<Article> articles = articleRepository.findAll();

        // Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine()
    {
        // Given
        long previousCount = articleRepository.count();
        // When
        Article savedArticle = articleRepository.save(Article.of("new article","new content","#spring"));

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousCount+1);
    }
    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine()
    {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);
        // When
        Article savedArticle = articleRepository.saveAndFlush(article);
//        articleRepository.flush();

        // Then
        assertThat(savedArticle)
                .hasFieldOrPropertyWithValue("hashtag",updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine()
    {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);
//        articleRepository.flush();

        // Then
        assertThat(articleRepository.count())
                .isEqualTo(previousArticleCount-1);
        assertThat(articleCommentRepository.count())
                .isEqualTo(previousArticleCommentCount-deletedCommentsSize);

    }
}