package Persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import Model.Arista;
import Model.Red2;

public class GSONPersistencia implements IPersistencia {

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final Path ARCHIVO = Paths.get("Data", "grafoPrueba.json");

	private static class RedDTO {
		int cantidadVertices;
		List<int[]> aristas = new ArrayList<>();
	}

	@Override
	public void guardar(Red2 red) {
		if (red == null) {
			throw new IllegalArgumentException("La red no puede ser nula.");
		}

		RedDTO dto = new RedDTO();
		dto.cantidadVertices = red.cantidadVertices();

		for (Arista arista : red.obtenerAristasSinRepetidos()) {
			dto.aristas.add(new int[] {arista.getOrigen(),arista.getDestino()});
		}
		try {
			// Crear la carpeta si todavía no existe.
			Files.createDirectories(ARCHIVO.getParent());
			try (Writer writer = Files.newBufferedWriter(ARCHIVO)) {
				gson.toJson(dto, writer);
			}

		} catch (IOException e) {
			throw new PersistenciaException("No se pudo guardar el grafo.", e);
		}
	}

	@Override
	public Red2 cargar() {
		if (!Files.exists(ARCHIVO)) {
			throw new PersistenciaException("Todavía no existe un archivo guardado.");
		}

		try (Reader reader = Files.newBufferedReader(ARCHIVO)) {
			RedDTO dto = gson.fromJson(reader, RedDTO.class);
			if (dto == null || dto.cantidadVertices < 0 || dto.aristas == null) {
				throw new PersistenciaException("El archivo no tiene un formato válido.");
			}

			Red2 red = new Red2();

			// Primero creamos todos los vértices.
			for (int i = 0; i < dto.cantidadVertices; i++) {
				red.agregarVertice();
			}

			// Después reconstruimos las aristas.
			for (int[] arista : dto.aristas) {

				if (arista == null || arista.length != 2) {
					throw new PersistenciaException("Una arista tiene un formato incorrecto.");
				}

				int origen = arista[0];
				int destino = arista[1];

				if (origen < 0 || origen >= dto.cantidadVertices ||
						destino < 0 || destino >= dto.cantidadVertices) {

					throw new PersistenciaException("Una arista contiene un vértice inexistente.");
				}

				red.agregarArista(origen, destino);
			}

			return red;

		} catch (IOException | JsonParseException e) {
			throw new PersistenciaException("No se pudo leer el archivo guardado.", e);
		}
	}
}