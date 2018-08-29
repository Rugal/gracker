package ga;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 *
 * @author Rugal Bernstein
 */
@ContextConfiguration(classes = {config.ApplicationContext.class,
                                 config.TestApplicationContext.class})
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class IntegrationTestBase {
}
