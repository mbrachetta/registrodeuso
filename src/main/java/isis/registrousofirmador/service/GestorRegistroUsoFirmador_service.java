package isis.registrousofirmador.service;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import isis.registrousofirmador.controller.RegistroUsoFirmadorDAO;
import isis.registrousofirmador.model.RegistroUsoFirmador;


@Path ("/")
public class GestorRegistroUsoFirmador_service {

	@Inject
	private Logger log;
	
	@Inject
	private RegistroUsoFirmadorDAO uso;
	
	private RegistroUsoFirmador newUso;
	

		
	@POST
    @Produces("application/xml")
    public Response registrarUso  (@FormParam("CN") String CN,
    							   @FormParam("title") String title, 
    							   @FormParam("OU") String OU,
    							   @FormParam("O") String O,
    							   @FormParam("email") String email,
			  					   @FormParam("ST") String ST,
			  					   @FormParam("C") String C) {

			ResponseBuilder response = null;
		
			try {
				newUso = new RegistroUsoFirmador();
				newUso.setName(CN);
				newUso.setTitle(title);
				newUso.setOu(OU);
				newUso.setOrganization(O);
				newUso.setEmail(email);
				newUso.setState(ST);
				newUso.setCountry(C);
				newUso.setFecha(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
				uso.registrarUso(newUso);
			
				response = Response.ok();
				return response.build();
			
		}
		catch (RollbackException ex){
			ex.printStackTrace();
			response = Response.status(Status.BAD_REQUEST);
			return response.build();
		}
		catch (PersistenceException ex){
			ex.printStackTrace();
			response = Response.status(Status.BAD_REQUEST);
			return response.build();
		}
		catch (Exception ex){
			ex.printStackTrace();
			response = Response.status(Status.BAD_REQUEST).entity(ex.getMessage()); 
			return response.build();
		} 
    }
}