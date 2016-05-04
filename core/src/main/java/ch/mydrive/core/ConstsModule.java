package ch.mydrive.core;

import org.apache.log4j.Logger;

import com.google.inject.AbstractModule;

public class ConstsModule extends AbstractModule{
	
	private static final Logger LOG = Logger.getLogger(ConstsModule.class);
	
	@Override
	protected void configure() {
		LOG.trace("Production Consts Module");
	}
}
