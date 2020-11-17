package mainPackage;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;


@Service 
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS) 
public class UserSessionProviderImp implements UserSessionProvider
{
	
	String loggedUserName;

	@Override
	public String getUserName() {
		return this.loggedUserName;
	}

	@Override
	public void saveUserName(String currentUserName) {
		this.loggedUserName = currentUserName;
	}




}
