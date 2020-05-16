package myExceptions;

public class IncorrectValue extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String m1 = "Valor incorrecto: ";

	public IncorrectValue() {
		super(m1);
	}
	public IncorrectValue(String mensage){
		super(m1 + mensage + '\n');

	}
	
	public IncorrectValue(String mensage, Throwable cause){
		super(m1 + mensage + '\n', cause);
	}
}

