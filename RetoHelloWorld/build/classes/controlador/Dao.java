/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import excepciones.LoginError;
import modelo.Usuario;

/**
 *
 * @author 2dam
 */
public interface Dao {

    public Usuario login(Usuario usu) throws LoginError;

}
