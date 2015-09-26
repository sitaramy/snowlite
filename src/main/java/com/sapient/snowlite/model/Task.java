/**
 * 
 */
package com.sapient.snowlite.model;

import java.io.Serializable;

/**
 * @author syada3
 *
 */
public class Task implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5895177026068360506L;
	private long id;
	private String shortDescription;
	private String description;
	private User user;
	private String status;


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
}
