package controllers;

import static play.data.Form.*;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.springframework.util.ReflectionUtils;

import mef.validate.SampleTwixt;
import models.Sample;
import models.dao.SampleDAO;
import play.Logger;
import play.Logger.ALogger;
import play.api.Play;
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
		return "SampleForm";
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
	
	protected Content xrenderForm(Long key, Form<SampleTwixt> form) {
		
//		try {
//			xlog.debug("A");
////			xcall("views.html.SampleForm", "render", with(Long.class, key).and(SampleTwixt.class, form));
//			xlog.debug("A");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MethodNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return render(templateForForm(), with(Long.class, key).and(Form.class, form));
	}
	
	
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
	
	
	@SuppressWarnings("unchecked")
	protected <R> R xcall(String className, String methodName, Parameters params)
			throws ClassNotFoundException, MethodNotFoundException {
		if (xlog.isDebugEnabled())
			xlog.debug("xxxcall <-");
		if (xlog.isDebugEnabled())
			xlog.debug("className : " + className);
		if (xlog.isDebugEnabled())
			xlog.debug("methodName : " + methodName);
		if (xlog.isDebugEnabled())
			xlog.debug("params : " + params);
		
		ClassLoader appClassloader = Play.classloader(Play.current());		
		ClassLoader cl = appClassloader; //classLoader();
		Object result = null;
		Class<?> clazz = cl.loadClass(className);
		if (xlog.isDebugEnabled())
			xlog.debug("clazz : " + clazz);
		
		for(Method mm : clazz.getMethods())
		{
			xlog.debug(mm.toString());
		}

		Class<?>[] paramTypes = params.types();
		Object[] paramValues = params.values();
		Method method = ReflectionUtils.findMethod(clazz, methodName,
				paramTypes);
		if (xlog.isDebugEnabled())
			xlog.debug("method : " + method);

		if (method == null) {
			throw new MethodNotFoundException(className, methodName, paramTypes);
		}
		result = null;
		return (R) result;
	}	
}
