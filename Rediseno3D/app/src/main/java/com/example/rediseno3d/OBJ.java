package com.example.rediseno3d;

public class OBJ {
    private String nombre, fecha, idImagen;

    public OBJ(String nombre, String fecha, String idImagen) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.idImagen = idImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(String idImagen) {
        this.idImagen = idImagen;
    }
}
