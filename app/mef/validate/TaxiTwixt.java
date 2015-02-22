package mef.validate;
import models.Taxi;

import org.mef.framework.metadata.*;
import org.mef.framework.metadata.validate.ValContext;

public class TaxiTwixt implements ValueContainer
{
	public StringValue name;
	public IntegerValue size;
	
	public String ball;
	
	public TaxiTwixt()
	{
		this("", 0);
	}
	
	public TaxiTwixt(String namex, int size)
	{
		this.name = new StringValue(namex);
		this.size = new IntegerValue(size);
	}

	@Override
	public void validateContainer(ValContext arg0) 
	{
		arg0.validate(name);
		arg0.validate(size);
	}
	
	@Override 
	public void copyTo(Object model)
	{
		Taxi m = (Taxi)model;
		m.setName(this.name.get());
		m.setSize(size.get());
	}

	@Override 
	public void copyFrom(Object model)
	{
		Taxi m = (Taxi)model;
		this.name.setValue(m.getName());
		this.size.setValue(m.getSize());
	}
}
