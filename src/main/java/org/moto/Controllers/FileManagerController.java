package org.moto.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.ALL;

/**
 * Created by Nahid on 4/17/2016.
 */

@Controller
public class FileManagerController {



    @Autowired
    public Environment env;

    @RequestMapping(value = "/")
    public String index(){

        return "index";
    }

    @RequestMapping(value ="/fileup", method = RequestMethod.GET)
    public String FileUpload(){

        return "fileup";
    }

    @RequestMapping(value = "/filedown")
    public String  FileDownload(){
        return "filedownload";
    }

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    @ResponseBody
    public String receiveFile(@RequestParam("uploadfile") MultipartFile uploadFile){

        String filename = uploadFile.getOriginalFilename();
        String directory = env.getProperty("pathForFile");
        String filepath = Paths.get(directory,filename).toString();


        System.out.println("FILE RECEived -----------------------------"+directory);

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream( new File(filepath)));
            stream.write(uploadFile.getBytes());
            stream.close();

        } catch (Exception e) {

            System.out.println(e.toString());
            return e.toString();
        }
        return "File uploaded successfull";
    }





    @RequestMapping(value = "/image/{name}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE )
    @ResponseBody
    public ClassPathResource getImageFile(@PathVariable("name") String fileName) {
        return new ClassPathResource("images/"+fileName+".jpg");
    }

    @RequestMapping(value = "/pdf/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE )
    @ResponseBody
    public ClassPathResource getPdfFile(@PathVariable("name") String fileName) {

        System.out.println(fileName);
        return new ClassPathResource("PDF_files/"+fileName+".pdf");
    }



}
