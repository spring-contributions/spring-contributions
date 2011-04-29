package org.springframework.contributions;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.contributions.impl.ServiceWithoutContribution;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@ContextConfiguration("spring-contributions-ordered.xml")
@TestExecutionListeners(DependencyInjectionTestExecutionListener.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderedContributionIntegrationTest
{
	@Autowired
	private CallService testService;

	@Autowired
	private CallableHolderService secondService;

	@Autowired
	private ServiceWithoutContribution serviceWithoutContribution;

	@Autowired
	private StringBuilder logBuffer;
	
	@Autowired
	private ValueHolder stringHolder;

	@Test
	public void testOrderedContribution()
	{
		testService.callAll();
		assertThat(logBuffer.toString(), is("OneTwoThree"));

		List<Callable> callables = secondService.getCallables();
		assertThat(callables.size(), is(3));
	}

	@Test
	public void testEmptyContribution()
	{
	    assertThat(serviceWithoutContribution.getEmptyContribution().size(), is(0));
	}

	@Test
	public void testStringContribution()
	{
		assertThat(stringHolder.getValues(), is(Arrays.asList("String 1", "String 2", "String 3")));
	}
}