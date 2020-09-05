package com.ssa.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssa.models.SsnVerifier;
import com.ssa.service.ISsnEnrolmentService;

@RunWith(SpringRunner.class)
@WebMvcTest(SsnValidatorController.class)
public class SsnValidatorControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ISsnEnrolmentService enrolmentService;
	
	@Test
	public void verifySsn() throws Exception {
		when(enrolmentService.findBySsnAndStateName("101", "California")).thenReturn(SsnVerifier.INVALID);
		
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/verify/101/California");
		
		MvcResult result = mvc.perform(request).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String actual = response.getContentAsString();
		System.out.println(actual+"********************");
		String expected = new ObjectMapper().writeValueAsString(SsnVerifier.INVALID);
		System.out.println(expected+"********************");
		assertEquals(expected, actual);
	}
}
