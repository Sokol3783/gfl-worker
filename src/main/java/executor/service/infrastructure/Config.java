package executor.service.infrastructure;


//TODO RENAME CLASS AND CHANGE HIS FUNCTIONALITY
public interface Config {

        <T> Class<? extends T> getImplClass(Class<T> ifc);

}
