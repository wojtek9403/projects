package tab;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Pictures")
public class Picture {
	
	@Id
	Long id;
	
	String orginalPicPath;
	
	String minPicPath;
	
	String description;	
	
	@ManyToMany(mappedBy = "pictures") 
	Set<User> users =  new HashSet<User>();

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
	public String toString()
	{
		
		return description;
	}

	
}
