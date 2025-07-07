package com.jw.resourceserver.dto.request;

import com.jw.resourceserver.entity.resource.BoardType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BoardCreateRequest {
    private final BoardType boardType;
    private final String title;
    private final String content;
    private final String author;
    private final Boolean isPinned;
    private final Boolean isSecret;
    private final String attachmentUrl;
    private final String attachmentName;

    public BoardCreateRequest(final BoardType boardType, final String title, final String content, final String author) {
        this(boardType, title, content, author, false, false, null, null);
    }

    @Builder
    public BoardCreateRequest(final BoardType boardType,
                              final String title,
                              final String content,
                              final String author,
                              final Boolean isPinned,
                              final Boolean isSecret,
                              final String attachmentUrl,
                              final String attachmentName) {
        this.validateTitle(title);
        this.validateContent(content);
        this.validateAuthor(author);

        this.boardType = boardType;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isPinned = isPinned != null ? isPinned : false;
        this.isSecret = isSecret != null ? isSecret : false;
        this.attachmentUrl = attachmentUrl;
        this.attachmentName = attachmentName;
    }

    private void validateTitle(final String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (title.length() > 200) {
            throw new IllegalArgumentException("제목은 200자를 초과할 수 없습니다.");
        }
    }

    private void validateContent(final String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
    }

    private void validateAuthor(final String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("작성자는 필수입니다.");
        }
        if (author.length() > 50) {
            throw new IllegalArgumentException("작성자는 50자를 초과할 수 없습니다.");
        }
    }
}
