package mef.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mef.framework.metadata.Value;
import org.mef.framework.metadata.ValueContainer;
import org.mef.framework.metadata.validate.ValContext;
import org.springframework.util.ReflectionUtils;

public abstract class TwixtForm implements ValueContainer, ReflectionUtils.FieldCallback
{
	boolean inCtor;
	private Object modelToCopyFrom;
	
	public TwixtForm()
	{}

	protected void initFields()
	{
		inCtor = true;
		ReflectionUtils.doWithFields(this.getClass(), this, ReflectionUtils.COPYABLE_FIELDS);
	}

	@Override
	public void doWith(Field field)
	{
		if (inCtor)
		{
			initField(field);
		}
		else if (modelToCopyFrom != null)
		{
			copyFieldFromModel(field);
		}
	}
	
	private void initField(Field field)
	{
		Class<?> clazz = field.getType();
		if (Value.class.isAssignableFrom(clazz))
		{
			try 
			{
				Object obj = clazz.newInstance();
				field.setAccessible(true);
				field.set(this, obj);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	private void copyFieldFromModel(Field field)
	{
		Class<?> clazz = field.getType();
		if (Value.class.isAssignableFrom(clazz))
		{
			try 
			{
				String getFnName = "get" + uppify(field.getName());
				Method meth = ReflectionUtils.findMethod(this.modelToCopyFrom.getClass(), getFnName);
				if (meth != null)
				{
					Object src = meth.invoke(this.modelToCopyFrom);
					
					getFnName = "forceValueObject";
					meth = ReflectionUtils.findMethod(clazz, getFnName, Object.class);
					
					field.setAccessible(true);
					Object valueObj = field.get(this);
					
					meth.invoke(valueObj, src);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private String uppify(String name) 
	{
		String upper = name.toUpperCase();
		String s = upper.substring(0, 1);
		s += name.substring(1);
		return s;
	}	

	@Override
	public abstract void copyFrom(Object model);

	@Override
	public abstract void copyTo(Object model);

	@Override
	public void validateContainer(ValContext arg0) 
	{
	}

	//helpers
	protected void copyFieldsFrom(Object model)
	{
		inCtor = false;
		modelToCopyFrom = model;
		ReflectionUtils.doWithFields(this.getClass(), this, ReflectionUtils.COPYABLE_FIELDS);
	}
	protected void copyFieldsTo(Object model)
	{}
	
}
