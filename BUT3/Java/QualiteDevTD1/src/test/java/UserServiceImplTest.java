import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.school.User;
import org.school.UserService;

public class UserServiceImplTest {

  @Test
  void should_create_user_with_hashed_password() {

    UserService userService = mock(UserService.class);
    when(userService.createUser("Bob", "secret")).thenReturn(new User("Bob", "???"));

    User user = userService.createUser("Bob", "secret");

    assertEquals(user.firstName(), "Bob");
    assertEquals(user.hashedPassword(), "???");
  }
}
