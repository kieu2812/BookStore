package com.perscholas.testdao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.sql.DataSource;

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

import com.perscholas.dao.AuthorDAO;
import com.perscholas.model.Author;

@ContextConfiguration(locations="/application-context-test.xml")
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(Parameterized.class)
public class AuthorDAOTestGetById {
	
	@ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();
    
	@Autowired
	DataSource dataSource;
	
	@Autowired
	AuthorDAO authorDAO ;
	
	private int id;
	private String expectedResult;
	private Author expected;
	
	public AuthorDAOTestGetById(int id, Author expected, String expectedResult) {
		this.id= id;
		this.expected = expected;
		this.expectedResult = expectedResult;
		
	}
	
	@Parameterized.Parameters
	public static Collection<Object[]> params() {
		
		List<Object[]> parameters =  new ArrayList<Object[]>();
		parameters.add(new Object[]{1, new Author(1,"Neil Landau and Matthew Frederic"), "Pass"}) ;
		parameters.add(new Object[] {1, new Author(1,"Wrong name"), "Fail"});//wrong name
		parameters.add(new Object[]{2, new Author(2,"David Kin"), "Pass"}) ;
		parameters.add(new Object[] {2, new Author(2,"Wrong name"), "Fail"});//wrong name
		
		return parameters;
	}
	@Before
	public void printName() {
		System.out.println("Id: "+ id + " expected=[" + expected + "], expectedResult=" + expectedResult);
	}
	
	@Test
	public void getAuthorById() {

		Assert.assertNotNull(dataSource);
		Assert.assertNotNull(authorDAO);

		if (authorDAO == null) {
			System.out.println("Dao is null");
		} else {
			System.out.println("Dao is not null");
		}

		System.out.println("Run Test Method: ");
		Author actual = authorDAO.getAuthorById(id);
		System.out.println(actual);
		System.out.println(expected);

		if (expectedResult.equals("Pass")) {
			Assert.assertEquals(expected, actual);
		} else if (expectedResult.equals("Fail")) {
			Assert.assertNotEquals(expected, actual);
		}
	}
}
