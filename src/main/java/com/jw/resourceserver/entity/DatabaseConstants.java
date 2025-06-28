package com.jw.resourceserver.entity;

public final class DatabaseConstants {
    private DatabaseConstants() {
    }

    public static final class Tables {
        public static final String BOARDS = "boards";
        public static final String COMMENTS = "comments";
    }

    public static final class Columns {
        public static final String CREATED = "created";
        public static final String UPDATED = "updated";
        public static final String CREATED_BY = "created_by";
        public static final String UPDATED_BY = "updated_by";
        public static final String IS_DELETED = "is_deleted";

        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String CONTENT = "content";
        public static final String BOARD_TYPE = "board_type";
        public static final String AUTHOR = "author";
        public static final String VIEW_COUNT = "view_count";
        public static final String IS_PINNED = "is_pinned";
        public static final String IS_SECRET = "is_secret";
        public static final String ATTACHMENT_URL = "attachment_url";
        public static final String ATTACHMENT_NAME = "attachment_name";

        //FK
        public static final String BOARD_ID = "board_id";
    }

    public static class ColumnDefinitions {
        public static final String BIGINT_UNSIGNED = "BIGINT(20) UNSIGNED";
        public static final String INT_DEFAULT_ZERO = "INT DEFAULT 0";
    }

    public static final String INDEX_ = "idx_";
}
