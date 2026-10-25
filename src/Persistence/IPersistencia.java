package Persistence;

import java.nio.file.Path;
import java.util.Optional;

import Model.Red;

public interface IPersistencia {

	Red cargar(Path archivo);
	void guardar(Red red, Path archivo);
}

