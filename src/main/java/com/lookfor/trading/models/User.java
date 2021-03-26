package com.lookfor.trading.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

/**
 * User entity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    private Integer id;

    private String username;
}
