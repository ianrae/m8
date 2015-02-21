package mef.validate;

import java.util.Map;

import org.mef.framework.metadata.ValueContainer;

public class MockValueContainerBinder<T extends ValueContainer> extends ValueContainerBinder<T>
	{
		private Map<String, String> mockData;

		public MockValueContainerBinder(Class<T> clazz, Map<String,String> anyData)
		{
			super(clazz);
			this.mockData = anyData;
		}
		
		@Override
		public boolean bind() 
		{
			return bindFromMap(mockData);
		}

	}