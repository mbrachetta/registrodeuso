package isis.registrousofirmador.controller;

import javax.ejb.Local;
import isis.registrousofirmador.model.RegistroUsoFirmador;

/**
 * Operaciones b�sicas para la manipulaci�n de UsuarioLicenciado
 */
@Local
public interface RegistroUsoFirmadorDAO  {
	void registrarUso(RegistroUsoFirmador uso);
}