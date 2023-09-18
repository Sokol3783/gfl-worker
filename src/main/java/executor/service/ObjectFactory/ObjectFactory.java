package executor.service.ObjectFactory;

public interface ObjectFactory {
    <T> T create(Class<T> clazz) ;
}
