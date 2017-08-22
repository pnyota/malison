package com.malison.common.job.restws;



import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.jboss.resteasy.annotations.Form;

import com.malison.common.invoice.details.InvoiceDetails;
import com.malison.common.invoice.model.Invoice;
import com.malison.common.invpdf.InvPdf;
import com.malison.common.job.model.Job;
import com.malison.common.job.model.JobWrapper;




@Path("jobapi")
public class JobApi {
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	static Logger logger = Logger.getLogger(JobApi.class);

	//saves new job to database
	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String Create(@Form Job job){
		
		EntityManager em = emf.createEntityManager();
		String response;
		try{
		em.getTransaction().begin();
		em.merge(job);
		em.getTransaction().commit();		
		response = "{\"success\":true, \"msg\": \"Saved successfully\"}";
	
		}
		catch (Exception e){
			em.getTransaction().rollback();
			 e.printStackTrace();
			 response = "{\"success\":false, \"msg\":\"Error occured, please  try later\"}";
			
		}
		
		return response;
	}
	
	//Edits a Job
	@POST
	@Path("/edit")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String Edit (@Form Job job){
		EntityManager em = emf.createEntityManager();
		Job j = em.find(Job.class, job.getId());
		String response;
		try{
			em.getTransaction().begin();
			j.setAmount(job.getAmount());
			j.setDate(job.getDate());
			j.setDeliveryNo(job.getDeliveryNo());
			j.setDestination(job.getDestination());
			j.setLoadedFrom(job.getLoadedFrom());
			j.setProduct(job.getProduct());
			j.setQtyLoaded(job.getQtyLoaded());
			j.setRate(job.getRate());
			j.setVehicleRegno(job.getVehicleRegno());
			em.merge(j);
			em.getTransaction().commit();
			response =  "{\"success\":true, \"msg\": \"Saved successfully\"}";
	}catch(Exception e){
		e.printStackTrace();
		em.getTransaction().rollback();
		response = "{\"success\":false, \"msg\":\"Error occured, please  try later\"}";
	}
		
		return response;
		
	}
	/*
	 * parses JSON array containing jobs from the client company and currency
	 * calls function to create invoice and return invoice number
	 * update job's invoice number
	 * calls function to create pdf
	 * returns pdf
	 */
	
	@GET
	@POST
	@Path ("/createinvoice")
	@Consumes (MediaType.APPLICATION_JSON)
	@Produces ("application/pdf")
	public Response acceptSelected(@Context HttpServletRequest request ,JSONObject selected) {
		EntityManager em = emf.createEntityManager();
		List<Job> job = new ArrayList<Job> ();
		List<Long> jobs = new ArrayList<Long> ();
		List<InvoiceDetails> invoiceDetails = new ArrayList<> ();
		String company = String.valueOf(selected.get("company"));
		company = company.toUpperCase();
		@SuppressWarnings("rawtypes")
		ArrayList selectedjobs = (ArrayList) selected.get("selectedjobs");
		String currency = String.valueOf(selected.get("currency"));
		String tax = String.valueOf(selected.get("tax"));
		String biller = String.valueOf(selected.get("biller"));
		@SuppressWarnings("unchecked")
		List<JSONObject> details  = (ArrayList<JSONObject>) selected.get("details");
		
		HttpSession session = request.getSession(false);
		try{
			
			for (int i = 0;i < selectedjobs.size();i++){
				int value = (Integer) selectedjobs.get(i);
				Long x = Long.parseLong(String.valueOf(value));
				job.add( findJobs( em, x));
				jobs.add(x);
				
			}
			for(int i = 0; i < details.size(); i++ ){
				JSONObject detail = details.get(i);
				String particular = String.valueOf(detail.get("particular"));
				BigDecimal amount = new BigDecimal(String.valueOf(detail.get("amount")));
				InvoiceDetails invoiceDetail = new InvoiceDetails();
				invoiceDetail.setParticular(particular);
				invoiceDetail.setAmount(amount);
				invoiceDetails.add(invoiceDetail);
			}
			Invoice invoice = createInvoice(currency, em, jobs, company, biller, tax);
			persistDetails(invoiceDetails, invoice);
			String invoiceNumber = invoice.getInvoiceNumber();
			
			for (int i = 0;i < job.size();i++){
				em.getTransaction().begin();
				job.get(i).setInvoiceNumber(invoiceNumber);
				job.get(i).setInvoiced(true);;
				em.getTransaction().commit();
			}
			File file = null;
			try {
				file = InvPdf.generatepdf(session, job, invoice, invoiceDetails);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition",
					"attachment; filename=invoice.pdf");
			return response.build();
			
		}
		catch (Exception e){
			em.getTransaction().rollback();
			e.printStackTrace();
			return Response.status(500).entity("{\"success\":false, \"msg\":\"Error occured, please  try later\"}").build();
		}
		
			
	}

