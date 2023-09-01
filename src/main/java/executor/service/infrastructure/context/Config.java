package executor.service.infrastructure.context;


//TODO RENAME CLASS AND CHANGE HIS FUNCTIONALITY
public interface Config {

  <T> Class<? extends T> getClass(Class<T> ifc) throws Exception;


}
