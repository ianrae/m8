import static org.junit.Assert.*;
import mef.validate.TwixtForm;

import org.junit.Test;
import org.mef.framework.metadata.BooleanValue;
import org.mef.framework.metadata.IntegerValue;
import org.mef.framework.metadata.StringValue;

import testbase.BaseTest;


public class ValueTests extends BaseTest
{
	public static class MyForm extends TwixtForm
	{
		StringValue s;
		IntegerValue a;
		BooleanValue b;
		
		public MyForm()
		{
			this.initDeclaredFields();
		}

		@Override
		public void copyFrom(Object model) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void copyTo(Object model) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	@Test
	public void test() 
	{
		log("a");
		MyForm frm = new MyForm();
		assertNull(frm.s);
		
	}

}
