package executor.service.exceptions;

public class StepExecutionException extends RuntimeException {

  public StepExecutionException() {
    super();
  }

  public StepExecutionException(String message) {
    super(message);
  }

  public StepExecutionException(String message, Throwable cause) {
    super(message, cause);
  }

  public StepExecutionException(Throwable cause) {
    super(cause);
  }
}
