package springBeans;

import pages.Alerts;
import pages.BookPage;
import pages.FlightsPage;
import pages.SearchPage;
import selenium.framework.App;
import selenium.framework.DataFetch;
import selenium.framework.DesiredCapabilities;
import selenium.framework.MultiThreadingExecutorService;
import selenium.framework.WebActions;
import tests.Runner;

public class ContextRegister {
	public ContextRegister() {
		FlightBookingConfig.context.refresh();
		FlightBookingConfig.context.register(App.class);
		FlightBookingConfig.context.register(DataFetch.class);
		FlightBookingConfig.context.register(DesiredCapabilities.class);
		FlightBookingConfig.context.register(Runner.class);
		FlightBookingConfig.context.register(MultiThreadingExecutorService.class);
		FlightBookingConfig.context.register(WebActions.class);
		FlightBookingConfig.context.register(Alerts.class);
		FlightBookingConfig.context.register(SearchPage.class);
		FlightBookingConfig.context.register(FlightsPage.class);
		FlightBookingConfig.context.register(BookPage.class);
	}
}
