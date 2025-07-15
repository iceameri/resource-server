package com.jw.resourceserver.entity.resource;

import com.jw.resourceserver.entity.BaseTimeEntity;
import com.jw.resourceserver.entity.DatabaseConstants;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = DatabaseConstants.Tables.BOARDS, indexes = {
        @Index(name = DatabaseConstants.IndexNames.BOARDS_BOARD_TYPE, columnList = DatabaseConstants.Columns.BOARD_TYPE),
        @Index(name = DatabaseConstants.IndexNames.BOARDS_SEARCH, columnList = DatabaseConstants.IndexColumns.BOARDS_SEARCH_COLUMNS),
})
@Getter
@NoArgsConstructor
public class Boards extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DatabaseConstants.Columns.ID, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = DatabaseConstants.Columns.BOARD_TYPE, nullable = false, length = 20)
    private BoardType boardType;

    @Column(name = DatabaseConstants.Columns.TITLE, nullable = false, columnDefinition = DatabaseConstants.ColumnDefinitions.NVARCHAR_200)
    private String title;

    @Column(name = DatabaseConstants.Columns.CONTENT, nullable = false, columnDefinition = DatabaseConstants.ColumnDefinitions.NVARCHAR_4000)
    private String content;

    @Column(name = DatabaseConstants.Columns.AUTHOR, nullable = false, columnDefinition = DatabaseConstants.ColumnDefinitions.NVARCHAR_50)
    private String author;

    @Column(name = DatabaseConstants.Columns.VIEW_COUNT, nullable = false)
    private Long viewCount = 0L;

    @Column(name = DatabaseConstants.Columns.IS_PINNED, nullable = false)
    private Boolean isPinned = false;

    @Column(name = DatabaseConstants.Columns.IS_SECRET, nullable = false)
    private Boolean isSecret = false;

    @OneToMany(mappedBy = DatabaseConstants.Tables.BOARDS, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comments> comments = new ArrayList<>();

    @Column(name = DatabaseConstants.Columns.ATTACHMENT_URL, length = 500)
    private String attachmentUrl;

    @Column(name = DatabaseConstants.Columns.ATTACHMENT_NAME, length = 200)
    private String attachmentName;

    @Builder
    public Boards(final Long id,
                  final BoardType boardType,
                  final String title,
                  final String content,
                  final String author,
                  final Long viewCount,
                  final Boolean isPinned,
                  final Boolean isSecret,
                  final String attachmentUrl,
                  final String attachmentName) {
        this.id = id;
        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.author = author;
        this.viewCount = viewCount == null ? 0 : viewCount;
        this.isPinned = isPinned;
        this.isSecret = isSecret;
        this.attachmentUrl = attachmentUrl;
        this.attachmentName = attachmentName;
    }

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
                .mapToLong(comments -> comments.getReplies().size())
                .sum();
    }

    public void updateTitle(final String title) {
        if (title != null && !title.trim().isEmpty() && title.length() <= 200) {
            this.title = title;
        }
    }

    public void updateContent(final String content) {
        if (content != null && !content.trim().isEmpty()) {
            this.content = content;
        }
    }

    public void updateSecretStatus(final Boolean isSecret) {
        this.isSecret = isSecret != null ? isSecret : false;
    }

    public void updateAttachmentUrl(final String attachmentUrl) {
        this.attachmentUrl = attachmentUrl;
    }

    public void updateAttachmentName(final String attachmentName) {
        this.attachmentName = attachmentName;
    }
}
