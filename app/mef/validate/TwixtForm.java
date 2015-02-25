package mef.validate;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.mef.framework.metadata.Value;
import org.mef.framework.metadata.ValueContainer;
import org.mef.framework.metadata.validate.ValContext;
import org.springframework.util.ReflectionUtils;

public abstract class TwixtForm implements ValueContainer, ReflectionUtils.FieldCallback, FormCopier.FieldCopier
{
	public TwixtForm()
	{}

	protected void initFields()
	{
		ReflectionUtils.doWithFields(this.getClass(), this, ReflectionUtils.COPYABLE_FIELDS);
	}

	@Override
	public void doWith(Field field)
	{
		Class<?> clazz = field.getType();
		if (Value.class.isAssignableFrom(clazz))
		{
			try 
			{
				field.setAccessible(true);
				if (field.get(this) != null)
				{
					return; //skip ones that are already not null
				}
				
				Object obj = clazz.newInstance();
				field.set(this, obj);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
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
		FormCopier copier = new FormCopier(this);
		copier.copyFromModel(model, this);
	}
	protected void copyFieldsTo(Object model)
	{
		FormCopier copier = new FormCopier(this);
		copier.copyToModel(this, model);
	}
	
	
	@Override
	public void copyFieldFromModel(FormCopier copier, Field field)
	{
		copier.copyFieldFromModel(field);
	}
	@Override
	public void copyFieldToModel(FormCopier copier, Field field)
	{
		copier.copyFieldToModel(field);
	}

}
