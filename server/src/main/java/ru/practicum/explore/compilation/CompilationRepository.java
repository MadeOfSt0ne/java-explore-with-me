package ru.practicum.explore.compilation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompilationRepository extends JpaRepository<Compilation, Long> {

    /**
     * Получение страницы с подборками по флагу pinned
     */
    Page<Compilation> findAllByPinned(Pageable pageable, boolean pinned);
}
