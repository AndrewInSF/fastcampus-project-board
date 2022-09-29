package com.fastcampus.projectboard.repository;

import com.fastcampus.projectboard.config.JpaConfig;
import com.fastcampus.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRespositoryTest {

    private final ArticleRespository articleRespository;

    private final ArticleCommentRepository articleCommentRepository;

    public JpaRespositoryTest(@Autowired ArticleRespository articleRespository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRespository = articleRespository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        long previousCount = articleRespository.count();
        Article savedArticle = articleRespository.save(Article.of("new article", "new content", "#spring"));

        assertThat(articleRespository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("Update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        Article article = articleRespository.save(Article.of("new article", "new content", "#spring"));

        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        Article savedArticle = articleRespository.saveAndFlush(article);

        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }


    @DisplayName("Delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        Article article = articleRespository.save(Article.of("new article", "new content", "#spring"));
        assertThat(articleRespository.count()).isEqualTo(124);

        articleRespository.delete(article);
        assertThat(articleRespository.count()).isEqualTo(123);


    }
}
