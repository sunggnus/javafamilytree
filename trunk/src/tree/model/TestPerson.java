package tree.model;

import junit.framework.Assert;

import org.junit.Test;

public class TestPerson {
	
	@Test
	public void testAge() throws AgeException, InvalidSexException{
		Person person = new Person("","",true, Person.FEMALE, null, null, 25, 9,1988);
		Assert.assertEquals(24, person.getAge());
		System.out.println(PersonUtil.calendarToString(person.getBirthdate()));
		Person person2 = new Person("","",true, Person.FEMALE, null, null, 25, 1,1988);
		Assert.assertEquals(25, person2.getAge());
		System.out.println(PersonUtil.calendarToString(person2.getBirthdate()));
	}

}
