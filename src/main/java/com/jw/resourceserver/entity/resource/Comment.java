package com.jw.resourceserver.entity.resource;

import com.jw.resourceserver.entity.BaseTimeEntity;
import com.jw.resourceserver.entity.DatabaseConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = DatabaseConstants.Tables.COMMENTS)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 게시판과의 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DatabaseConstants.Columns.BOARD_ID, nullable = false)
    @Setter
    private Board board;

    @Column(name = DatabaseConstants.Columns.CONTENT, nullable = false, length = 1000)
    private String content;

    // 대댓글 관계 (Self-referencing)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @Setter
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("created ASC")
    private List<Comment> replies = new ArrayList<>();

    @Column(name = DatabaseConstants.Columns.IS_SECRET, nullable = false)
    private Boolean isSecret = false;

    @Builder
    private Comment(final Board board,
                    final String content,
                    final Comment parent,
                    final Boolean isSecret) {
        validateContent(content);
        validateParentDepth(parent);

        this.board = board;
        this.content = content;
        this.parent = parent;
        this.isSecret = isSecret != null ? isSecret : false;
        this.replies = new ArrayList<>();
    }

    public boolean isReply() {
        return parent != null;
    }

    public boolean isParentComment() {
        return parent == null;
    }

    public void addReply(Comment reply) {
        reply.setParent(this);
        reply.setBoard(this.board);
        this.replies.add(reply);
    }

    public int getReplyCount() {
        return replies.size();
    }

    // depth 체크 (대댓글은 무조건 depth 1)
    public int getDepth() {
        return parent == null ? 0 : 1;
    }

    // 최상위 댓글 조회
    public Comment getRootComment() {
        return parent == null ? this : parent;
    }

    private void validateContent(String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다.");
        }
        if (content.length() > 1000) {
            throw new IllegalArgumentException("댓글은 1000자를 초과할 수 없습니다.");
        }
    }

    private void validateParentDepth(Comment parent) {
        if (parent != null && parent.isReply()) {
            throw new IllegalArgumentException("대댓글에는 답글을 달 수 없습니다.");
        }
    }
}