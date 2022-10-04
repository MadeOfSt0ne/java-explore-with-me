package ru.practicum.explore.models.category;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Категория событий
 */
@Entity
@Table(name = "categories")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Название категории
     */
    @Column(name = "name")
    @NotBlank
    private String name;

}
