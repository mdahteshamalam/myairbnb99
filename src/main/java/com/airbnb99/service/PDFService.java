package com.airbnb99.service;

import com.airbnb99.dto.BookingDto;
import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;

@Service
public class PDFService {

    public boolean generatePDF(String fileName,BookingDto dto){
        try {
            Document document = new Document();
           // PdfWriter.getInstance(document, new FileOutputStream("D://PDF//iTextHelloWorld.pdf"));

            PdfWriter.getInstance(document,new FileOutputStream(fileName));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk bookingConfirmation = new Chunk("Booking-confirmation", font);
            Chunk guestName = new Chunk("Guest Name:"+dto.getGuestName(), font);
            Chunk mobile = new Chunk("Mobile:"+dto.getMobile(), font);
            Chunk price = new Chunk("Price Per Night:"+dto.getPrice(), font);
            Chunk totalPrice = new Chunk("Total Price:"+dto.getTotalPrice(), font);

            document.add(bookingConfirmation);
            document.add(new Paragraph("\n"));
            document.add(guestName);
            document.add(new Paragraph("\n"));
            document.add(mobile);
            document.add(new Paragraph("\n"));
            document.add(price);
            document.add(new Paragraph("\n"));
            document.add(totalPrice);
            document.close();

            return true;
        }catch (Exception e){
         e.printStackTrace();
        }
        return false;
    }
}
