package ru.nemolyakin.resttestspring.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL,  mappedBy = "user")
    private List<EventEntity> eventEntities;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}