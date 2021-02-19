package dbModelsnDAOs;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.ui.Model;

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
	
	String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Transient
	private User loginIstnieje;

	public User getLoginIstnieje() {
		return loginIstnieje;
	}

	@OneToMany(mappedBy = "owner")
	Set<Notify> myNotifs = new HashSet<Notify>();

	@ManyToMany
	Set<User> friends = new HashSet<User>();

	@ManyToMany
	Set<Picture> pictures = new HashSet<Picture>();

	@OneToMany(mappedBy = "Comentator")
	Set<Comments> myComments = new HashSet<Comments>();

	public Set<Notify> getMyNotifs() {
		return myNotifs;
	}

	public void setMyNotifs(Set<Notify> myNotifs) {
		this.myNotifs = myNotifs;
	}

	public void setName(String name) {
		name.toLowerCase();
		this.Name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Username == null) ? 0 : Username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (Username == null) {
			if (other.Username != null)
				return false;
		} else if (!Username.equals(other.Username))
			return false;
		return true;
	}

	public void setSurname(String surname) {
		surname.toLowerCase();
		this.Surname = surname;
	}

	@Override
	public String toString() {

		return Username;
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getName() {
		return Name;
	}

	public String getSurname() {
		return Surname;
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

	public Set<User> getFriends() {
		return friends;
	}

	public void setFriends(Set<User> friends) {
		this.friends = friends;
	}

	public Set<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Set<Picture> pictures) {
		this.pictures = pictures;
	}

	public void setLoginIstnieje(User loginIstnieje) {
		this.loginIstnieje = loginIstnieje;
	}
//	

	public void notify(String nadawcaID, String nadawca, String odbiorcaID, String tresc, User owner,
			NotifyRepository notifyRepository, String what, String path) {
		Notify newNotif = new Notify();
		newNotif.setNadawcaID(nadawcaID);
		newNotif.setNadawca(nadawca);
		newNotif.setOdbiorca(odbiorcaID);
		newNotif.setTresc(tresc);
		newNotif.setOwner(owner);
		newNotif.setWhat(what);
		newNotif.setPath(path);
		notifyRepository.save(newNotif);
	}

	public void updateNotifs(Model model) {
		Set<Notify> notifs = this.getMyNotifs();

		List<Notify> listOfNotifs = new ArrayList<Notify>(notifs);

		Comparator<Notify> comp = new Comparator<Notify>() {

			@Override
			public int compare(Notify o1, Notify o2) {

				if (o1.getId() < o2.getId()) {
					return 1;
				} else if (o1.getId() > o2.getId()) {
					return -1;
				} else {
					return 0;
				}
			}

		};

		listOfNotifs.sort(comp);

		model.addAttribute("notifs", listOfNotifs);

	}

}
