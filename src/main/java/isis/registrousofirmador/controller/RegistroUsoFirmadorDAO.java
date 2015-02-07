package isis.registrousofirmador.controller;

import javax.ejb.Local;
import isis.registrousofirmador.model.RegistroUsoFirmador;

/**
 * Operaciones básicas para la manipulación de UsuarioLicenciado
 */
@Local
public interface RegistroUsoFirmadorDAO  {
	void registrarUso(RegistroUsoFirmador uso);
}