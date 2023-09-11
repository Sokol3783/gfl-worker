package executor.service.DIFactory;

public interface ObjectFactory {
    <T> T create(Class<T> clazz) ;
}
