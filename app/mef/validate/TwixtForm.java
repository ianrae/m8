package mef.validate;

import java.lang.reflect.Field;

import org.mef.framework.metadata.Value;
import org.mef.framework.metadata.ValueContainer;
import org.mef.framework.metadata.validate.ValContext;
import org.springframework.util.ReflectionUtils;

public abstract class TwixtForm implements ValueContainer, ReflectionUtils.FieldCallback
{
	public TwixtForm()
	{}

	protected void initDeclaredFields()
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

	@Override
	public abstract void copyFrom(Object model);

	@Override
	public abstract void copyTo(Object model);

	protected void copyFieldsFrom(Object model)
	{}
	protected void copyFieldsTo(Object model)
	{}

	@Override
	public void validateContainer(ValContext arg0) 
	{
	}

}
