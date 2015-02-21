package controllers;

import static play.data.Form.*;

import java.lang.reflect.Method;

import javax.inject.Inject;

import org.springframework.util.ReflectionUtils;

import mef.validate.SampleTwixt;
import mef.validate.ValueContainerBinder;
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

public class SampleController extends TwixtController<Long, Sample, SampleTwixt> {
	
	@Inject
	public SampleController(SampleDAO dao) 
	{
		super(dao, Long.class, Sample.class, SampleTwixt.class, 10, "name");
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
	
}
