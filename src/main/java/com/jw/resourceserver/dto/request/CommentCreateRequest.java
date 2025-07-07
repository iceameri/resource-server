package com.jw.resourceserver.dto.request;

import lombok.Getter;

@Getter
public class CommentCreateRequest {
    private final String content;
    private final Long parentId;
    private final Boolean isSecret;

    public CommentCreateRequest(final String content) {
        this(content, null, false);
    }

    public CommentCreateRequest(final String content, final Long parentId, final Boolean isSecret) {
        this.validateContent(content);

        this.content = content;
        this.parentId = parentId;
        this.isSecret = isSecret != null ? isSecret : false;
    }

    private void validateContent(final String content) {
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("댓글 내용은 필수입니다.");
        }
        if (content.length() > 1000) {
            throw new IllegalArgumentException("댓글은 1000자를 초과할 수 없습니다.");
        }
    }

    public boolean isReply() {
        return parentId != null;
    }
}

