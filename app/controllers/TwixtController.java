package controllers;

import static play.data.Form.form;

import javax.inject.Inject;

import mef.validate.ValueContainerBinder;

import org.mef.framework.metadata.ValueContainer;

import play.Logger;
import play.Logger.ALogger;
import play.data.Form;
import play.mvc.Call;
import play.mvc.Result;
import play.twirl.api.Content;
import play.utils.crud.CRUDController;
import play.utils.dao.BasicModel;
import play.utils.dao.DAO;

public abstract class TwixtController<K,  M extends BasicModel<K>,T extends ValueContainer> extends CRUDController<K, M> 
{
	private Class<T> twixtClass;
	
	@Inject
	public TwixtController(DAO<K, M> dao, Class<K> keyClass, Class<M> modelClass, Class<T>twixtClass, int pageSize, String orderBy) {
		super(dao, form(modelClass), keyClass, modelClass, pageSize, orderBy);
		this.twixtClass = twixtClass;
	}

	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
	
	private static ALogger xlog = Logger.of(CRUDController.class);
	
	protected Content xrenderForm(K key, Form<T> form) 
	{
		return render(templateForForm(), with(getKeyClass(), key).and(Form.class, form));
	}
	
	
	public Result newForm() {
		if (xlog.isDebugEnabled())
			xlog.debug("xnewForm() <-");
		Form<T> form =  Form.form(twixtClass);

		return ok(xrenderForm(null, form));
	}	
	
	@Override
	public Result create()
	{
		if (xlog.isDebugEnabled())
			xlog.debug("ccXcreate() <-");
		
		ValueContainerBinder<T> binder = new ValueContainerBinder<T>(twixtClass);
		boolean b = binder.bind();
		Form<T> filledForm = binder.getForm();
		
		if (!b) {
			if (xlog.isDebugEnabled())
				xlog.debug("Xvalidation errors occured: " + binder.getValidationErrors()); // filledForm.errors());

			return badRequest(xrenderForm(null, filledForm));
		} else {
			T twixt = filledForm.get();
			M model = createModel();
			if (model == null)
			{
				return badRequest(xrenderForm(null, filledForm));
			}
			
			copyDataToModel(twixt, model);
			
			DAO<K,M> dao = getDao();
			dao.create(model);
			if (xlog.isDebugEnabled())
				xlog.debug("Xentity created");

			Call index = toIndex();
			if (xlog.isDebugEnabled())
				xlog.debug("Xindex : " + index);
			return redirect(index);
		}
	}
		
	public Result editForm(K key) {
		if (xlog.isDebugEnabled())
			xlog.debug("editForm() <-" + key);

		M model = this.getDao().get(key);
		if (xlog.isDebugEnabled())
			xlog.debug("model : " + model);

		T twixt = createTwixt();
		this.copyDataFromModel(model, twixt);
		Form<T> frm = Form.form(this.twixtClass).fill(twixt);
		return ok(xrenderForm(key, frm));
	}

	public Result update(K key) {
		if (xlog.isDebugEnabled())
			xlog.debug("update() <-" + key);

		M original = getDao().get(key);
		
		ValueContainerBinder<T> binder = new ValueContainerBinder<T>(twixtClass);
		boolean b = binder.bind();
		Form<T> filledForm = binder.getForm();
		
		if (! b) {
			if (xlog.isDebugEnabled())
				xlog.debug("validation errors occured: " + binder.getValidationErrors());

			return badRequest(xrenderForm(key, filledForm));
		} else {
			T twixt = filledForm.get();		
			this.copyDataToModel(twixt, original);
			if (xlog.isDebugEnabled())
				xlog.debug("model : " + original);
			getDao().update(original);
			if (xlog.isDebugEnabled())
				xlog.debug("entity updated");

			Call index = toIndex();
			if (xlog.isDebugEnabled())
				xlog.debug("index : " + index);
			return redirect(index);
		}
	}
	
	
	
	protected abstract void copyDataToModel(T twixt, M model);
	protected abstract void copyDataFromModel(M model, T twixt);
	
	protected M createModel()
	{
		M model = null;
		try {
			model = this.getModelClass().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	protected T createTwixt()
	{
		T twixt = null;
		try {
			twixt = this.twixtClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return twixt;
	}

	
}