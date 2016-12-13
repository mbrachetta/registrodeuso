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
    	System.out.println("SE VA A PERSISTIR EL USO DE:");
    	System.out.println(uso.getName());
    	System.out.println(uso.getOrganization());
    	System.out.println(uso.getTitle());
    	System.out.println(uso.getEmail());
    	System.out.println(uso.getFecha());
   		em.persist(uso);
   		System.out.println("SE PERSISTIO OK.");
    }
}