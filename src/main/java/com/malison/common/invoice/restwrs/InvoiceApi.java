package com.malison.common.invoice.restwrs;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;

import com.itextpdf.text.DocumentException;
import com.malison.common.invoice.details.InvoiceDetails;
import com.malison.common.invoice.model.InvWrapper;
import com.malison.common.invoice.model.Invoice;
import com.malison.common.invpdf.InvPdf;
import com.malison.common.job.model.Job;
import com.malison.common.job.model.JobWrapper;

@Path("/invoice")
public class InvoiceApi {

	@PersistenceUnit
	private EntityManagerFactory emf;
	
	static Logger logger = Logger.getLogger(InvoiceApi.class);
	
	
	@SuppressWarnings("unchecked")
	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public Response invoicelist(){
		EntityManager em = emf.createEntityManager();
		List<Invoice> invoice = em.createNamedQuery("Invoice.findall").getResultList();
		InvWrapper wrapper = new InvWrapper();
		wrapper.setInvoice(invoice);
		return Response.status(200).entity(wrapper).build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getjobs")
	public Response jobslist(JSONArray selected){
		EntityManager em = emf.createEntityManager();
		int y = (Integer) selected.get(0);
		Long id = Long.parseLong(String.valueOf(y));
		Invoice invoice = em.find(Invoice.class,  id);
		String invNo= invoice.getInvoiceNumber();
		
		List<Job> jobs = em.createNamedQuery("Job.byInvoiceNo").setParameter("invno", invNo).getResultList();
		
		
		JobWrapper wrapper = new JobWrapper();
		wrapper.setJob(jobs);
		
		return Response.status(200).entity(wrapper).build();
	}

	@POST
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getinvoice")
	public Response selectedinvoice(JSONArray selected){
		EntityManager em = emf.createEntityManager();
		int y = (Integer) selected.get(0);
		Long id = Long.parseLong(String.valueOf(y));
		Invoice invoice = em.find(Invoice.class,  id);
		
		
		return Response.status(200).entity(invoice).build();
	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(JSONArray selected){
		EntityManager em = emf.createEntityManager();
		try{
			em.getTransaction().begin();
			for (int i = 0; i< selected.size(); i++){
				int y = (Integer) selected.get(i);
				Long id = Long.parseLong(String.valueOf(y));
				Invoice inv = em.find(Invoice.class, id);
				inv.setDeleted(true);
				String invno = inv.getInvoiceNumber();
				em.merge(inv);
				em.getTransaction().commit();
				List<Job> jobs = em.createNamedQuery("Job.byInvoiceNo").setParameter("invno", invno).getResultList();
				for (Job job: jobs){
					job.setInvoiced(false);
					job.setInvoiceNumber(null);
					em.getTransaction().begin();
					em.merge(job);
					em.getTransaction().commit();
				}
			}
		}
		catch (Exception e){
			e.printStackTrace();
			em.getTransaction().rollback();
			return "{\"success\":false, \"msg\":\"Error occured, please  try later\"}";
		}
		
		return "{\"success\":true, \"msg\": \"Saved successfully\"}";
	}
	
	@GET
	@SuppressWarnings("unchecked")
	@Path("/create")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/pdf")
	public Response create(@Context HttpServletRequest request, JSONArray selected) throws DocumentException, IOException{
		EntityManager em = emf.createEntityManager();
		Invoice inv = em.find(Invoice.class, Long.parseLong(String.valueOf(selected.get(0))));
		String invno = inv.getInvoiceNumber();
		List<Job> jobs = em.createNamedQuery("Job.byInvoiceNo").setParameter("invno", invno).getResultList();
		List<InvoiceDetails> invoiceDetails = em.createNamedQuery("Details.getbyInvoice")
				.setParameter("invoiceNo", invno).getResultList();
			HttpSession session = request.getSession(false);
			File file = InvPdf.generatepdf(session, jobs, inv, invoiceDetails);
		
		//ResponseBuilder response = Response.ok((Object)x);
		return Response.status(200).entity(file).build();
	}
	
}


