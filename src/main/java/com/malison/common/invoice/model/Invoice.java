package com.malison.common.invoice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.ElementCollection;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;

import com.malison.common.model.BaseEntity;


/**
 * @author fridah
 *
 */
@NamedQueries({
	@NamedQuery(
			name ="Invoice.findall",
			query = "from Invoice i WHERE DELETED=0"
			)
})

@XmlRootElement (name = "invoice")
@Entity
@Table (name = "INVOICE")
public class Invoice extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@ElementCollection
	@Column (name ="JOBS")
	private List<Long> jobs;
	
	@Column (name = "COMPANY")
	private String company;
	
	@FormParam ("date")
	@Temporal(TemporalType.DATE)
	@Column(name ="DATE")
	private	Date Date = new Date();
	
	@Column (name = "NUMBER")
	private String invoiceNumber;
	
	@Column (name = "COUNT")
	private int count;
	
	@Column (name = "DELETED", columnDefinition="tinyint(1) default 0" )
	private boolean deleted;
	
	
	public List<Long> getJobs() {
		return jobs;
	}

	public void setJobs(List<Long> jobs) {
		this.jobs = jobs;
	}

	public String getInvoiceNumber(){
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public int getCount() {
		return count;
	}
	

	public Date getDate() {
		return Date;
	}

	public void setDate(Date date) {
		Date = date;
	}

	public int setCount(EntityManager em) {
		
		int number;
		Query query = em.createQuery("SELECT MAX(i.count) FROM Invoice i" );
		 
		if(query.getSingleResult() == null){
			number = 1;
		}
		else
		{
			number = (Integer) query.getSingleResult();
			number += 1;
		}	
		this.count = number;
		return number;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}


