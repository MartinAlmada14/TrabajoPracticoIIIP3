package Model;

import java.util.Objects;

public class Arista {

	private final int origen;
	private final int destino;
	//private double peso;
	
	public Arista(int origen, int destino) {
		this.origen = origen;
		this.destino = destino;
		//this.peso = peso;
	}

	public int getOrigen() {
		return origen;
	}

	public int getDestino() {
		return destino;
	}

	/*public double getPeso() {
		return peso;
	}*/

	/*@Override
	public int compareTo(Arista otraArista) {
		return Double.compare(this.peso, otraArista.peso);
	}*/
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof Arista)) {
			return false;
		}
		Arista otra = (Arista) o;
		return (origen == otra.origen && destino == otra.destino) || (origen == otra.destino && destino == otra.origen);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(Math.min(origen, destino), Math.max(origen, destino));
	}
	
	@Override
	public String toString() {
		return "(" + origen + "," + destino + ")";
	}
	
	
}