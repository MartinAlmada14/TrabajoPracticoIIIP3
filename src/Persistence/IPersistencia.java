package Persistence;

import java.nio.file.Path;
import java.util.Optional;

import Model.Conjunto;

public interface IPersistencia {

	Optional<Conjunto> cargar();

	Optional<Conjunto> cargar(Path archivo);

	void guardar(Conjunto conjunto);
}

