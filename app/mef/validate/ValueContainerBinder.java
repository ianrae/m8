package mef.validate;

import java.util.List;
import java.util.Map;

import org.mef.framework.binder.IFormBinder;
import org.mef.framework.metadata.ValueContainer;
import org.mef.framework.metadata.validate.ValidationErrorSpec;

import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.ValidationError;

public class ValueContainerBinder<T extends ValueContainer> implements IFormBinder<T>
	{
		private T entity;
		private Class<T> clazz;
		TwixtBinder binder = new TwixtBinder();
		private DynamicForm form;

		public ValueContainerBinder(Class<T> clazz)
		{
			this.clazz = clazz;
		}
		
		@Override
		public boolean bind() 
		{
			DynamicForm form = Form.form().bindFromRequest();
			return bindFromDynamicForm(form);
		}
		protected boolean bindFromMap(Map<String,String> anyData)
		{
			DynamicForm form = Form.form().bind(anyData);
			return bindFromDynamicForm(form);
		}
		
		private T blankInstance() 
		{
	        try {
	            return (T) clazz.newInstance();
	        } catch(Exception e) {
	            throw new RuntimeException("Cannot instantiate " + clazz + ". It must have a default constructor", e);
	        }
	    }		
		protected boolean bindFromDynamicForm(DynamicForm form)
		{
			this.entity = blankInstance(); //throw exception if fails
			this.form = form;
			
			boolean b = binder.bind(entity, form.data());
			
			//get errors (in getForm)
			return b;
		}

		@Override
		public T get() 
		{
			return entity;
		}

		@Override
		public Object getValidationErrors() 
		{
			Form<T> frm = getForm();
			return frm.errors();
		}

		@Override
		public Form<T> getForm()
		{
			Form<T> frm;
			if (this.entity != null)
			{
				frm = this.fillForm(this.entity); //copy over field values
			}
			else
			{
				frm = Form.form(clazz);
			}
			
			//and add errors
			Map<String, List<ValidationErrorSpec>> errorMap = binder.getErrors();
			for(String key : errorMap.keySet())
			{
				List<ValidationErrorSpec> specL = errorMap.get(key);
				for(ValidationErrorSpec spec : specL)
				{
					ValidationError error = new ValidationError(spec.key, spec.message);
					frm.reject(error);
				}
			}
			
			return frm;
		}
		
		public Form<T> fillForm(T input)
		{
			Form<T> form = Form.form(clazz);
			form = form.fill(input);
			return form;
		}
	}