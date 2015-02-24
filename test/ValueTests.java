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
		IntegerValue abc;
		BooleanValue b;
		
		public MyForm()
		{
			this.initFields();
		}

		@Override
		public void copyFrom(Object model) 
		{
			this.copyFieldsFrom(model);
		}

		@Override
		public void copyTo(Object model) {
			// TODO Auto-generated method stub
			
		}
	}
	
	public static class MyDerivedForm extends MyForm
	{
		StringValue d;
		
		public MyDerivedForm()
		{
			this.initFields(); //double init -- can we avoid this??
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
	
	public static class MyModel 
	{
		String s;
		int abc;
		boolean b;
		
		public String getS()
		{
			return s;
		}
		public int getAbc()
		{
			return abc;
		}
		public boolean getB()
		{
			return b;
		}
	}
	
	
	@Test
	public void test() 
	{
		log("a");
		MyForm frm = new MyForm();
		assertNotNull(frm.s);
		assertNotNull(frm.abc);
		assertNotNull(frm.b);
	}

	@Test
	public void testDerived() 
	{
		MyDerivedForm frm = new MyDerivedForm();
		assertNotNull(frm.s);
		assertNotNull(frm.abc);
		assertNotNull(frm.b);
	}
	
	@Test
	public void testCopy() 
	{
		MyModel m = new MyModel();
		m.abc = 45;
		m.b = true;
		m.s = "def";
		
		MyForm frm = new MyForm();
		frm.copyFrom(m);
		
		assertEquals(45, frm.abc.get());
		assertEquals(true, frm.b.get());
		assertEquals("def", frm.s.get());
	}
}
