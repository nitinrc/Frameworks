package com.star.core;

import com.star.Runner;
import com.star.pages.*;
import com.star.suites.Tests;

public class BeanContextRegister {
    public BeanContextRegister() {
        Config.context.refresh();
        Config.context.register(Runner.class);
        Config.context.register(ResultStatus.class);
        Config.context.register(Driver.class);
        Config.context.register(DataFetch.class);
        Config.context.register(BrowserConfig.class);
        Config.context.register(MultiThreading.class);
        Config.context.register(WebActionsImpl.class);
        Config.context.register(ReadPropertyFile.class);
        Config.context.register(Common.class);
        Config.context.register(Alerts.class);
        Config.context.register(SearchImpl.class);
        Config.context.register(FlightsImpl.class);
        Config.context.register(BookImpl.class);
        Config.context.register(Tests.class);
    }
}
