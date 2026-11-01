package Persistence;

import Model.Red2;

public interface IPersistencia {

    Red2 cargar();

    void guardar(Red2 red);
}