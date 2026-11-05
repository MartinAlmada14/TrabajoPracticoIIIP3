package persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonParseException;

import model.RedDeGrafos;

public class GSONPersistencia implements IPersistencia {

	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	private static class RedDTO {
		Integer cantidadVertices;
		List<int[]> aristas;
	}

	@Override
	public RedDeGrafos cargar(Path archivo) {
		if (!Files.isRegularFile(archivo)) {
			throw new PersistenciaException("No existe el archivo: " + archivo);
		}
		return aRed(leer(archivo));
	}

	@Override
	public void guardar(RedDeGrafos red, Path archivo) {
		if (red == null) {
			throw new IllegalArgumentException("La red no puede ser nula.");
		}
		escribir(aDTO(red), archivo);
	}

	private RedDTO leer(Path archivo) {
		try (Reader reader = Files.newBufferedReader(archivo)) {
			return gson.fromJson(reader, RedDTO.class);
		} catch (IOException | JsonParseException e) {
			throw new PersistenciaException("No se pudo leer el archivo: " + archivo, e);
		}
	}

	private void escribir(RedDTO dto, Path archivo) {
		try {
			crearCarpetaPadre(archivo);
			try (Writer writer = Files.newBufferedWriter(archivo)) {
				gson.toJson(dto, writer);
			}
		} catch (IOException | JsonIOException e) {
			throw new PersistenciaException("No se pudo guardar el archivo: " + archivo, e);
		}
	}

	private void crearCarpetaPadre(Path archivo) throws IOException {
		Path padre = archivo.toAbsolutePath().getParent();
		if (padre != null) {
			Files.createDirectories(padre);
		}
	}

	private RedDTO aDTO(RedDeGrafos red) {
		RedDTO dto = new RedDTO();
		dto.cantidadVertices = red.cantidadVertices();
		dto.aristas = red.obtenerAristasComoPares();
		return dto;
	}

	private RedDeGrafos aRed(RedDTO dto) {
		if (!esFormatoValido(dto)) {
			throw new PersistenciaException("El archivo no tiene un formato valido.");
		}
		try {
			RedDeGrafos red = new RedDeGrafos();
			agregarVertices(red, dto.cantidadVertices);
			agregarAristas(red, dto.aristas);
			return red;
		} catch (IllegalArgumentException e) {
			throw new PersistenciaException("Contenido invalido: " + e.getMessage(), e);
		}
	}
	private boolean esFormatoValido(RedDTO dto) {
		return dto != null && dto.cantidadVertices != null && dto.cantidadVertices >= 0 && dto.aristas != null;
	}

	private void agregarVertices(RedDeGrafos red, int cantidad) {
		for (int i = 0; i < cantidad; i++) {
			red.agregarVertice();
		}
	}

	private void agregarAristas(RedDeGrafos red, List<int[]> aristas) {
		for (int[] arista : aristas) {
			if (arista == null || arista.length != 2) {
				throw new PersistenciaException("Una arista debe tener exactamente dos extremos.");
			}
			red.agregarArista(arista[0], arista[1]);
		}
	}
}