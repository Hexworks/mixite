package biz.pavonis.hexameter.exception;

public class HexagonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2262788570507184567L;

	public HexagonNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public HexagonNotFoundException(String message) {
		super(message);
	}

}
