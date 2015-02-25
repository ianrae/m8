package mef.validate;

import java.lang.reflect.Field;

import org.mef.framework.metadata.Value;
import org.mef.framework.metadata.ValueContainer;
import org.mef.framework.metadata.validate.ValContext;
import org.springframework.util.ReflectionUtils;

public abstract class TwixtForm implements ValueContainer
{
	private class Facade implements  FormCopier.FieldCopier, ReflectionUtils.FieldCallback
	{
		@Override
		public void doWith(Field field)
		{
			Class<?> clazz = field.getType();
			if (Value.class.isAssignableFrom(clazz))
			{
				try 
				{
					field.setAccessible(true);
					if (field.get(TwixtForm.this) != null)
					{
						return; //skip ones that are already not null
					}
					
					Object obj = clazz.newInstance();
					field.set(TwixtForm.this, obj);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
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
	
	private Facade _facade = new Facade(); //avoid name clash, use _
	
	public TwixtForm()
	{}

	protected void initFields()
	{
		ReflectionUtils.doWithFields(this.getClass(), _facade, ReflectionUtils.COPYABLE_FIELDS);
	}

	
	@Override
	public void copyFrom(Object model) 
	{
		this.copyFieldsFromModel(model);
	}

	@Override
	public void copyTo(Object model) 
	{
		this.copyFieldsToModel(model);
	}
	
	protected void copyFieldsFromModel(Object model, String... fieldsToNotCopy) 
	{
		FormCopier copier = new FormCopier(_facade);
		copier.copyFromModel(model, this, fieldsToNotCopy);
	}
	protected void copyFieldsToModel(Object model, String... fieldsToNotCopy) 
	{
		FormCopier copier = new FormCopier(_facade);
		copier.copyToModel(this, model, fieldsToNotCopy);
	}
	

	@Override
	public void validateContainer(ValContext arg0) 
	{
	}
	
}
