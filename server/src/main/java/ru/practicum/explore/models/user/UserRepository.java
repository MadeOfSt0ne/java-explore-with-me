package ru.practicum.explore.models.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Получение списка пользователей по списку идентификаторов
     */
    Page<User> findUsersByIdIn(List<Long> ids, Pageable pageable);

}
