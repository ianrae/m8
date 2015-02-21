package controllers;

import javax.inject.Inject;

import mef.validate.TaxiTwixt;
import models.Taxi;
import models.dao.TaxiDAO;
import play.mvc.Call;

public class TaxiController extends TwixtController<Long, Taxi, TaxiTwixt> {
	
	@Inject
	public TaxiController(TaxiDAO dao) 
	{
		super(dao, Long.class, Taxi.class, TaxiTwixt.class, 10, "name");
	}


	@Override
	protected Call toIndex() {
		return routes.Application.index();
	}
	
}
