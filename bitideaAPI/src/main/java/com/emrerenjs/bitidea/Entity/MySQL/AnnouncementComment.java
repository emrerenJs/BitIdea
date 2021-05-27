package com.emrerenjs.bitidea.Entity.MySQL;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Announcement_Comments")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class AnnouncementComment implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private int id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "comment_owner_id", referencedColumnName = "profile_id")
	//@JsonBackReference
	private Profile commentOwner;
	
	@Column(name = "comment", nullable = false)
	private String comment;
	
	@ManyToOne
	@JoinColumn(name = "announcement_id", referencedColumnName = "announcement_id")
	@JsonBackReference
	private GroupAnnouncement groupAnnouncement;
	
	@Column(name = "created_at", nullable = false)
	private String createdAt;
	
	
	@PrePersist
	protected void onCreate() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
		this.createdAt = simpleDateFormat.format(new Date());
	}
	
}
