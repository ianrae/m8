package controllers;

import static play.data.Form.*;

import javax.inject.Inject;

import mef.validate.SampleTwixt;
import models.Sample;
import models.dao.SampleDAO;
import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.twirl.api.Content;
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
	
	
	
	protected Content xrenderForm(Long key, Form<SampleTwixt> form) {
		String formName = "SampleForm";
		return render(formName, with(Long.class, key).and(SampleTwixt.class, form));
	}
	
	private static ALogger xlog = Logger.of(CRUDController.class);
	
	public Result newForm() {
		if (xlog.isDebugEnabled())
			xlog.debug("xnewForm() <-");
		Form<SampleTwixt> form =  Form.form(SampleTwixt.class);

		return ok(xrenderForm(null, form));
	}	
	
	@Override
	public Result create() {
		if (xlog.isDebugEnabled())
			xlog.debug("Xcreate() <-");

		Form<SampleTwixt> form =  Form.form(SampleTwixt.class);
		
		Form<SampleTwixt> filledForm = form.bindFromRequest();
		if (filledForm.hasErrors()) {
			if (xlog.isDebugEnabled())
				xlog.debug("Xvalidation errors occured: " + filledForm.errors());

			return badRequest(xrenderForm(null, filledForm));
		} else {
			SampleTwixt twixt = filledForm.get();
			Sample model = new Sample();
			model.setName(twixt.name.get());
			
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
