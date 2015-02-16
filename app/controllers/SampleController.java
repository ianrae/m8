package controllers;

import static play.data.Form.*;

import javax.inject.Inject;

import models.Sample;
import models.dao.SampleDAO;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.utils.crud.CRUDController;
import play.utils.crud.Parameters;
import play.utils.dao.DAO;

public class SampleController extends CRUDController<Long, Sample> {
	
	@Inject
	public SampleController(SampleDAO dao) {
		super(dao, form(Sample.class), Long.class, Sample.class, 10, "name");
	}

	@Override
	protected String templateForList() {
		return "sampleList";
	}

	@Override
	protected String templateForForm() {
		return "sampleForm";
	}

	@Override
	protected String templateForShow() {
		return "sampleShow";
	}

	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
	
//	@Override
//	protected Result badRequest(String template, Parameters params) {
//		return badRequest(render(template, params));
//	}
	
	
	private static ALogger xlog = Logger.of(CRUDController.class);
	
	@Override
	public Result create() {
		if (xlog.isDebugEnabled())
			xlog.debug("Xcreate() <-");

		Form<Sample> form =  Form.form(Sample.class);
		
		Form<Sample> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (xlog.isDebugEnabled())
				xlog.debug("Xvalidation errors occured: " + filledForm.errors());

			return badRequest(renderForm(null, filledForm));
		} else {
			Sample model = filledForm.get();
			
			DAO<Long,Sample> dao = getDao();
			dao.create(model);
			if (xlog.isDebugEnabled())
				xlog.debug("Xentity created");

			Call index = toIndex();
			if (xlog.isDebugEnabled())
				xlog.debug("Xindex : " + index);
			return redirect(index);
		}
	}	
}
