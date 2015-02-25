package mef.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mef.framework.metadata.Value;
import org.springframework.util.ReflectionUtils;

public class FormCopier implements ReflectionUtils.FieldCallback
{
	public interface FieldCopier
	{
		void copyFieldFromModel(FormCopier copier, Field field);
		void copyFieldToModel(FormCopier copier, Field field);
	}
	
	TwixtForm form;
	private Object modelToCopyFrom;
	private Object modelToCopyTo;
	private FieldCopier fieldCopier;

	public FormCopier(FieldCopier fieldCopier)
	{
		this.fieldCopier = fieldCopier;
	}
	
	public void copyToModel(TwixtForm twixtForm, Object model)
	{
		form = twixtForm;
		modelToCopyTo = model;
		ReflectionUtils.doWithFields(form.getClass(), this, ReflectionUtils.COPYABLE_FIELDS);
	}

	public void copyFromModel(Object model, TwixtForm twixtForm)
	{
		form = twixtForm;
		modelToCopyFrom = model;
		ReflectionUtils.doWithFields(form.getClass(), this, ReflectionUtils.COPYABLE_FIELDS);
	}

	@Override
	public void doWith(Field field)
	{
		if (modelToCopyFrom != null)
		{
			fieldCopier.copyFieldFromModel(this, field);
		}
		else if (modelToCopyTo != null)
		{
			fieldCopier.copyFieldToModel(this, field);
		}
	}

	public void copyFieldFromModel(Field field)
	{
		Class<?> clazz = field.getType();
		if (Value.class.isAssignableFrom(clazz))
		{
			try 
			{
				field.setAccessible(true);
				Object valueObj = field.get(form);
				
				String fnName = "get" + uppify(field.getName());
				Method meth = ReflectionUtils.findMethod(modelToCopyFrom.getClass(), fnName);
				if (meth != null)
				{
					Object src = meth.invoke(modelToCopyFrom);

					fnName = "forceValueObject";
					meth = ReflectionUtils.findMethod(clazz, fnName, Object.class);

					meth.invoke(valueObj, src);
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void copyFieldToModel(Field field)
	{
		Class<?> clazz = field.getType();
		if (Value.class.isAssignableFrom(clazz))
		{
			try 
			{
				field.setAccessible(true);
				Object valueObj = field.get(form);

				String fnName = "getRawValue";
				Method meth = ReflectionUtils.findMethod(valueObj.getClass(), fnName);
				if (meth != null)
				{
					Object src = meth.invoke(valueObj);

					fnName = "set" + uppify(field.getName());
					meth = findMatchingMethod(field, src);
					if (meth != null)
					{
						meth.invoke(modelToCopyTo, src);
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
		Method meth = ReflectionUtils.findMethod(modelToCopyTo.getClass(), fnName, src.getClass());
		if (meth != null)
		{
			return meth;
		}
		
		if (src.getClass().equals(Integer.class))
		{
			meth = ReflectionUtils.findMethod(modelToCopyTo.getClass(), fnName, int.class);
			if (meth != null)
			{
				return meth;
			}
		}
		else if (src.getClass().equals(Boolean.class))
		{
			meth = ReflectionUtils.findMethod(modelToCopyTo.getClass(), fnName, boolean.class);
			if (meth != null)
			{
				return meth;
			}
		}
		else if (src.getClass().equals(Long.class))
		{
			meth = ReflectionUtils.findMethod(modelToCopyTo.getClass(), fnName, long.class);
			if (meth != null)
			{
				return meth;
			}
		}
		else if (src.getClass().equals(Double.class))
		{
			meth = ReflectionUtils.findMethod(modelToCopyTo.getClass(), fnName, double.class);
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

}