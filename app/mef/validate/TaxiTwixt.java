package mef.validate;
import java.util.Date;

import models.Taxi;

import org.mef.framework.metadata.*;
import org.mef.framework.metadata.validate.ValContext;

import play.Logger;

public class TaxiTwixt extends TwixtForm
{
	public StringValue name;
	public IntegerValue size;
	public DateValue startDate;
	
	public String ball;
	
	public TaxiTwixt()
	{
		this("", 0);
		ball = "red";
	}
	
	public TaxiTwixt(String namex, int size)
	{
		this.name = new StringValue(namex);
		this.size = new IntegerValue(size);
		this.startDate = new DateValue(new Date());
	}

	@Override
	public void validateContainer(ValContext arg0) 
	{
		arg0.validate(name);
		arg0.validate(size);
		arg0.validate(startDate);
	}
	
	@Override 
	public void copyTo(Object model)
	{
		Taxi m = (Taxi)model;
		m.setName(this.name.get());
		m.setSize(size.get());
		m.setStartDate(this.startDate.get());
	}

	@Override 
	public void copyFrom(Object model)
	{
		Taxi m = (Taxi)model;
		this.name.setValue(m.getName());
		this.size.setValue(m.getSize());
		this.startDate.setValue(m.getStartDate());
		Logger.info("AA: " + this.name);
	}
}
