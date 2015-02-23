import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mef.validate.TaxiTwixt;
import models.Sample;

import org.junit.Test;

import testbase.BaseTest;

import org.springframework.util.ReflectionUtils;

import play.utils.meta.FieldMetadata;
import play.utils.meta.convert.StringConverter;
import controllers.TaxiController;

public class OtherTests extends BaseTest
{

	@Test
	public void test() 
	{
		log("test1");
//		fail("Not yet implemented");
		
		Sample sample = new Sample();
		log(sample.getClass().getSimpleName());
		
//		Field f = ReflectionUtils.findMethod(TaxiController.class, "rev", null);
		Field f = ReflectionUtils.findField(TaxiTwixt.class, "ball");
		FieldMetadata meta = new FieldMetadata(f, new StringConverter());
		assertNotNull(f);
	}
	
	@Test
	public void findL()
	{
		List<FieldMetadata> L = new ArrayList<FieldMetadata>();
		Class clazz = L.getClass();
		log(clazz.getName());
		
		List<FieldMetadata> L2 = null;
		clazz = L2.getClass();
		log(clazz.getName());
		
		
	}

}
