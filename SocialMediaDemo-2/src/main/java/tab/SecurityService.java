package tab;

public interface SecurityService {
	
    String findLoggedInUsername();

    void autoLogin(String login, String password);
    
}
