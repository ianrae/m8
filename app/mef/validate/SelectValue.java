package mef.validate;

import java.util.Map;

import org.mef.framework.metadata.StringValue;
import org.mef.framework.metadata.StringValueAndValidator;
import org.mef.framework.metadata.validate.ValidationErrors;

public class SelectValue extends StringValueAndValidator 
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
		super(id, "select");
		this.options = options;
	}

	public SelectValue(Long id, Map<String,String> options) 
	{
		super(id.toString(), "select");
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
	public boolean validate(Object val, ValidationErrors errors) 
	{
		if (options == null)
		{
			return true;
		}
		
		String id = (String)val;
		boolean b = options.containsKey(id);
		if (!b)
		{
			errors.addError("select: unknown id: " + id);
		}
		return b;
	}
	
}