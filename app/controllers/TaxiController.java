package controllers;

import java.lang.reflect.Field;

import javax.inject.Inject;

import mef.validate.TaxiTwixt;
import models.Taxi;
import models.dao.TaxiDAO;
import play.mvc.Call;
import play.utils.meta.FieldMetadata;
import play.utils.meta.form.FormFieldWidget;
import play.utils.meta.form.TextAreaWidget;
import play.utils.meta.form.TextWidget;

public class TaxiController extends DynamicTwixtController<Long, Taxi, TaxiTwixt> 
{
	@Inject
	public TaxiController(TaxiDAO dao) 
	{
		super(dao, Long.class, Taxi.class, TaxiTwixt.class, 10, "name");
	}
	
	@Override
	protected FormFieldWidget createWidget(Field f, FieldMetadata meta)
	{
		if (f.getName().equals("name"))
		{
			TextAreaWidget w = new TextAreaWidget(meta);
			return w;
		}
		else
		{
			return super.createWidget(f, meta);
		}
	}
	

	
	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
}
