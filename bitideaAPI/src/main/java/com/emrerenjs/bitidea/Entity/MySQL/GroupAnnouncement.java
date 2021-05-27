package com.emrerenjs.bitidea.Entity.MySQL;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Group_Announcements")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class GroupAnnouncement implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "announcement_id")
	private int id;
	
	@Column(name = "announcement", nullable = false)
	private String announcement;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "announcer_id", referencedColumnName = "profile_id")
	//@JsonBackReference
	private Profile announcer;
	
	@ManyToOne
	@JoinColumn(name = "code_group_id", referencedColumnName = "code_group_id")
	@JsonBackReference
	private CodeGroup codeGroup;
	
	@OneToMany(mappedBy="groupAnnouncement", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<AnnouncementComment> comments;
	
    @Column(name="created_at", nullable = false)
    private String createdAt;
    
    @PrePersist
    protected void onCreate(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		this.createdAt = simpleDateFormat.format(new Date());
    }

}
