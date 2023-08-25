package executor.service.service.di;

import java.util.Map;

public class Application {
    public static ApplicationContext run(Map<Class, Class> ifc2ImplClass) {
        JavaConfig config = new JavaConfig(ifc2ImplClass);
        ApplicationContext context = new ApplicationContext(config);
        ObjectFactory objectFactory = new ObjectFactory(context);

        context.setFactory(objectFactory);
        return context;
    }
}
