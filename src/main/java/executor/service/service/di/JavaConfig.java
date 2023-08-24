package executor.service.service.di;

import java.util.Map;

public class JavaConfig implements Config {

    private Map<Class, Class> ifc2ImplClass;

    public JavaConfig(Map<Class, Class> ifc2ImplClass) {
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> ifc) {
        return ifc2ImplClass.get(ifc);
    }
}
