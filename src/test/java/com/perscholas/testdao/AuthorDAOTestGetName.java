package com.perscholas.testdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.perscholas.dao.AuthorDAO;
import com.perscholas.model.Author;

@ContextConfiguration(locations = {"application-context-test.xml"})
@RunWith(Parameterized.class)
public class AuthorDAOTestGetName {

	@Autowired
	AuthorDAO authorDAO;
	
	private String name;
	private String expectedResult;
	private List<Author> expected;
	
	public AuthorDAOTestGetName( String name, List<Author> expected, String expectedResult) {
		this.name= name;
		this.expected = expected;
		this.expectedResult = expectedResult;
		
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> params() {

		
		List<Object[]> parameters =  new ArrayList<Object[]>();

		// Name not exists
		parameters.add(new Object[]{"Hello", 
				new ArrayList<Author>(),				 
				"Pass"}) ;
		//Name= Bob Colacello and Jonathan Becke  has 1 record with id =11 =>Pass
		parameters.add(new Object[]{"Bob Colacello and Jonathan Becke",
				Arrays.asList(
								new Author(11,"Bob Colacello and Jonathan Becke")
							),																				 
				"Pass"}) ;
		
		// Name= Bob Colacello and Jonathan Becke  has 1 record with id =12 =>Fail
		parameters.add(new Object[]{"Bob Colacello and Jonathan Becke",
				Arrays.asList(
								new Author(12,"Bob Colacello and Jonathan Becke")
							),																				 
				"Fail"}) ;	
		//Name= Bob Colacello and Jonathan Becke  has 1 record with id =11 and name difference => Fail
		parameters.add(new Object[]{"Bob Colacello and Jonathan Becke",
				Arrays.asList(
								new Author(11,"Jonathan Becke")
							),																				 
				"Fail"}) ;
		//Name= Bob Colacello and Jonathan Becke don't exist in database => Fail
		parameters.add(new Object[]{"Bob Colacello and Jonathan Becke",
				new ArrayList<Author>(),																				 
				"Fail"}) ;

		
		return parameters;
	}
	@Before
	public void printName() {
		System.out.println("\n****** Name= "+ name + "\n Expected" + expected.toString() + "\n ExpectedResult =>  " + expectedResult);
	}
	
	@Test 
	public void getAuthorByName() {
		Author actual = authorDAO.getAuthorByName(name);
		System.out.println("\nActual= "+ actual);
		
		// Compare 2 Collection using EqualsBuilder 
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(expected, actual);
		String actualResult = builder.isEquals() ? "Pass" : "Fail";
		System.out.println("Actual Result => "+ actualResult);
		
		Assert.assertEquals(expectedResult, actualResult);
		
		
	}
}
