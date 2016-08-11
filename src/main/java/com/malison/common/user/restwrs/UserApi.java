package com.malison.common.user.restwrs;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.malison.common.user.restwrs.hasher;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.Form;
import org.json.simple.JSONObject;

import com.malison.common.user.User;

@Path ("/user")
public class UserApi {
	
	static Logger logger = Logger.getLogger(UserApi.class);
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String Create(@Form User user){
		String pass = user.getPassword();
		/*String salt = user.getUserName();*/
		user.setPassword(hasher.hashPassword(pass));
		
		EntityManager em = emf.createEntityManager();
		try{
			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		}
		catch (Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			
			return "{\"success\":false, \"msg\":\"Error occured, please  try later\"}";
		}
		return "{\"success\":true, \"msg\": \"Saved successfully\"}";
	}
	
	@POST
	@Path("/authenticate")
	@Consumes(MediaType.APPLICATION_JSON)
	public String authenticate(@Context HttpServletRequest request, JSONObject obj){
		
		String userName = String.valueOf(obj.get("username"));
		String pass = String.valueOf(obj.get("password"));
		String password = hasher.hashPassword(pass);
		try{
			request.login(userName, password);
			
		}
		catch(Exception e){
			logger.info("lets try this");
			e.printStackTrace();
			return "{\"success\":false, \"msg\": \"Login Failed\"}";
		}
		HttpSession session = request.getSession(true);
		SetSession(session, userName);
		
		return "{\"success\":true, \"msg\": \"Login successfull\"}";
	}
	
	@SuppressWarnings("unchecked")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getusers")
	public Response getUsers(){
		
		EntityManager em = emf.createEntityManager();
		List<String> usernames = em.createNamedQuery("User.getUsernames").getResultList();
		
		return Response.status(200).entity(usernames).build();		
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request, JSONObject obj){

		//String userName = String.valueOf(obj.get("username"));
		try {
			request.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(200).entity("{\"success\":false, \"msg\": \"Logout Failed\"}").build();
		}
		request.getSession().invalidate();
			return Response.status(200).entity("{\"success\":true, \"msg\": \"Logout successfull\"}").build();
	}
	
	public void SetSession(HttpSession session, String username){
		
		EntityManager em = emf.createEntityManager();
		User user =	(User) em.createNamedQuery("User.findUser").setParameter("username", username)
				.getSingleResult();
		String name = user.getFirstName() + " " + user.getLastName();
		String position = user.getPosition();
		
		session.setAttribute("name", name);
		session.setAttribute("position", position);
		}
}
