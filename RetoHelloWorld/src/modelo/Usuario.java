/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;

/**
 *
 * @author 2dam
 */
public class Usuario implements Serializable {

    private int idU;
    private String nombre;
    private String password;
    private String nombreReal;
    private String apellido;
    private String email;
    private String direccion;

    public Usuario() {
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreReal() {
        return nombreReal;
    }

    public void setNombreReal(String nombreReal) {
        this.nombreReal = nombreReal;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Usuario(int idU, String nombre, String password, String nombreReal, String apellido, String email, String direccion) {
        this.idU = idU;
        this.nombre = nombre;
        this.password = password;
        this.nombreReal = nombreReal;
        this.apellido = apellido;
        this.email = email;
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" + "idU=" + idU + ", nombre=" + nombre + ", password=" + password + ", nombreReal=" + nombreReal + ", apellido=" + apellido + ", email=" + email + ", direccion=" + direccion + '}';
    }

}
