package tab;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "Person")
public class User {

	@javax.persistence.Id
	String Username;
	
	String Name;
	
	String Surname;
	
    private String password;
    
	@Transient
    private String passwordConfirm;
    
    String role;
    
    String profilePicture;
    
    @Transient 
    private User loginIstnieje;
    
    public User getLoginIstnieje() {
		return loginIstnieje;
	}
    
    /*
     * void update();
     * 
     * Set<User> friends = new HashSet<User>();
     * 
     * void getFriends();
     * 
     * void addFriend();
     * 
     * void notifyMyFriends();
     * 
     * 
     */

	public void setLoginIstnieje(User loginIstnieje) {
		this.loginIstnieje = loginIstnieje;
	}

	public String getName()
	{
		return this.Name;
	}
	
	public String getSurname()
	{
		return this.Surname;
	}
	
	public void setName(String name)
	{
		name.toLowerCase();
		this.Name = name;
	}
	
	public void setSurname(String surname)
	{
		surname.toLowerCase();
		this.Surname = surname;
	}

	@ManyToMany 
    Set<Picture> pictures = new HashSet<Picture>();

    public String getUsername() {
		return Username;
	}

	public void setUsername(String Username) {
		this.Username = Username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}
	
	@Override
	public String toString()
	{
		
		return Username;
	}
	
	
}
