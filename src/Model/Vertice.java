package Model;

public class Vertice {
	private final String nombre;
	
	public Vertice(String nombre) {
		if(nombre == null || nombre.trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre del vertice no puede ser vacio");
		}
		this.nombre = nombre.trim();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
	
}
