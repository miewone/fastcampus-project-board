package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.Article;
import com.fastcampus.fastcampusprojectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>, // 기본 적으로 이 Entity 안에 있는 모든 필드에 검색 기능을 추가 해준다.
        QuerydslBinderCustomizer<QArticle>
{
    @Override
    default void customize(QuerydslBindings bindings, QArticle root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title,root.content,root.hashtag,root.createdAt,root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // like '${v}' 쿼리생성
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '${v}' 쿼리생성
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase); // like '${v}' 쿼리생성
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like '${v}' 쿼리생성
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '${v}' 쿼리생성
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '%${v}%' 쿼리생성
    }
}