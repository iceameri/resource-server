package com.jw.resourceserver.entity.resource;

import com.jw.resourceserver.entity.BaseTimeEntity;
import com.jw.resourceserver.entity.DBConstants;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = DBConstants.Tables.COMMENTS)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DBConstants.Columns.ID, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DBConstants.Columns.BOARD_ID, nullable = false)
    @Setter
    private Boards boards;

    @Column(name = DBConstants.Columns.CONTENT, nullable = false, columnDefinition = DBConstants.ColumnDefinitions.NVARCHAR_2000)
    private String content;

    // 대댓글 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = DBConstants.Columns.PARENT_ID)
    @Setter
    private Comments parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("created ASC")
    private List<Comments> replies = new ArrayList<>();

    @Column(name = DBConstants.Columns.IS_SECRET, nullable = false)
    private Boolean isSecret = false;

    @Builder
    private Comments(final Boards boards,
                     final String content,
                     final Comments parent,
                     final Boolean isSecret) {
        this.validateContent(content);
        this.validateParentDepth(parent);

        this.boards = boards;
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

    public void addReply(final Comments reply) {
        reply.setParent(this);
        reply.setBoards(this.boards);
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
    public Comments getRootComment() {
        return parent == null ? this : parent;
    }

    private void validateContent(final String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다.");
        }
        if (content.length() > 1000) {
            throw new IllegalArgumentException("댓글은 1000자를 초과할 수 없습니다.");
        }
    }

    private void validateParentDepth(final Comments parent) {
        if (parent != null && parent.isReply()) {
            throw new IllegalArgumentException("대댓글에는 답글을 달 수 없습니다.");
        }
    }
}