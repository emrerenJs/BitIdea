package com.emrerenjs.bitidea.Entity.MySQL;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int id;

    @Column(name="role",length = 20,unique = true,nullable = false)
    private String role;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "role_id",foreignKey = @ForeignKey(name="fk_role_user"))
    },
    inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> userList;

}
