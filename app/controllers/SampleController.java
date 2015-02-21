package controllers;

import javax.inject.Inject;

import mef.validate.SampleTwixt;
import models.Sample;
import models.dao.SampleDAO;
import play.mvc.Call;

public class SampleController extends TwixtController<Long, Sample, SampleTwixt> {
	
	@Inject
	public SampleController(SampleDAO dao) 
	{
		super(dao, Long.class, Sample.class, SampleTwixt.class, 10, "name");
	}


	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
}
