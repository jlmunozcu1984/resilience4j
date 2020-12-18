package io.github.resilience4j.timelimiter.configure;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.github.resilience4j.TestApplication;
import io.github.resilience4j.TestDummyService;
import io.github.resilience4j.TimeLimiterDummyService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class TimeLimiterTest {
    @Autowired
    @Qualifier("timeLimiterDummyService")
    TestDummyService testDummyService;
    
    @Test
    public void testSync0ms() {
        assertThat(((TimeLimiterDummyService)testDummyService).sync(0)).isEqualTo("test");
    }
    
    @Test
    public void testSync60000ms() {       
    	assertThat(catchThrowable(() -> ((TimeLimiterDummyService)testDummyService).sync(60000)))
    	.isInstanceOf(UndeclaredThrowableException.class)
    	.hasCauseInstanceOf(TimeoutException.class);   	
    }    
}
