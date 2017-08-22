package com.malison.common.invpdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.malison.common.invoice.details.InvoiceDetails;
import com.malison.common.invoice.model.Invoice;
import com.malison.common.job.model.Job;
import com.malison.common.wordconverter.WordConverter;

public class InvPdf {
	static Logger logger = Logger.getLogger(InvPdf.class);
	//Creates a pdf
		public static File generatepdf(HttpSession session, List<Job> jobs, Invoice invoice, List<InvoiceDetails> invoiceDetails) throws DocumentException, IOException{
			
			File file = null;
			DecimalFormat df = new DecimalFormat("#,###.00");
			DecimalFormat df2 = new DecimalFormat("####.00");
			String name = ((String) session.getAttribute("name"));
			name = name.toUpperCase();
			
			try {
				file = File.createTempFile("invoice", ".pdf",null);
				file.deleteOnExit();
			}
			catch (Exception e){
				e.printStackTrace();
				 
			}
			Document document = new Document (PageSize.A4.rotate(), 0,0,0,30);
		
			OutputStream outputStream = new FileOutputStream(file);
			
		       PdfWriter writer = PdfWriter.getInstance(document, outputStream);
		        document.open();
		        
		        
		        Font bold = new Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD);
		        Font normal = new Font(Font.FontFamily.HELVETICA, 14f, Font.NORMAL);
		        Font fortotal = new Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD);
		        Font addressfont = new Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD);
		        
		        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String date = format.format(invoice.getDate());
				
		        PdfPTable tablelogo = new PdfPTable(1);
				tablelogo.setWidthPercentage(100);
				PdfPCell logo = new PdfPCell();
		        logo.setBorderColor(BaseColor.RED);
				
		        
		        Image header = null;
				if(invoice.getBiller().equals("agency")){
					header = Image.getInstance(new URL("http://res.cloudinary.com/invision-itech/image/upload/v1475315014/agency_gj9s9d.png"));
				}
					
				else if (invoice.getBiller().equals("limited")){
					header = Image.getInstance(new URL("http://res.cloudinary.com/invision-itech/image/upload/v1470911337/malison_tiavfx.png"));
				}
				else{
					logger.info("not agency nor limited");
				}
				header.disableBorderSide(0);
		        header.setAlignment(Image.ALIGN_TOP);
		        logo.addElement(header);
		        tablelogo.addCell(logo);
		        document.add(tablelogo);
		        
		        PdfPTable tablehead = new PdfPTable (2);
		        float[] headwidths = {80,20};
		        tablehead.setWidths(headwidths);
		        tablehead.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		        tablehead.setWidthPercentage(90);
		        Chunk chunk1 = new Chunk ("INVOICE NO: ", normal);
		        Phrase invoiceno = new Phrase (invoice.getInvoiceNumber(), bold);
		        
		        Phrase address = new Phrase("P.O.BOX 30932-00100 GPO\nTEL:310338\nNAIROBI",addressfont);
		        	        
		        Phrase ph1 = new Phrase(chunk1);
		        
		        
		        Paragraph ph = new Paragraph();
		        ph.add(ph1);
		        ph.add(invoiceno);
		        PdfPCell cell1= new PdfPCell (ph);
		        cell1.setVerticalAlignment(Element.ALIGN_BOTTOM);
		        cell1.setBorder(Rectangle.NO_BORDER);
		        
		        PdfPCell addresscell = new PdfPCell(address);
		        addresscell.setHorizontalAlignment(Element.ALIGN_LEFT);
		        addresscell.setBorder(Rectangle.NO_BORDER);
		     
		        tablehead.addCell(cell1);
		        tablehead.addCell(addresscell);
		        document.add(tablehead);
		        
		        PdfPTable tableCName = new PdfPTable(2);
		        tableCName.setWidthPercentage(90);
		        
		        tableCName.getDefaultCell().setBorder(Rectangle.BOTTOM);
		        Phrase cName = new Phrase(String.valueOf(invoice.getCompany()), bold);
		        Chunk to = new Chunk("M/s: ", normal);
		        Chunk chunk2 = new Chunk ("DATE: ", normal);
		        Phrase pdate = new Phrase (date,bold);
		        
		        Phrase ph2 = new Phrase(chunk2);
		        Paragraph phdate = new Paragraph();
		        phdate.add(ph2);
		        phdate.add(pdate);
		        
		        PdfPCell cell2 = new PdfPCell(phdate);
		        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		        cell2.setBorder(Rectangle.NO_BORDER);
		             
		        Paragraph prCName = new Paragraph();
		        prCName.add(to);
		        prCName.add(cName);
		        PdfPCell cellCName = new PdfPCell();
		        cellCName.setBorder(Rectangle.NO_BORDER);
		        cellCName.setBorderColorBottom(BaseColor.BLACK);
		        cellCName.addElement(prCName);
		        tableCName.addCell(cellCName);
		        tableCName.addCell(cell2);
		        document.add(tableCName);
		        
		        PdfPTable vat = new PdfPTable(1);
		        vat.setWidthPercentage(90);
		        vat.setSpacingAfter(10f);
		        vat.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		        Phrase vatph = new Phrase("V.A.T NO.0110751Y    PIN NO. P01125700K ",bold);
		        vat.addCell(vatph);
		        document.add(vat);
		        	        
		        PdfPTable tableheader = new PdfPTable (9);
		        tableheader.setWidthPercentage(90);
		        tableheader.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		        tableheader.addCell(new Phrase ("DATE",bold));
		        tableheader.addCell(new Phrase ("VEHICLE", bold));
		        tableheader.addCell(new Phrase ("LOADED",bold));
		        tableheader.addCell(new Phrase ("DESTINATION", bold));
		        tableheader.addCell(new Phrase ("DELIVERY\nNOTE NO.",bold));
		        tableheader.addCell(new Phrase ("PRODUCT",bold));
		        tableheader.addCell(new Phrase ("QUANTITY\nLOADED",bold));
		        tableheader.addCell(new Phrase ("RATE",bold));
		        tableheader.addCell(new Phrase ("AMOUNT\n" + invoice.getCurrency(), bold));
		        PdfPRow r = tableheader.getRow(0);
		        for (PdfPCell c: r.getCells()){
		        	c.setBackgroundColor(new BaseColor(195,201,201));
		        }
		        document.add(tableheader);
		        
		        PdfPTable table = new PdfPTable(9);
		        table.setWidthPercentage(90);
		        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
		       BigDecimal total = new BigDecimal(0.00);
		        for (Job job: jobs){
		        	table.addCell(String.valueOf(job.getDate()));
		        	table.addCell(String.valueOf(job.getVehicleRegno()));
		        	table.addCell(String.valueOf(job.getLoadedFrom()));
		        	table.addCell(String.valueOf(job.getDestination()));
		        	table.addCell(String.valueOf(job.getDeliveryNo()));
		        	table.addCell(String.valueOf(job.getProduct()));
		        	table.addCell(String.valueOf(job.getQtyLoaded()));
		        	table.addCell(String.valueOf(job.getRate()));
		        	table.addCell(String.valueOf(df.format(job.getAmount())));
		       total = total.add(job.getAmount());
		        	
		        }
		        for (InvoiceDetails invoiceDetail: invoiceDetails){
		        	PdfPCell invoiceDetailCell = new PdfPCell();
		        	invoiceDetailCell.setColspan(8);
		        	invoiceDetailCell.addElement(new Phrase(invoiceDetail.getParticular()));
		        	table.addCell(invoiceDetailCell);
		        	table.addCell(String.valueOf(df.format(invoiceDetail.getAmount())));
		        	total = total.add(invoiceDetail.getAmount());
		        }


				boolean b = false;
				for(PdfPRow t: table.getRows()) {
				  for(PdfPCell c: t.getCells()) {
				    c.setBackgroundColor(b ? new BaseColor(195,201,201) : BaseColor.WHITE);
				  }
				  b = !b;
				}
		        document.add(table);
		        
		        double sum = total.doubleValue();
		        float[] colwidths = {80f,20f};
		        PdfPTable totalAmount = new PdfPTable(2);
	        	totalAmount.setWidths(colwidths);
		        
		        if (invoice.getTax().equals("true")){
		        	BigDecimal withoutTax = total;
		        	sum = sum * 1.16;
		        	total = BigDecimal.valueOf(sum);
		        	PdfPCell vatcell= new PdfPCell(new Phrase("+16% VAT: "));
		        	vatcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			        vatcell.setBorder(Rectangle.NO_BORDER);
			        PdfPCell vatamount= new PdfPCell(new Phrase(invoice.getCurrency() + String.valueOf(df.format(total.subtract(withoutTax)))));
			        vatamount.setBorder(Rectangle.NO_BORDER);
			        totalAmount.addCell(vatcell);
			        totalAmount.addCell(vatamount);
		        }        	
			    PdfPCell totalcell= new PdfPCell(new Phrase("TOTAL: ",fortotal));
			        
			    totalcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			    totalcell.setBorder(Rectangle.NO_BORDER);
			    totalAmount.addCell(totalcell);
			    PdfPCell amount= new PdfPCell(new Phrase(invoice.getCurrency() + String.valueOf(df.format(total)),fortotal));
			    amount.setBorder(Rectangle.NO_BORDER);
		        
		        totalAmount.addCell(amount);
		        document.add(totalAmount);
		        
		        PdfPTable footer = new PdfPTable(1);
		        PdfPCell words = new PdfPCell(); 
		        Phrase AmountTitle = new Phrase("Amount in Words: ", bold);
		        Phrase wordsAmount = new Phrase( currency(invoice.getCurrency(), df2.format(total).toString()));
		        Paragraph ph3 = new Paragraph();
		        ph3.add(AmountTitle);
		        ph3.add(wordsAmount);
		        words.addElement(ph3);
		        words.setVerticalAlignment(Rectangle.BOTTOM);
		        words.setBorder(Rectangle.NO_BORDER);
		        PdfPCell signed = new PdfPCell(new Phrase("Approved By: " + name,bold));
		        signed.setVerticalAlignment(Rectangle.BOTTOM);
		        signed.setBorder(Rectangle.NO_BORDER);
		        
		        footer.addCell(words);
		        footer.addCell(signed);
		        footer.setTotalWidth(document.right(document.rightMargin())
		        	    - document.left(document.leftMargin()));
		        
		        footer.writeSelectedRows(0, -1,
		        	    20 + document.left(document.leftMargin()),
		        	    footer.getTotalHeight() + document.bottom(document.bottomMargin()), 
		        	    writer.getDirectContent());
		        
		        
		        document.close();
		        outputStream.close();
		        
		      return file;
		 
		}
		
		public static String currency (String s, String amount){
			String[] a = amount.split("\\.");
			String full = a[0];
			
			String cents = a[1];
			
			if(s.equals("Ksh"))
				return WordConverter.convert(Integer.parseInt(full)).toUpperCase() + " SHILLINGS AND " + WordConverter.convert(Integer.parseInt(cents)).toUpperCase() + " CENTS";
			else 
				return WordConverter.convert(Integer.parseInt(full)).toUpperCase() + " DOLLARS AND " + WordConverter.convert(Integer.parseInt(cents)).toUpperCase() + " CENTS";
		}
}
