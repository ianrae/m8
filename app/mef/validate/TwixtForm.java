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
	private Object modelToCopyTo;

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
		else if (modelToCopyTo != null)
		{
			copyFieldToModel(field);
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
				String fnName = "get" + uppify(field.getName());
				Method meth = ReflectionUtils.findMethod(this.modelToCopyFrom.getClass(), fnName);
				if (meth != null)
				{
					Object src = meth.invoke(this.modelToCopyFrom);

					fnName = "forceValueObject";
					meth = ReflectionUtils.findMethod(clazz, fnName, Object.class);

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

	private void copyFieldToModel(Field field)
	{
		Class<?> clazz = field.getType();
		if (Value.class.isAssignableFrom(clazz))
		{
			try 
			{
				field.setAccessible(true);
				Object valueObj = field.get(this);

				String fnName = "getRawValue";
				Method meth = ReflectionUtils.findMethod(valueObj.getClass(), fnName);
				if (meth != null)
				{
					Object src = meth.invoke(valueObj);

					fnName = "set" + uppify(field.getName());
					meth = findMatchingMethod(field, src);
					if (meth != null)
					{
						meth.invoke(this.modelToCopyTo, src);
					}
				} 
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	private Method findMatchingMethod(Field field, Object src)
	{
		String fnName = "set" + uppify(field.getName());
		Method meth = ReflectionUtils.findMethod(this.modelToCopyTo.getClass(), fnName, src.getClass());
		if (meth != null)
		{
			return meth;
		}
		
		if (src.getClass().equals(Integer.class))
		{
			meth = ReflectionUtils.findMethod(this.modelToCopyTo.getClass(), fnName, int.class);
			if (meth != null)
			{
				return meth;
			}
		}
		else if (src.getClass().equals(Boolean.class))
		{
			meth = ReflectionUtils.findMethod(this.modelToCopyTo.getClass(), fnName, boolean.class);
			if (meth != null)
			{
				return meth;
			}
		}
		else if (src.getClass().equals(Long.class))
		{
			meth = ReflectionUtils.findMethod(this.modelToCopyTo.getClass(), fnName, long.class);
			if (meth != null)
			{
				return meth;
			}
		}
		else if (src.getClass().equals(Double.class))
		{
			meth = ReflectionUtils.findMethod(this.modelToCopyTo.getClass(), fnName, double.class);
			if (meth != null)
			{
				return meth;
			}
		}
		
		return null;
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
	{
		inCtor = false;
		modelToCopyTo = model;
		ReflectionUtils.doWithFields(this.getClass(), this, ReflectionUtils.COPYABLE_FIELDS);
	}

}
