package controllers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
	public void doWith(Field arg0) throws IllegalArgumentException,
	IllegalAccessException 
	{
		addFieldToMetaL(arg0.getName());
	}

	@Override
	public boolean matches(Field arg0) 
	{
		return (Value.class.isAssignableFrom(arg0.getType()));
	}	
	
	private void addFieldToMetaL(String fieldName)
	{
		Field f = ReflectionUtils.findField(twixtClass, fieldName);
		FieldMetadata meta = new FieldMetadata(f, null); //new StringConverter());
		TextWidget w = new TextWidget(meta);
		try {
			forceSetWidget(meta, w);
			Logger.info("ffff: " + fieldName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		metaL.add(meta);
	}

	private void forceSetWidget(FieldMetadata meta2, TextWidget w) throws Exception 
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
