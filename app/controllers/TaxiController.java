package controllers;

import java.lang.reflect.Field;
import java.util.Map;

import javax.inject.Inject;









import org.mef.twixt.controllers.DynamicTwixtController;

import com.avaje.ebean.Page;

import mef.validate.TaxiTwixt;
import models.Taxi;
import models.dao.TaxiDAO;
import play.Logger;
import play.mvc.Call;
import play.twirl.api.Content;
import play.utils.crud.CRUDManager;
import play.utils.meta.FieldMetadata;
import play.utils.meta.ModelMetadata;
import play.utils.meta.form.FormFieldWidget;
import play.utils.meta.form.TextAreaWidget;

public class TaxiController extends DynamicTwixtController<Long, Taxi, TaxiTwixt> 
{
	@Inject
	public TaxiController(TaxiDAO dao) 
	{
		super(dao, Long.class, Taxi.class, TaxiTwixt.class, 10, "name");
		templatePackageName = "views.html.taxi.";
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
	protected Content renderList(Page p) {
//		try {
//			return super.renderList(p);
//		} catch (TemplateNotFoundException e) {
//			// use dynamic template
//		if (log.isDebugEnabled())
		
		CRUDManager m = CRUDManager.getInstance();
		ModelMetadata model = m.getMetadata(Taxi.class);
		Logger.debug("Rendering dynamic xLIST template for model : " + model);
		return play.utils.crud.views.html.list.render(model, model.getFields().values(), p);
	}
	
	
	@Override
	protected String templateForList() {
		return  genTemplate("zzList");
	}

	@Override
	protected String templateForForm() {
		return  genTemplate("Form");
	}

	@Override
	protected String templateForShow() {
		return  genTemplate("Show");
	}
	

	
	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
}