	private void persistDetails(List<InvoiceDetails> invoiceDetails, Invoice invoice) {
		EntityManager em = emf.createEntityManager();
		System.out.println("hapa Tunaanza kupersist details");
		Invoice completeInvoice =(Invoice) em.createNamedQuery("Invoice.getByInvoiceNumber")
				.setParameter("InvoiceNumber", invoice.getInvoiceNumber())
				.getSingleResult();
		for (InvoiceDetails invoiceDetail: invoiceDetails ){
			invoiceDetail.setInvoice(completeInvoice);
			em.getTransaction().begin();
			em.persist(invoiceDetail);
			em.getTransaction().commit();
		}
		
	
	}

	//Returns Job list
	@SuppressWarnings("unchecked")
	@Path ("/list")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response list(){
		EntityManager em = emf.createEntityManager();
		List<Job> job = em.createNamedQuery("Job.findall").getResultList();
		JobWrapper wrapper = new JobWrapper();
		wrapper.setJob(job);
		
		return Response.status(200).entity(wrapper).build();
		
	}
	
	//Returns uninvoiced jobs
	@SuppressWarnings("unchecked")
	@Path ("/invoice")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response invoice(){
		EntityManager em = emf.createEntityManager();
		List<Job> invoice = em.createNamedQuery("Job.finduninvoiced").getResultList();
		JobWrapper wrapper = new JobWrapper();
		wrapper.setJob(invoice);
		return Response.status(200).entity(wrapper).build();
	}
	
	//Function to find job by their id
	public static Job findJobs(EntityManager em, long x){
				
		Job job = em.find(Job.class, x);
		
		return job;
		
	}
	
	//Creates Invoice and generates invoice numbers
	public Invoice createInvoice (String currency, EntityManager em, List<Long> jobs, String company, String biller, String tax ){
		Invoice invoice = new Invoice();
		em.getTransaction().begin();
		invoice.setJobs(jobs);
		long x = invoice.setCount(em);
		
		
		String invoiceNumber= String.format("%05d", x);
		invoiceNumber = "MTL" + invoiceNumber;	
		invoice.setInvoiceNumber(invoiceNumber);
		invoice.setCurrency(currency);
		invoice.setDate(Calendar.getInstance().getTime());
		invoice.setCompany(company);
		invoice.setBiller(biller);
		invoice.setTax(tax);
		em.merge(invoice);
		em.getTransaction().commit();

		
		return invoice;
	}
	
	
	
	
	//Delete Jobs
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public String deletejobs(JSONArray selected){
		EntityManager em = emf.createEntityManager();
		String response = new String();
		try{
		for(int i= 0; i < selected.size(); i++){
			Job j = em.find(Job.class, Long.parseLong(String.valueOf(selected.get(i))));
			if (!j.isInvoiced()){
			em.getTransaction().begin();
			em.remove(j);
			em.getTransaction().commit();
			response = "{\"success\":true, \"msg\": \"Saved successfully\"}";
			}
		else{
				response = "{\"success\":false, \"msg\":\"Cannot delete an invoiced job, delete its invoice first\"}";
				}
		}
		}
		catch (Exception e){
		e.printStackTrace();
		response =  "{\"success\":false, \"msg\":\"Error occured, please  try later\"}";
		}
		
		return response;
	}
	
	//editJobs
	@POST
	@Path("/selectedjob")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces (MediaType.APPLICATION_JSON)
	public Response getSelectedJob(JSONArray selected){
		EntityManager em = emf.createEntityManager();
		
		Job j = em.find(Job.class, Long.parseLong(String.valueOf(selected.get(0))));
		
	return Response.status(200).entity(j).build();
	}
	
}

