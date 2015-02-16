package mef.validate;
import org.mef.framework.metadata.*;
import org.mef.framework.metadata.validate.ValContext;

public class SampleTwixt implements ValueContainer
{
	public StringValue name;
	
	public SampleTwixt(String namex)
	{
		this.name = new StringValue(namex);
	}

	@Override
	public void validateContainer(ValContext arg0) 
	{
		arg0.validate(name);
	}

}
