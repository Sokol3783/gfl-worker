package executor.service.service.di;

public interface Config {

    <T> Class<? extends T> getImplClass(Class<T> ifc);

}
