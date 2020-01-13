package com.etud;

import com.etud.entities.Etudiant;
import com.etud.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootApplication
public class SpringEtudiantThymelealApplication implements CommandLineRunner {
    @Autowired
    private EtudiantRepository etudiantRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringEtudiantThymelealApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        etudiantRepository.save(new Etudiant("fama", "diouf", df.parse("2018-12-10"), "t@gmail.Com","ta.jpg"));
        etudiantRepository.save(new Etudiant("Rase", "diouf", df.parse("2017-01-22"), "t@gmail.Com","sa.jpg"));
        etudiantRepository.save(new Etudiant("Seynabou", "diouf", df.parse("2016-11-18"), "t@gmail.Com","t.jpg"));
        etudiantRepository.save(new Etudiant("Boubacar", "diouf", df.parse("2014-05-25"), "t@gmail.Com","ma.jpg"));


        List<Etudiant> etds = etudiantRepository.findAll();
        etds.forEach(e->System.out.println(e.getNomEtudiant()));
    }
}
