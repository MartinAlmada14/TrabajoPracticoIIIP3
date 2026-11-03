package persistence;

import java.nio.file.Path;

import model.Red2;

public interface IPersistencia {
	Red2 cargar(Path archivo);
	void guardar(Red2 red, Path archivo);
}