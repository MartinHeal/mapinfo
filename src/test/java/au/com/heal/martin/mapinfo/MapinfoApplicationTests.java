package au.com.heal.martin.mapinfo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MapinfoApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

	@Test
	void testThatContextLoads() {
		assertThat(applicationContext.getId()).isEqualTo("application");
	}
}
