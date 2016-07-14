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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.Form;
import org.json.simple.JSONObject;

import com.malison.common.user.User;

@Path ("/user")
public class userApi {
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String Create(@Form User user){
		String pass = user.getPassword();
		String salt = user.getUserName();
		user.setPassword(hasher.hashPassword(pass,salt.getBytes()));
		
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
	public String authenticate(JSONObject obj){
		
		String userName = String.valueOf(obj.get("username"));
		String password = String.valueOf(obj.get("password"));
		password = hasher.hashPassword(password, userName.getBytes());
		
		EntityManager em = emf.createEntityManager();
		try{
		User user = (User) em.createNamedQuery("User.findUser").setParameter("username", userName).getSingleResult();
		String pass = user.getPassword();
		if(pass.equals(password)){
			return "{\"success\":true, \"msg\": \"User authenticated\"}";
		}
		else{
			return "{\"success\":false, \"msg\":\"No match username or password\"}";
		}
		}catch (Exception e){
			e.printStackTrace();
			return "{\"success\":false, \"msg\":\"Wrong username or password\"}";
		}
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
}
