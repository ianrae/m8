package mef.validate;
import models.Sample;

import org.mef.framework.metadata.*;
//import org.mef.framework.metadata.validate.ValContext;
import org.mef.framework.metadata.validate.ValContext;

public class SampleTwixt implements ValueContainer
{
	public StringValue name;
	
	public SampleTwixt()
	{
		this("");
	}
	
	public SampleTwixt(String namex)
	{
		this.name = new StringValue(namex);
	}

	@Override
	public void validateContainer(ValContext arg0) 
	{
		arg0.validate(name);
	}
	
	@Override 
	public void copyTo(Object model)
	{
		Sample m = (Sample)model;
		m.setName(this.name.get());
	}

	@Override 
	public void copyFrom(Object model)
	{
		Sample m = (Sample)model;
		this.name.set(m.getName());
	}
}
