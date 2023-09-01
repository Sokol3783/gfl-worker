package executor.service.infrastructure.context;

public interface ApplicationContext {

  <T> T getObject(Class<T> type) throws Exception;

}
