package com.jw.resourceserver.entity;

public final class DBConstants {
    DBConstants() {
    }

    public static final class Tables {
        public static final String BOARDS = "boards";
        public static final String COMMENTS = "comments";
    }

    public static final class Columns {
        /*common*/
        public static final String CREATED = "created";
        public static final String UPDATED = "updated";
        public static final String CREATED_BY = "created_by";
        public static final String UPDATED_BY = "updated_by";
        public static final String IS_DELETED = "is_deleted";
        public static final String DELETED = "deleted";

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

        /*FK*/
        public static final String BOARD_ID = "board_id";
        public static final String PARENT_ID = "parent_id";
    }

    public static class ColumnDefinitions {
        public static final String BIGINT = "BIGINT";
        public static final String NVARCHAR_50 = "NVARCHAR(50)";
        public static final String NVARCHAR_200 = "NVARCHAR(200)";
        public static final String NVARCHAR_2000 = "NVARCHAR(2000)";
        public static final String NVARCHAR_4000 = "NVARCHAR(4000)";
    }


    public static final class IndexNames {
        public static final String BOARDS_BOARD_TYPE = "idx_01_board_type";
        public static final String BOARDS_SEARCH = "idx_02_boards_search";
    }

    public static final class IndexColumns {

        public static final String BOARDS_SEARCH_COLUMNS =
                Columns.TITLE + " ASC , " +
                Columns.CONTENT + " ASC , " +
                Columns.AUTHOR + " ASC , " +
                Columns.CREATED_BY + " ASC ";
    }
}
