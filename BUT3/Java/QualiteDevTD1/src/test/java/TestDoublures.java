import org.junit.jupiter.api.Test;
import org.school.User;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
public class TestDoublures {

	@Test 
	void test_UnPremierStub() {

		User user = mock(User.class);
		when(user.getLogin()).thenReturn("Nestor");
	}
}
