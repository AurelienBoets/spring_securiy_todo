package org.example.spring_security.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.spring_security.enums.StateEnum;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private double id;
    private String description;
    private StateEnum state;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
