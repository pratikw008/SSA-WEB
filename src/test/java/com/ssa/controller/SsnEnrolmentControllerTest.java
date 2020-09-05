package com.ssa.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ssa.models.Gender;
import com.ssa.models.SsnDTO;
import com.ssa.service.ISsnEnrolmentService;

@RunWith(SpringRunner.class)
@WebMvcTest(SsnEnrolmentController.class)
public class SsnEnrolmentControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ISsnEnrolmentService enrolmentService;
	
	@Test
	public void getAllStates() throws Exception {
		List<String> listStates = Arrays.asList("Texas", "Florida", "Washington", "New Jersey", "California");
		//Create Behaviour for Service
		when(enrolmentService.getAllStates()).thenReturn(listStates);
		
		//Create get Request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/allStates");
		
		//Using mockMvc Call perform method and return method
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//By Using MvcResult get Response
		MockHttpServletResponse response = mvcResult.getResponse();
		
		int statusCode = response.getStatus();
		
		String contentAsString = response.getContentAsString();
		System.out.println("**********Actaul: "+contentAsString+" **********");
		String expected = new ObjectMapper().writeValueAsString(listStates);
		System.out.println("**********Expected: "+expected+" **********");
		
		assertEquals(200, statusCode);
		
		assertEquals(expected, contentAsString);
	}
	
	@Test
	public void ssnEnrol() throws Exception {
		SsnDTO ssnEnrol = new SsnDTO("000000001", "John", "DeCosta", Gender.MALE, LocalDate.now(), "Vegas");
		System.out.println(LocalDate.of(2000, Month.OCTOBER, 2)+"********************");
		System.out.println(ssnEnrol);
		//Create Behaviour for Service
		when(enrolmentService.ssnEnrol(ssnEnrol)).thenReturn(ssnEnrol);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		
		String ssnEnrolJson = mapper.writeValueAsString(ssnEnrol);
		System.out.println(ssnEnrolJson+"**********");
		//Create Post Request
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/ssnEnrol")
																			 .content(ssnEnrolJson)
																			 .contentType(MediaType.APPLICATION_JSON_VALUE);

		//Call perform method and return
		MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
		
		//Get Response using Mvc Result
		MockHttpServletResponse response = mvcResult.getResponse();
		
		//Get Response from response
		int actualStatus = response.getStatus();
		System.out.println("******Actual Status "+actualStatus+" ******");
		
		String actualContent = response.getContentAsString();
		System.out.println("******Actual Content "+actualContent+" ******");
		
		assertEquals(201, actualStatus);
		
		assertEquals(ssnEnrolJson, actualContent);
	}
}