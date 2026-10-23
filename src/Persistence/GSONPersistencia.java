
package Persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Model.Conjunto;

public class GSONPersistencia implements IPersistencia {

	private static final Path DATA_DIR = Paths.get("data");
	private static final Path FILE_PATH = DATA_DIR.resolve("Datos.json");
	private final Gson gson;

	public GSONPersistencia() {
		this.gson = new GsonBuilder().setPrettyPrinting().create();

		try {
			Files.createDirectories(DATA_DIR);
		} catch (IOException e) {
			throw new RuntimeException(
					"No se pudo crear la carpeta de datos.", e);
		}
	}

	@Override
	public Optional<Conjunto> cargar() {
		return cargar(FILE_PATH);
	}

	@Override
	public Optional<Conjunto> cargar(Path archivo) {
		if (!Files.exists(archivo) || !Files.isRegularFile(archivo)) {
			return Optional.empty();
		}

		try (Reader reader = Files.newBufferedReader(archivo)) {
			Conjunto conjunto = gson.fromJson(reader, Conjunto.class);
			return Optional.ofNullable(conjunto);

		} catch (IOException | com.google.gson.JsonParseException e) {
			throw new RuntimeException(
					"No se pudo cargar el archivo: " + archivo, e);
		}
	}

	@Override
	public void guardar(Conjunto conjunto) {
		if (conjunto == null) {
			throw new IllegalArgumentException(
					"No se puede guardar un conjunto nulo.");
		}
		try {
			Files.createDirectories(DATA_DIR);

			try (Writer writer = Files.newBufferedWriter(FILE_PATH)) {
				gson.toJson(conjunto, writer);
			}

		} catch (IOException e) {
			throw new RuntimeException(
					"No se pudo guardar el archivo: " + FILE_PATH, e);
		}
	}
}

