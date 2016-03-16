package my.maylab.amba;

import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.*;
import org.springframework.test.context.junit4.*;
import sample.auth.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = AuthApplication.class)
public class AuthApplicationTests {

	@Test
	public void contextLoads() {
	}

}
