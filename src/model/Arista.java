package model;

import java.util.Objects;

public class Arista {

	private final int origen;
	private final int destino;

	public Arista(int origen, int destino) {
		this.origen = origen;
		this.destino = destino;
	}

	public int getOrigen() {
		return origen;
	}

	public int getDestino() {
		return destino;
	}
}