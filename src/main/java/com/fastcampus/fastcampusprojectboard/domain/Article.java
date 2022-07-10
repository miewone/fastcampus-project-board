package com.fastcampus.fastcampusprojectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 increment auto 사용하면 안됨 mysql는 identity 방식으로 되기 때문에
    private Long id;


    // setter을 필드 마다 거는 이유는 일부러 사용자가 특정 필드에 접근 불가능하도록 설정
    @Setter @Column(nullable = false)private String title;  // 제목
    @Setter @Column(nullable = false,length = 10000)private String content; // 본문

    @Setter private String hashtag; // 해시태그


    // @ToString.Exclude로 연결을 안끊는다면 설큘러 레퍼런싱(순환 참조) 이슈가 생길수가 있다.
    @ToString.Exclude
    @OrderBy("id")
    @OneToMany(mappedBy = "article",cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();

    @CreatedDate @Column(nullable=false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable=false,length=100) private String createdBy;    // 생성자
    @LastModifiedDate @Column(nullable=false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable=false,length=100) private String modifiedBy;  // 수정자

    // JPA Entity는 하이버넽이트 구현체를 사용할때 기본 생성자를 가지고 있어야한다.
    protected Article(){}

    private Article(String title,String content,String hashtag){
        this.title=title;
        this.content=content;
        this.hashtag=hashtag;
    }

    public static Article of(String title,String content,String hashtag){
        return new Article(title,content,hashtag);
    }
//    팩토리 메소드를 이용해서 사용할 수 있게 만듦.


//    새로만든 Entity 들이 영속화가 안되어 있다면 그 내용이 완전히 동일하더라도 각 각 다른 값으로 취급하겠다.
//    Entity를 데이터 베이스에 영속화하고 사용하는 환경에서 사용하는 두 엔티티가 같은지?? 에 대한 답을 주는데 밑 메소드
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article article)) return false;
        return id != null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
