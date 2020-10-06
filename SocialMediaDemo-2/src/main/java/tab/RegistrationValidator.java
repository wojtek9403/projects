package tab;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationValidator implements Validator {
	private UserRepository userRepository;

	public RegistrationValidator(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		User user = (User) target;

		this.validation("[a-zA-z0-9_!#$%&’*+/=?`{|}~^.-]{2,}", user.getUsername(), "Username", errors, "err_code",
				"Niepoprawna nazawa uzytkownika");
		this.validation("[a-zA-z0-9_!#$%&’*+/=?`{|}~^.-]{5,}", user.getPassword(), "password", errors, "err_code",
				"Hasło musi zawierać co najmniej 5 znaków");
		this.validation("[a-zA-z0-9_!#$%&’*+/=?`{|}~^.-]{3,}", user.getName(), "Name", errors, "err_code",
				"Imie musi mieć minimum 3 litery");
		this.validation("[a-zA-z0-9_!#$%&’*+/=?`{|}~^.-]{3,}", user.getName(), "Surname", errors, "err_code",
				"Nazwisko musi mieć minimum 3 litery");

		if (!user.getPassword().equals(user.getPasswordConfirm())) {
			errors.rejectValue("passwordConfirm", "err_code", "hasła muszą być zgodne");
		}

		if (this.userRepository.findRegistredLogin(user.getUsername()) != null) {
			errors.rejectValue("loginIstnieje", "err_code", "ten login jest już zajęty !");
		}

	}

	public void validation(String pattern, String value, String fieldName, Errors errors, String errCode,
			String errMessage) {

		Pattern patternCompiled = Pattern.compile(pattern);
		Matcher matcher = patternCompiled.matcher(value);

		if (!matcher.matches()) {
			errors.rejectValue(fieldName, errCode, errMessage);
		}

	}

}
