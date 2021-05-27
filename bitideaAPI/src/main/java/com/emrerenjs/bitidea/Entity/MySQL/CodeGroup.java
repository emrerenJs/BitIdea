package com.emrerenjs.bitidea.Entity.MySQL;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Code_Groups")
@Getter 
@Setter 
@AllArgsConstructor 
@NoArgsConstructor
public class CodeGroup implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="code_group_id")
	private int id;

    @Column(name="name", unique = true,nullable = false,length = 50)
	private String name;
    
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "group_owner_id", referencedColumnName="profile_id")
    @JsonIgnore
    private Profile groupOwner;

    @OneToMany(mappedBy = "codeGroup", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProfilesCodeGroups> groupMembers;
    
    @OneToMany(fetch=FetchType.LAZY, mappedBy="codeGroup", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<GroupAnnouncement> announcements;
}
