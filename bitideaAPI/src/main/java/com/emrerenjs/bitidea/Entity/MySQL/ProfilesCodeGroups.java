package com.emrerenjs.bitidea.Entity.MySQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Profiles_CodeGroups")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ProfilesCodeGroups implements Serializable {

    public static final int OWNER_USER = 3;
    public static final int INVITED_USERS = 0;
	public static final int PARTICIPATING_USERS = 1;
	public static final int MEMBER_USERS = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "profiles_codeGroups_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "code_group_id", referencedColumnName = "code_group_id")
	@JsonBackReference
	private CodeGroup codeGroup;
	
	@ManyToOne
	@JoinColumn(name = "profile_id", referencedColumnName = "profile_id")
	@JsonBackReference
	private Profile profile;
	
	@Column(name = "member_type", length = 4)
	private int memberType;
	
	
	
}
