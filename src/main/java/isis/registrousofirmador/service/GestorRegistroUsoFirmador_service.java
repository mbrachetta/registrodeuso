package isis.registrousofirmador.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.spi.validation.ValidateRequest;

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
    @Produces("application/octet-stream")
	@ValidateRequest
    public Response getRegistroUso(@FormParam("CN")
    							   @NotNull
    							   @Pattern(regexp = "[A-Za-zñáéíóúÑÁÉÍÓÚ\\s]{2,50}$", message = "debe contener sólo letras y espacios")
    							   String CN,
    							
    							   @FormParam("title") String title, 
    							   @FormParam("OU") String OU,
			  					
    							   @FormParam("O") 
    							   @NotNull
    							   @Pattern(regexp = "[A-Za-zñáéíóúÑ.&-_0-9\\s]{2,50}$", message = "debe contener sólo letras y espacios")
    							   String O,
			  					
    							   @FormParam("email")
    							   @NotNull
    							   @NotEmpty
    							   @Email (message= "Debe colocar una dirección de email bien formada")
    							   String email,
    							
			  					   @FormParam("ST") String ST,
			  					
			  					   @FormParam("C")
    							   @NotNull
    							   @Pattern(regexp = "[A-Z][A-Z]", message = "debe contener un código de país válido")
    								String C) {

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
		
		catch (ConstraintViolationException ex) {
			ex.printStackTrace();
			//Handle bean validation issues
			response = Response.status(Status.BAD_REQUEST);
			return response.build();
		} 
		catch (ValidationException ex) {
			ex.printStackTrace();
			//Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("email","Email taken");
			response = Response.status(Response.Status.CONFLICT).entity(responseObj);
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