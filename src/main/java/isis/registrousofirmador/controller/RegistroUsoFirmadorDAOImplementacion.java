package isis.registrousofirmador.controller;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import isis.registrousofirmador.model.RegistroUsoFirmador;


/**
 * Provee funcionalidades para la manipulación de Usuarios usando el contexto de persistencia desde {@link Resources}.
 */
@Stateful
public class RegistroUsoFirmadorDAOImplementacion implements RegistroUsoFirmadorDAO {

    @Inject
    private EntityManager em;
    

    public void registrarUso (RegistroUsoFirmador uso) {
    	System.out.println("VOY A PERSISTIR EL USO DE:");
    	System.out.println(uso.getName());
   		em.persist(uso);
   		System.out.println("PASE OK");
    }
}