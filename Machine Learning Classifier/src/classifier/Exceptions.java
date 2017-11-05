package classifier;

public final class Exceptions {
	private Exceptions(){}
}

class NotImplementedException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "The called method has not yet been implemented! Please try one of the implemented methods.";

	public NotImplementedException() {super(MESSAGE);}

    @SuppressWarnings("unused")
	private NotImplementedException(String message) {super(message); }
    
    @SuppressWarnings("unused")
    private NotImplementedException(Throwable cause) { super(cause); }

    @SuppressWarnings("unused")
    private NotImplementedException(String message, Throwable cause) { super(message, cause); }
    
}