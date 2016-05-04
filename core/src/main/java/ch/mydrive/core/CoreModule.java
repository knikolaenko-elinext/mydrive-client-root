package ch.mydrive.core;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class CoreModule extends AbstractModule{
	@Override
	protected void configure() {
		install(new ConstsModule());
		bind(SessionService.class).to(SessionServiceImpl.class).in(Singleton.class);
	}
}
