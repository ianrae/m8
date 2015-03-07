import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mef.validate.TaxiTwixt;
import models.Sample;

import org.junit.Test;


import org.mef.twixt.Value;
import org.springframework.util.ReflectionUtils;

import base.Base;
import play.utils.meta.FieldMetadata;
import play.utils.meta.convert.StringConverter;
import controllers.TaxiController;

public class OtherTests extends Base implements ReflectionUtils.FieldCallback, ReflectionUtils.FieldFilter
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
	public void testFmt()
	{
		int planet = 7;
		 String event = "a disturbance in the Force";

		 String result = MessageFormat.format(
		     "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
		     planet, new Date(), event);
		 log(result);
	}

	@Test
	public void findL()
	{
		List<FieldMetadata> L = new ArrayList<FieldMetadata>();
		Class clazz = L.getClass();
		log(clazz.getName());


		ReflectionUtils.doWithFields(TaxiTwixt.class, this, this);

	}


	@Override
	public void doWith(Field arg0) throws IllegalArgumentException,
	IllegalAccessException {

		log(arg0.getName());

	}

	@Override
	public boolean matches(Field arg0) 
	{
		return (Value.class.isAssignableFrom(arg0.getType()));
	}

}
