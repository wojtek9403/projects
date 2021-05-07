package soap;

import java.util.HashSet;
import java.util.Set;

public class User {

	private String username;	
	
	private String password;	
	
	private boolean enabled;

	private Set<Authorities> auths = new HashSet<Authorities>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Authorities> getAuths() {
		return auths;
	}

	public void setAuths(Set<Authorities> auths) {
		this.auths = auths;
	}
	
}
