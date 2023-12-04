import org.junit.jupiter.api.Test;
import org.school.User;
import org.school.UserService;
import org.school.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserServiceImplTest {

	@Test
	void should_create_user_with_hashed_password() {

		UserService userService = new UserServiceImpl(???);

		User user = userService.createUser("Bob", "secret");

		assertEquals(user.firstName(), "Bob");
		assertEquals(user.hashedPassword(), "???");
	}
}
