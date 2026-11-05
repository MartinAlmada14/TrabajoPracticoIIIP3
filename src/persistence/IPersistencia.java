package persistence;

import java.nio.file.Path;

import model.RedDeGrafos;

public interface IPersistencia {
	RedDeGrafos cargar(Path archivo);
	void guardar(RedDeGrafos red, Path archivo);
}