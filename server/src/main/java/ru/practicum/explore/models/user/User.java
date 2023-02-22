package ru.practicum.explore.models.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Пользователь
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /**
     * Имя пользователя
     */
    @Column(name = "name")
    @NotBlank
    @Size(min = 2)
    private String name;
    /**
     * Электронная почта пользователя
     */
    @Column(name = "email")
    @Email
    @NotBlank
    private String email;
    @Column(name = "banned")
    private boolean banned;
}
