package executor.service.objectfactory;

public interface ObjectFactory {
    <T> T create(Class<T> clazz) ;
}
