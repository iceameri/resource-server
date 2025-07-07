package com.jw.resourceserver.repository;

import com.jw.resourceserver.entity.resource.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comments, Long> {
    List<Comments> findByBoardsIdAndParentIsNullOrderByCreatedAsc(final Long boardId);
    List<Comments> findByParentIdOrderByCreatedAsc(final Long parentId);

    List<Comments> findByBoardsId(Long boardsId);

    @Modifying
    @Query("""
            UPDATE  Comments AS A
            SET     A.isDeleted = true,
                    A.deleted = GETDATE()
            WHERE   A.boards.id = :boardId
            """)
    void softDeleteByBoardId(@Param("boardId") Long boardId);

    @Modifying
    @Query("""
            UPDATE  Comments AS A
            SET     A.isDeleted = true,
                    A.deleted = GETDATE()
            WHERE   A.id = IN (:ids)
            """)
    int softDeleteByIds(@Param("ids") List<Long> ids);

    @Query("SELECT c.id FROM Comments c WHERE c.id IN (:ids) AND c.isDeleted = false")
    List<Long> findExistingIds(@Param("ids") List<Long> ids);
}
