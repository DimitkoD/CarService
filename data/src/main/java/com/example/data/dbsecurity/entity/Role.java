package com.example.data.dbsecurity.entity;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {
    @Id
    private Long roleId;
    private String role;

    @OneToMany(mappedBy = "role")
    private Set<User> users;

}
