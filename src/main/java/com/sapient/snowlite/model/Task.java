/**
 * 
 */
package com.sapient.snowlite.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author syada3
 *
 */
public class Task implements Serializable, Comparable<Task> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5895177026068360506L;
	private long id;
	private String shortDescription;
	private String description;
	private User user;
	private String status;
	private String displayPrefix;
	private LocalDateTime createdOn;


	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDisplayPrefix() {
		return displayPrefix;
	}

	public void setDisplayPrefix(String displayPrefix) {
		this.displayPrefix = displayPrefix;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	@Override
	public int compareTo(Task o) {
		return o.getCreatedOn().compareTo(createdOn);
	}
	
}
