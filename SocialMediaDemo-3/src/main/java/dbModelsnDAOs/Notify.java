package dbModelsnDAOs;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "NotifyTable")
public class Notify {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;

	String nadawcaID;
	String odbiorca;
	String nadawca;
	String tresc;

	public String getNadawcaID() {
		return nadawcaID;
	}

	public void setNadawcaID(String nadawcaID) {
		this.nadawcaID = nadawcaID;
	}

	String what;
	String path;

	@ManyToOne
	User owner;

	public String getWhat() {
		return what;
	}

	public void setWhat(String what) {
		this.what = what;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOdbiorca() {
		return odbiorca;
	}

	public void setOdbiorca(String odbiorca) {
		this.odbiorca = odbiorca;
	}

	public String getNadawca() {
		return nadawca;
	}

	public void setNadawca(String nadawca) {
		this.nadawca = nadawca;
	}

	public String getTresc() {
		return tresc;
	}

	public void setTresc(String tresc) {
		this.tresc = tresc;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

}
