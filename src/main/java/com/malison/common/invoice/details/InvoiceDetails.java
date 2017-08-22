package com.malison.common.invoice.details;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.malison.common.invoice.model.Invoice;
import com.malison.common.model.BaseEntity;

@NamedQueries({
	@NamedQuery(
			name = "Details.getbyInvoice",
			query = "From InvoiceDetails d Where d.invoice.invoiceNumber= :invoiceNo"
			)
})
@Entity
@Table(name= "INVOICE_DETAILS")
public class InvoiceDetails extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "PARTICULAR")
	private String particular;
	
	@Column(name = "AMOUNT")
	private BigDecimal amount;
	
	@ManyToOne
	private Invoice invoice;

	public String getParticular() {
		return particular;
	}

	public void setParticular(String particular) {
		this.particular = particular;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	
	

}
