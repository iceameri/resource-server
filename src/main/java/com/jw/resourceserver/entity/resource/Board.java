package com.jw.resourceserver.entity.resource;

import com.jw.resourceserver.entity.BaseTimeEntity;
import com.jw.resourceserver.entity.DatabaseConstants;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = DatabaseConstants.Tables.BOARDS)
@Getter
@NoArgsConstructor
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseConstants.Columns.BOARD_TYPE, nullable = false, length = 20)
    private BoardType boardType;

    @Column(name = DatabaseConstants.Columns.TITLE, nullable = false, length = 200)
    private String title;

    @Lob
    @Column(name = DatabaseConstants.Columns.CONTENT, nullable = false)
    private String content;

    @Column(name = DatabaseConstants.Columns.AUTHOR, nullable = false, length = 50)
    private String author;

    @Column(name = DatabaseConstants.Columns.VIEW_COUNT, nullable = false)
    private Long viewCount = 0L;

    @Column(name = DatabaseConstants.Columns.IS_PINNED, nullable = false)
    private Boolean isPinned = false;

    @Column(name = DatabaseConstants.Columns.IS_SECRET, nullable = false)
    private Boolean isSecret = false;

    // 댓글과의 관계 (양방향)
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // 첨부파일 (필요시)
    @Column(name = DatabaseConstants.Columns.ATTACHMENT_URL, length = 500)
    private String attachmentUrl;

    @Column(name = DatabaseConstants.Columns.ATTACHMENT_NAME, length = 200)
    private String attachmentName;

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void pin() {
        this.isPinned = true;
    }

    public void unpin() {
        this.isPinned = false;
    }

    public int getCommentCount() {
        return comments.size();
    }

    public int getReplyCount() {
        return (int) comments.stream()
                .mapToLong(comment -> comment.getReplies().size())
                .sum();
    }
}
