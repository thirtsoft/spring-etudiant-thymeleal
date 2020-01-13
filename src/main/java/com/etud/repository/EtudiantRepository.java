package com.etud.repository;

import com.etud.entities.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    public List<Etudiant> findByNomEtudiant(String nomEtudiant);
    public Page<Etudiant> findByNomEtudiant(String nomEtudiant, Pageable pageable);
    @Query("select e from Etudiant e where e.nomEtudiant like :x")
    public Page<Etudiant> chercherEtudiants(@Param("x") String mc, Pageable pageable);
    @Query("select e from Etudiant e where e.dateNaissance >:x and e.dateNaissance <:y")
    public List<Etudiant> chercherEtudiants(@Param("x") Date d1, @Param("y") Date d2);
}
