package com.jw.resourceserver.repository;

import com.jw.resourceserver.entity.resource.BoardType;
import com.jw.resourceserver.entity.resource.Boards;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Boards, Long> {
    List<Boards> findByBoardTypeOrderByIsPinnedDescCreatedDesc(final BoardType boardType);
    List<Boards> findAllByOrderByIsPinnedDescCreatedDesc();
    List<Boards> findByTitleContainingIgnoreCaseOrderByCreatedDesc(final String keyword);
    List<Boards> findByContentContainingIgnoreCaseOrderByCreatedDesc(final String keyword);
    List<Boards> findByAuthorOrderByCreatedDesc(final String author);
}
