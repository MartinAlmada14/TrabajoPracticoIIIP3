package Model;

public class EjecucionCanceladaException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	
	public EjecucionCanceladaException() {
			super("La ejecucion fue cancelada por el usuario.");
	}

}
