import static org.junit.Assert.*;

import org.junit.Test;
import org.mef.twixt.BooleanValue;
import org.mef.twixt.IntegerValue;
import org.mef.twixt.StringValue;
import org.mef.twixt.binder.TwixtForm;

import base.Base;



public class ValueTests extends Base
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

	}
	
	public static class MyDerivedForm extends MyForm
	{
		StringValue d;
		
		public MyDerivedForm()
		{
			this.initFields(); 
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
		
		public void setS(String s)
		{
			this.s = s;
		}
		public void setAbc(int n)
		{
			this.abc = n;
		}
		public void setB(boolean b)
		{
			this.b = b;
		}
	}
	
	public static class MyForm2 extends TwixtForm
	{
		StringValue s = new StringValue("halifax");
		IntegerValue abc;
		
		public MyForm2()
		{
			this.initFields();
		}
	}
	
	public static class MyForm3 extends TwixtForm
	{
		StringValue s;
		IntegerValue abc;
		BooleanValue b;
		
		public MyForm3()
		{
			this.initFields();
		}
		
		@Override
		public void copyFrom(Object model) 
		{
			this.copyFieldsFromModel(model, "abc", "s");
		}

		@Override
		public void copyTo(Object model) 
		{
			this.copyFieldsToModel(model, "abc", "s");
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
	public void test2() 
	{
		MyForm2 frm = new MyForm2();
		assertNotNull(frm.s);
		assertNotNull(frm.abc);
		assertEquals("halifax", frm.s.get());
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
	public void testCopyFrom() 
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
	
	@Test
	public void testCopyTo() 
	{
		MyModel m = new MyModel();
		
		MyForm frm = new MyForm();
		frm.abc.set(15);
		frm.b.set(true);
		frm.s.set("apple");
		frm.copyTo(m);
		
		assertEquals("apple", m.s);
		assertEquals(15, m.abc);
		assertEquals(true, m.b);
	}
	
	
	@Test
	public void testCopyFrom3() 
	{
		MyModel m = new MyModel();
		m.abc = 45;
		m.b = true;
		m.s = "def";
		
		MyForm3 frm = new MyForm3();
		frm.copyFrom(m);
		
		assertEquals(0, frm.abc.get());
		assertEquals(true, frm.b.get());
		assertEquals("", frm.s.get());
	}
	
}
