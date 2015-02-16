package mef.validate;

import java.util.HashMap;
import java.util.Map;

public class AccountType extends SelectValue
{
	public AccountType(Long id) 
	{
		super(id);
		this.options = accountTypes();
	}

	private static Map<String,String> accountTypes()
	{
		Map<String,String> map = new HashMap<String,String>();
		map.put("1", "normal");
		map.put("2", "premium");
		map.put("3", "executive");
		return map;
	}		
}