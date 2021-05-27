package com.emrerenjs.bitidea.Entity.MySQL;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="Users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User implements Serializable {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="username",unique = true,nullable = false,length = 50)
    private String username;

    @Column(name="password",nullable = false,length = 80)
    private String password;

    @Column(name="email",unique = true,nullable = false, length = 75)
    private String email;

    @Column(name="is_email_valid",nullable = false,columnDefinition = "BOOLEAN DEFAULT false")
    private boolean emailValid;

    @Column(name="created_at", nullable = false)
    private Date createdAt;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="user_role",joinColumns = {
            @JoinColumn(name="user_id",foreignKey = @ForeignKey(name="fk_user_role"))
    },
    inverseJoinColumns = {@JoinColumn(name="role_id")}
    )
    @JsonIgnore
    private List<Role> roleList;

    public User(String username,String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @PrePersist
    protected void onCreate(){
        this.createdAt = new Date();
    }

}
