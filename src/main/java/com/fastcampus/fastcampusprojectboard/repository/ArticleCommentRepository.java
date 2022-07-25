package com.fastcampus.fastcampusprojectboard.repository;

import com.fastcampus.fastcampusprojectboard.domain.ArticleComment;
import com.fastcampus.fastcampusprojectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment,Long>,
        QuerydslPredicateExecutor<ArticleComment>, // 기본 적으로 이 Entity 안에 있는 모든 필드에 검색 기능을 추가 해준다.
        QuerydslBinderCustomizer<QArticleComment>
{
    @Override
    default void customize(QuerydslBindings bindings, QArticleComment root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content,root.createdAt,root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '${v}' 쿼리생성
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // like '${v}' 쿼리생성
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase); // like '${v}' 쿼리생성
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); // like '%${v}%' 쿼리생성
    }
}
