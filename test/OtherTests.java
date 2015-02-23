import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import mef.validate.TaxiTwixt;
import models.Sample;

import org.junit.Test;
import org.mef.framework.metadata.Value;

import testbase.BaseTest;

import org.springframework.util.ReflectionUtils;

import play.utils.meta.FieldMetadata;
import play.utils.meta.convert.StringConverter;
import controllers.TaxiController;

public class OtherTests extends BaseTest implements ReflectionUtils.FieldCallback, ReflectionUtils.FieldFilter
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
