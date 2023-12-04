import org.school.HashProvider;
import org.school.User;
import org.school.UserService;

public class UserServiceImpl implements UserService {

	private final HashProvider hashProvider;

	public UserServiceImpl(HashProvider hashProvider) {
		this.hashProvider = hashProvider;
	}

	@Override
	public User createUser(String firstName, String password) {
		String hashedPassword = hashProvider.hash(password);
		return new User(firstName, hashedPassword);
	}
}
