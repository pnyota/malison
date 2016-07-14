package com.malison.common.invoice.model;



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.malison.common.invoice.model.Invoice;

@XmlRootElement
public class InvWrapper {
	
	List<Invoice> invoice = new ArrayList<Invoice>();

	@XmlElement
	public List<Invoice> getInvoice() {
		return invoice;
	}

	public void setInvoice(List<Invoice> invoice) {
		this.invoice = invoice;
	}
	

}
