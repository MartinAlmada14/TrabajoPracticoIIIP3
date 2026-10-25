
package Persistence;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import Model.Arista;
import Model.Red;
import Model.Vertice;

public class GSONPersistencia implements IPersistencia {
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	
	private static class RedDTO {
		List<String> vertices = new ArrayList<String>();
		List<int[]> aristas = new ArrayList<int[]>();
	}
	
	
	@Override
	public Red cargar(Path archivo)	{
		if(!Files.isRegularFile(archivo)) {
			throw new PersistenciaException("No existe el archivo: " + archivo);
		}
		try(Reader reader = Files.newBufferedReader(archivo)) {
		return aRed(gson.fromJson(reader, RedDTO.class));
		}
		catch (IOException | JsonParseException e) {
			throw new PersistenciaException("No se pudo leer el archivo: " + archivo, e);
		}
	}

	@Override
	public void guardar(Red red, Path archivo) {
		if (red == null) {
			throw new IllegalArgumentException("No se puede guardar un red nulo.");
		}
		try {
			Path padre = archivo.toAbsolutePath().getParent();
			if(padre != null) {
				Files.createDirectories(padre);
			}
			try (Writer writer = Files.newBufferedWriter(archivo)) {
				gson.toJson(aDTO(red), writer);
			}

		} catch (IOException | JsonParseException e) {
			throw new PersistenciaException("No se pudo guardar el archivo: " + archivo, e);
		}
	}
	
	private Red aRed(RedDTO dto) {
		if(dto == null || dto.vertices == null || dto.aristas == null) {
			throw new PersistenciaException("El archivo no tiene el formato esperado");	
		}
		try {
			Red red = new Red();
			for(String nombre : dto.vertices) {
				red.agregarVertice(nombre);
			}
			for(int[] arista : dto.aristas) {
				if(arista == null || arista.length != 2) {
					throw new PersistenciaException("Cada arista debe tener exactamente dos extremos");
				}
				red.agregarArista(arista[0], arista[1]);
			}
			return red;
		} catch (IllegalArgumentException e) {
			throw new PersistenciaException("Contenido invalido: " + e.getMessage(), e);
		}
		
	}

	private RedDTO aDTO(Red red) {
		RedDTO dto = new RedDTO();
		for(Vertice vertice : red.getVertices()) {
			dto.vertices.add(vertice.getNombre());
		}
		for(Arista arista : red.aristas() ) {
			dto.aristas.add(new int[] {arista.getOrigen(), arista.getDestino()});
		}
		return dto;
	}
	
}
