import static org.junit.Assert.*;
import models.Sample;

import org.junit.Test;

import testbase.BaseTest;


public class OtherTests extends BaseTest
{

	@Test
	public void test() {
		log("test1");
//		fail("Not yet implemented");
		
		Sample sample = new Sample();
		log(sample.getClass().getSimpleName());
	}

}
