package com.malison.common.job.model;

import com.malison.common.model.BaseEntity;
import com.malison.common.model.DateAdapter;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.ws.rs.FormParam;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@NamedQueries({
	@NamedQuery(
			name= "Job.findall",
			query = "from Job j"
			),
	@NamedQuery(
			name="Job.finduninvoiced",
			query=" FROM Job j WHERE INVOICED= 0"
			),
	@NamedQuery(
			name ="Job.byInvoiceNo",
			query="SELECT j FROM Job j WHERE j.InvoiceNumber = :invno "
			)
})

@XmlRootElement (name = "invoice")
@Entity
@Table (name= "JOB")
public class Job extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XmlJavaTypeAdapter(DateAdapter.class)
	@FormParam ("date")
	@Temporal(TemporalType.DATE)
	@Column(name ="DATE")
	private	Date date = new Date();

	@FormParam ("qtyLoaded")
	@Column(name ="QUANTITY")	
	private float QtyLoaded;
	
	@FormParam ("vehicleRegno")
	@Column(name ="VEHICLE")
	private String VehicleRegno;
	
	@FormParam ("product")
	@Column(name ="PRODUCT")
	private String Product;
	
	@FormParam("currency")
	@Column(name="CURRENCY")
	private String currency;
	
	
	@Column (name = "INVOICED", columnDefinition="tinyint(1) default 0" )
	private boolean invoiced;
	
	@FormParam ("loadedFrom")
	@Column(name ="LOADED")
	private String LoadedFrom;
	
	@FormParam ("destination")
	@Column(name ="DESTINATION")
	private String Destination;
	
	@FormParam ("deliveryNo")
	@Column(name ="DELIVERYNO")
	private String DeliveryNo;
	
	@Column(name ="INVOICE")
	private String InvoiceNumber;
	
	@FormParam ("rate")
	@Column(name ="RATE")
	private int Rate;
	
	@FormParam ("amount")
	@Column(name ="AMOUNT")
	private float Amount;
	

	public String getVehicleRegno() {
		return VehicleRegno;
	}

	public void setVehicleRegno(String vehicleRegno) {
		VehicleRegno = vehicleRegno;
	}

	public String getProduct() {
		return Product;
	}

	public void setProduct(String product) {
		Product = product;
	}

	public String getLoadedFrom() {
		return LoadedFrom;
	}

	public void setLoadedFrom(String loadedFrom) {
		LoadedFrom = loadedFrom;
	}

	public boolean isInvoiced() {
		return invoiced;
	}

	public void setInvoiced(boolean invoiced) {
		this.invoiced = invoiced;
	}

	public String getDestination() {
		return Destination;
	}

	public void setDestination(String destination) {
		Destination = destination;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public int getRate() {
		return Rate;
	}

	public void setRate(int rate) {
		Rate = rate;
	}

	public float getAmount() {
		return Amount;
	}

	public void setAmount(float amount) {
		Amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDeliveryNo() {
		return DeliveryNo;
	}

	public void setDeliveryNo(String deliveryNo) {
		DeliveryNo = deliveryNo;
	}

	public float getQtyLoaded() {
		return QtyLoaded;
	}

	public void setQtyLoaded(float qtyLoaded) {
		QtyLoaded = qtyLoaded;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	

}
