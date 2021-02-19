package dbModelsnDAOs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Comment")
public class Comments {

	@Id
	@GeneratedValue
	Long id;

	String tresc;

	@ManyToOne
	User Comentator;

	@ManyToOne
	Picture picture;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTresc() {
		return tresc;
	}

	public User getComentator() {
		return Comentator;
	}

	public void setComentator(User comentator) {
		Comentator = comentator;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public Picture getPicture() {
		return picture;
	}

	public void setPicture(Picture picture) {
		this.picture = picture;
	}

}
