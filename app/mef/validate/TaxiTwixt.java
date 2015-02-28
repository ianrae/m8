package mef.validate;
import java.util.Date;

import models.Taxi;

import org.mef.framework.metadata.*;
import org.mef.framework.metadata.validate.IValidator;
import org.mef.framework.metadata.validate.ValContext;

import play.Logger;

public class TaxiTwixt extends TwixtForm
{
	public static class EvenValidator implements IValidator
	{
		@Override
		public void validate(ValContext valctx, Value arg1) 
		{
			IntegerValue val = (IntegerValue) arg1;
			int n = val.get();
			if (n % 2 != 0)
			{
				valctx.addError("val {0} not even!", n);
			}
		}
	}
	public static class NoAValidator implements IValidator
	{
		@Override
		public void validate(ValContext valctx, Value arg1) 
		{
			StringValue val = (StringValue) arg1;
			String s = val.get();
			if (s.contains("a"))
			{
				valctx.addError("val contains a");
			}
		}
	}
	
	public StringValue name;
	public IntegerValue size;
	public DateValue startDate;
	public BooleanValue isAdmin;
	public AccountType accountTypeId; //name must match Taxi
	
	public String ball;
	
	public TaxiTwixt()
	{
		this("", 0);
		ball = "red";
		size.setValidator(new EvenValidator());
		name.setValidator(new NoAValidator());
		
		size.set(144);
	}
	
	public TaxiTwixt(String namex, int size)
	{
		this.name = new StringValue(namex);
		this.size = new IntegerValue(size);
		this.startDate = new DateValue(new Date());
		this.isAdmin = new BooleanValue(false); //MUST be false
		this.accountTypeId = new AccountType();
	}

	
//	@Override 
//	public void copyTo(Object model)
//	{
//		Taxi m = (Taxi)model;
//		m.setName(this.name.get());
//		m.setSize(size.get());
//		m.setStartDate(this.startDate.get());
//	}
//
//	@Override 
//	public void copyFrom(Object model)
//	{
//		Taxi m = (Taxi)model;
//		this.name.set(m.getName());
//		this.size.set(m.getSize());
//		this.startDate.set(m.getStartDate());
//		Logger.info("AA: " + this.name);
//	}
}
