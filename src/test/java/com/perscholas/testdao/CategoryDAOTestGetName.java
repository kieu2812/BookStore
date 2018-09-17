package com.perscholas.testdao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import com.perscholas.dao.CategoryDAO;
import com.perscholas.model.Category;

@ContextConfiguration(locations="classpath:application-context-test.xml")
@RunWith(Parameterized.class)
public class CategoryDAOTestGetName {
	
	@ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    CategoryDAO categoryDAO;
    
    private String name;
	private String expectedResult;
	private List<Category> expected;
	
	public CategoryDAOTestGetName( String name, List<Category> expected, String expectedResult) {
		this.name= name;
		this.expected = expected;
		this.expectedResult = expectedResult;
		
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> params() {

		
		List<Object[]> parameters =  new ArrayList<Object[]>();

		// Name not exist
		parameters.add(new Object[]{"Computer",
						Arrays.asList(
										new Category(8,"Computers & Technology"),
										new Category(29,"Mary Ann Hoberman and Michael Emberle"),
										new Category(123,"Michael Krach")
									),																				 
						"Fail"}) ;
		// Name not exists
		parameters.add(new Object[]{"Hello", 
				new ArrayList<Category>(),				 
				"Pass"}) ;
		// exact name Computers & Technology in database => pass
		parameters.add(new Object[]{"Computers & Technology",
				Arrays.asList(
								new Category(8,"Computers & Technology")
							),																				 
				"Pass"}) ;
		
		// exact name Computers & Technology in database but actual difference name =>Fail
		parameters.add(new Object[]{"Computers & Technology",
				Arrays.asList(
								new Category(8,"computer technology")
							),																				 
				"Fail"}) ;	
		//Name= Computers & Technology  has 1 record in database but actual result= null => Fail
		parameters.add(new Object[]{"Computers & Technology",
									new ArrayList<Category>(),																					 
									"Fail"}) ;
		//Name= Computers & Technology  has 1 record in database but actual result= difference name => Fail
		parameters.add(new Object[]{"Computers & Technology",
				 Arrays.asList(new Category(23,"Engineering & Transportation")),																				 
				"Fail"}) ;

		
		return parameters;
	}
	@Before
	public void printName() {
		System.out.println("\n****** Name= "+ name + "\n Expected" + expected.toString() + "\n ExpectedResult =>  " + expectedResult);
	}
	
	@Test 
	public void getCategoryByName() {
		
		Category actual = categoryDAO.getCategoryByName(name);
		System.out.println("Actual= "+ actual);

		
		// Compare 2 Collection using EqualsBuilder 
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(expected, actual);
		String actualResult = builder.isEquals() ? "Pass" : "Fail";
		System.out.println("Actual Result => "+ actualResult);
		
		Assert.assertEquals(expectedResult, actualResult);
		
		
	}
}
