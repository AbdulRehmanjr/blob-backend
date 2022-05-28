package com.file.management.DAO;

import com.file.management.Model.file;

import org.springframework.data.jpa.repository.JpaRepository;




public  interface fileRepository  extends JpaRepository<file,Long>{
    
}
