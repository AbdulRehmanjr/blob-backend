package com.file.management.Controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import com.file.management.DAO.fileRepository;
import com.file.management.Model.file;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/file")
public class fileController {
    
    @Autowired
    private fileRepository filerepo;

    @PostMapping("/save")
    public ResponseEntity<?> saveFile(@RequestParam("file") MultipartFile savefile){

        file f = new file();
    //    directly save the file  not opening the content in it
        f.setFileName(savefile.getOriginalFilename());
        f.setFileType(savefile.getContentType());
        try {
            f.setFileData(savefile.getBytes());
        } catch (IOException e) {
            return ResponseEntity.ok(e.getMessage());    
        }
        filerepo.save(f);
        return ResponseEntity.ok("File saved successfully");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileId") long id, HttpServletResponse response){
        

        Optional<file> file = filerepo.findById(id);

        byte[] data = file.get().getFileData();
        // telling for downloading file
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(file.get().getFileType()))
        .header("Content-Disposition","attachment; filename=" + file.get().getFileName() )
        .body(data);
    }

}
