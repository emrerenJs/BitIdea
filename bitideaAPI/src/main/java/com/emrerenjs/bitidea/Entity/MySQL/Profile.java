package com.emrerenjs.bitidea.Entity.MySQL;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Profile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="profile_id")
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id",referencedColumnName = "user_id", foreignKey = @ForeignKey(name = "fk_profile_user"))
    @JsonIgnore
    private User user;

    @Column(name="username",unique = true,nullable = false,length = 50)
    private String username;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProfilesCodeGroups> groups;

    @Column(length = 25)
    private String firstname;
    @Column(length = 25)
    private String lastname;
    @Column(length = 250)
    private String photo;
    private String biography;
    private int score;
}
