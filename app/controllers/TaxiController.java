package controllers;

import java.lang.reflect.Field;

import javax.inject.Inject;

import org.springframework.util.ReflectionUtils;

import mef.validate.TaxiTwixt;
import models.Taxi;
import models.dao.TaxiDAO;
import play.data.Form;
import play.mvc.Call;
import play.twirl.api.Content;
import play.utils.meta.FieldMetadata;
import play.utils.meta.convert.StringConverter;

public class TaxiController extends TwixtController<Long, Taxi, TaxiTwixt> {
	
	FieldMetadata meta;
	
	@Inject
	public TaxiController(TaxiDAO dao) 
	{
		super(dao, Long.class, Taxi.class, TaxiTwixt.class, 10, "name");
		
		Field f = ReflectionUtils.findField(TaxiTwixt.class, "ball");
		meta = new FieldMetadata(f, null); //new StringConverter());
		
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
