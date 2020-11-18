package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import com.ipiecoles.java.mdd050.repository.ManagerRepository;
import com.ipiecoles.java.mdd050.repository.TechnicienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.IllegalFormatCodePointException;
import java.util.Optional;

@RestController
@RequestMapping(value = "/managers")
public class ManagerController {

    @Autowired
    private TechnicienRepository technicienRepository;
    @Autowired
    private EmployeRepository employeRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/{idManager}/equipe/{idTechnicien}/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTechnicienFromEquipe(@PathVariable Long idManager,
                                           @PathVariable Long idTechnicien){
        //recupere technicien
        Optional<Technicien> technicienOptional = technicienRepository.findById(idTechnicien);
        if (technicienOptional.isEmpty()){
            throw new EntityNotFoundException("Impossible de trouver le technicien d'identifiant " + idTechnicien);
        }
        // puis set manager = null
        Technicien technicien = technicienOptional.get();
        if (technicien.getManager() == null){
            throw new IllegalArgumentException("Le technicien n'a pas de manager");
        }
        if (!technicien.getManager().getId().equals(idManager)){
            throw new IllegalArgumentException("Le manager d'identifiant " + idManager + "n'a pas le technicien " +
                    "d'identifiant " + idTechnicien + "dans son équipe");
        }
        technicien.setManager(null);
        //save modification
        technicienRepository.save(technicien);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/equipe/{matricule}/add")
    public void addTechnicienToEquipe(@PathVariable Long id, @PathVariable String matricule){
        //recup technicien a partir de son id
        Employe employe = employeRepository.findByMatricule(matricule);
        if (!(employe instanceof Technicien)){
            throw new IllegalArgumentException("L'employé de matricule " + matricule + " n'existe pas ou n'est pas un technicien !");
        }
        Technicien technicien = (Technicien)employe;

        if (technicien.getManager() != null){
            throw new IllegalArgumentException("Le technicien de matricule " + matricule + " a déjà un manager !");
        }

        //recup manager a partir de son id
        Optional<Manager> managerOptional = managerRepository.findById(id);
        if (managerOptional.isEmpty()){
            throw new EntityNotFoundException("Le manager d'identifiant " + id + " n'existe pas !");
        }
        Manager manager = managerOptional.get();

        //set manager au tech + save
        technicien.setManager(manager);

        technicienRepository.save(technicien);
    }
}
