package controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.mef.framework.metadata.BooleanValue;
import org.mef.framework.metadata.DateValue;
import org.mef.framework.metadata.IntegerValue;
import org.mef.framework.metadata.LongSelectValue;
import org.mef.framework.metadata.SelectValue;
import org.mef.framework.metadata.Value;
import org.mef.framework.metadata.ValueContainer;
import org.springframework.util.ReflectionUtils;

import play.Logger;
import play.data.Form;
import play.mvc.Call;
import play.twirl.api.Content;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;
import play.utils.meta.FieldMetadata;
import play.utils.meta.form.CheckboxWidget;
import play.utils.meta.form.DateWidget;
import play.utils.meta.form.FormFieldWidget;
import play.utils.meta.form.NumberWidget;
import play.utils.meta.form.SelectWidget;
import play.utils.meta.form.TextWidget;

public class DynamicTwixtController<K,  M extends BasicModel<K>,T extends ValueContainer> extends TwixtController<K, M,T> implements ReflectionUtils.FieldCallback, ReflectionUtils.FieldFilter
{
	List<FieldMetadata> metaL = new ArrayList<FieldMetadata>();

	public DynamicTwixtController(DAO<K, M> dao, Class<K> keyClass, Class<M> modelClass, Class<T>twixtClass, int pageSize, String orderBy) 
	{
		super(dao, keyClass, modelClass, twixtClass, pageSize, orderBy);
		ReflectionUtils.doWithFields(twixtClass, this, this);
	}

	@Override
	public void doWith(Field field) throws IllegalArgumentException,
	IllegalAccessException 
	{
		addFieldToMetaL(field);
	}

	@Override
	public boolean matches(Field arg0) 
	{
		return (Value.class.isAssignableFrom(arg0.getType()));
	}	

	private void addFieldToMetaL(Field f)
	{
		FieldMetadata meta = new FieldMetadata(f, null); //new StringConverter());
		FormFieldWidget w = createWidget(f, meta);
		try {
			forceSetWidget(meta, w);
			Logger.info("ffff: " + f.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		metaL.add(meta);
	}

	protected FormFieldWidget createWidget(Field f, FieldMetadata meta)
	{
		Class<?> clazz = f.getType();

		if (clazz.equals(IntegerValue.class))
		{
			return new NumberWidget(meta);
		}
		else if (clazz.equals(BooleanValue.class))
		{
			return new CheckboxWidget(meta);
		}
		else if (clazz.equals(DateValue.class))
		{
			return new DateWidget(meta); //yyyy-mm-dd
		}
		else if (clazz.isAssignableFrom(SelectValue.class))
		{
			return new SelectWidget(meta);
		}
		else if (LongSelectValue.class.isAssignableFrom(clazz))
		{
			return new SelectWidget(meta);
		}
		else
		{
			TextWidget w = new TextWidget(meta);
			return w;
		}
	}

	private void forceSetWidget(FieldMetadata meta2, FormFieldWidget w) throws Exception 
	{
		Field f = ReflectionUtils.findField(meta2.getClass(), "widget");
		f.setAccessible(true); //force!
		f.set(meta2, w);
	}

	@Override
	protected Content xrenderForm(K key, Form<T> form) 
	{
		return render(templateForForm(), with(getKeyClass(), key).and(Form.class, form).and(metaL.getClass(), metaL));
	}	

	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}

}
