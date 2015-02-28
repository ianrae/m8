package mef.validate;

import java.util.Map;

import org.mef.framework.metadata.StringValue;
import org.mef.framework.metadata.validate.ValContext;

public class SelectValue extends StringValue
{
	protected Map<String, String> options;

	public SelectValue(String id) 
	{
		this(id, null);
	}
	public SelectValue(Long id) 
	{
		this(id, null);
	}
	public SelectValue(String id, Map<String,String> options) 
	{
		super(id);
		this.options = options;
	}

	public SelectValue(Long id, Map<String,String> options) 
	{
		super(id.toString());
		this.options = options;
	}
	
	public Map<String,String> options()
	{
		return options;
	}
	public void setOptions(Map<String,String> map)
	{
		options = map;
	}
	
	@Override
	public boolean validate(ValContext valctx)
	{
		if (options == null)
		{
			return true;
		}
		
		String id = get();
		boolean b = options.containsKey(id);
		if (!b)
		{
			valctx.addError("select: unknown id: " + id);
		}
		return b;
	}
	
}