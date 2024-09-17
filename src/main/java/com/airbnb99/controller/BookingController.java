package com.airbnb99.controller;

import com.airbnb99.dto.BookingDto;
import com.airbnb99.entity.Booking;
import com.airbnb99.entity.Property;
import com.airbnb99.entity.PropertyUser;
import com.airbnb99.repository.BookingRepository;
import com.airbnb99.repository.PropertyRepository;
import com.airbnb99.service.BucketService;
import com.airbnb99.service.PDFService;
import com.airbnb99.service.SmsService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private BookingRepository bookingRepository;
    private PropertyRepository propertyRepository;
    private BucketService bucketService;
    private PDFService pdfService;
    private SmsService smsService;
    //  Booking createdBooking=null;
    public BookingController(BookingRepository bookingRepository, PropertyRepository propertyRepository, BucketService bucketService, PDFService pdfService, SmsService smsService) {
        this.bookingRepository = bookingRepository;
        this.propertyRepository = propertyRepository;
        this.bucketService = bucketService;
        this.pdfService = pdfService;
        this.smsService = smsService;
    }
    @PostMapping("/createBooking/{propertyId}")
    public ResponseEntity<?> createBooking(
            @RequestBody Booking booking,
            @AuthenticationPrincipal PropertyUser user,
            @PathVariable long propertyId
            ) throws IOException {
        booking.setPropertyUser(user);
//        Property property = booking.getProperty();
//        long propertyId=property.getId();
//        Property completePropertyInfo = propertyRepository.findById(propertyId).get();

      //  Booking createdBooking = bookingRepository.save(booking);

        Optional<Property> byId = propertyRepository.findById(propertyId);
        Property property = byId.get();
        int propertyPrice=property.getNightlyPrice();
        int totalNights = booking.getTotalNights();
        int totalPrice=propertyPrice*totalNights;
        booking.setProperty(property);
        booking.setTotalPrice(totalPrice);

        Booking createdBooking = bookingRepository.save(booking);
        BookingDto dto=new BookingDto();
        dto.setBookingId(createdBooking.getId());
        dto.setGuestName(createdBooking.getGuestName());
        dto.setPrice(propertyPrice);
        dto.setTotalPrice(createdBooking.getTotalPrice());
       // dto.setMobile(createdBooking.);

//        //create PDF with Booking confirmation
//
        boolean b = pdfService.generatePDF("C://Users//mdahtesham//Desktop//Notes//pdf//"+"booking-confirmation-id"+createdBooking.getId()+".pdf",dto);
        if (b){
            //Upload your file into bucket
            MultipartFile file=BookingController.convert("C://Users//mdahtesham//Desktop//Notes//pdf//"+"booking-confirmation-id"+createdBooking.getId()+".pdf");
            String uploadedFileUrl = bucketService.uploadFile(file, "myairbnbah");
            smsService.sendSms("+917739243670","Test");
            System.out.println(uploadedFileUrl);

        }else{
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(createdBooking, HttpStatus.CREATED);
    }
//    @PostMapping("/createPdf")
//      public void createPdf(){
//        pdfService.generatePDF("D://PDF//"+"booking-confirmation-id"+createdBooking.getId()+".pdf");
//    }


    public static MultipartFile convert(String filePath) throws IOException{

        //Load the file from specified path
        File file=new File(filePath);

        //create a fileSystemResource from the file
      //  FileSystemResource fileSystemResource=new FileSystemResource(file);

        //read the file content into a byte array
        byte[] fileContent= Files.readAllBytes(file.toPath());

        //Convert FileSystemResource from the file
        Resource resource=new ByteArrayResource(fileContent);

        MultipartFile multipartFile=new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length==0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
               Files.write(dest.toPath(),fileContent);
            }
        };
        return multipartFile;
    }
}
