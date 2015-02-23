package controllers;

import java.lang.reflect.Field;

import javax.inject.Inject;

import mef.validate.TaxiTwixt;
import models.Taxi;
import models.dao.TaxiDAO;

import org.springframework.util.ReflectionUtils;

import play.data.Form;
import play.mvc.Call;
import play.twirl.api.Content;
import play.utils.meta.FieldMetadata;
import play.utils.meta.form.TextWidget;

public class TaxiController extends TwixtController<Long, Taxi, TaxiTwixt> 
{
	FieldMetadata meta;
	
	@Inject
	public TaxiController(TaxiDAO dao) 
	{
		super(dao, Long.class, Taxi.class, TaxiTwixt.class, 10, "name");
		
		Field f = ReflectionUtils.findField(TaxiTwixt.class, "name");
		meta = new FieldMetadata(f, null); //new StringConverter());
		
		TextWidget w = new TextWidget(meta);
		try {
			forceSetWidget(meta, w);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void forceSetWidget(FieldMetadata meta2, TextWidget w) throws Exception 
	{
		Field f = ReflectionUtils.findField(meta2.getClass(), "widget");
		f.setAccessible(true); //force!
		f.set(meta2, w);
	}

	@Override
	protected Content xrenderForm(Long key, Form<TaxiTwixt> form) 
	{
		return render(templateForForm(), with(getKeyClass(), key).and(Form.class, form).and(FieldMetadata.class, meta));
	}	
	
	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
}
