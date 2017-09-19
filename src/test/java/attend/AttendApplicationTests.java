package attend;

import com.benzourry.cloqr.config.AttendApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

//import org.springframework.boot.test.SpringApplicationConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = AttendApplication.class)
//@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AttendApplication.class)
@WebAppConfiguration
public class AttendApplicationTests {

	@Test
	public void contextLoads() {
	}

}
