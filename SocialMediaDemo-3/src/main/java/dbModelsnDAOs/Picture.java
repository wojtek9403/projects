package dbModelsnDAOs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Pictures")
public class Picture implements Comparable<Picture> {

	@Id
	Long id;

	String orginalPicPath;

	String minPicPath;

	String description;

	@ManyToMany(mappedBy = "pictures")
	Set<User> users = new HashSet<User>();

	@OneToMany(mappedBy = "picture")
	Set<Comments> myComments = new HashSet<Comments>();

	public Set<Comments> getMyComments() {
		return myComments;
	}

	public void setMyComments(Set<Comments> myComments) {
		this.myComments = myComments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOrginalPicPath() {
		return orginalPicPath;
	}

	public void setOrginalPicPath(String orginalPicPath) {
		this.orginalPicPath = orginalPicPath;
	}

	public String getMinPicPath() {
		return minPicPath;
	}

	public void setMinPicPath(String minPicPath) {
		this.minPicPath = minPicPath;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {

		return description;
	}

	@Override
	public int compareTo(Picture o) {

		if (o instanceof Picture) {
			if (this.getId() < o.getId()) {
				return -1;
			} else if (this.getId() > o.getId()) {
				return 1;
			}
		}

		return 0;
	}

}
