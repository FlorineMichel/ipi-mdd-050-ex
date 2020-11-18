package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/employes")
public class EmployeController {

    @Autowired
    private EmployeRepository employeRepository;

    @RequestMapping(value ="/count", method = RequestMethod.GET,produces = "application/json")
    public Long countEmployes(){
        return employeRepository.count();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Employe getEmploye(@PathVariable Long id){
        Optional<Employe> optionalEmploye = employeRepository.findById(id);
        if (optionalEmploye.isEmpty()){
            //404 error
            throw new EntityNotFoundException("L'employé " + id + " n'a pas été identifié");
        }
        return optionalEmploye.get();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", params = {"matricule"})
    public Employe findByMatricule(@RequestParam String matricule){
        Employe employe = employeRepository.findByMatricule(matricule);
        if(employe == null ){
            //404
            throw new EntityNotFoundException("L'employé avec le " + matricule + " n'a pas été trouvé !");
        }
        return employe;
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json",
            params = {"page", "size", "sortProperty", "sortDirection"})
    public Page<Employe> listEmployes(@RequestParam (value = "page", defaultValue = "0") Integer page,
                                      @RequestParam (value = "size", defaultValue = "10") Integer size,
                                      @RequestParam (value = "sortProperty") String sortProperty,
                                      @RequestParam (value = "sortDirection") String sortDirection){
        if (page < 0){
            //400
            throw new IllegalArgumentException("Le paramètre page doit être positif ou nul !");
        }
        if (size <=0 ||size > 51){
            throw new IllegalArgumentException("Le paramètre size doit être compris entre 0 et 50 !");
        }
        if (!"ASC".equalsIgnoreCase(sortDirection) && !"DESC".equalsIgnoreCase(sortDirection)){
            throw new IllegalArgumentException("Le paramètre sortDirection doit valoir ASC ou DESC !");
        }

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return employeRepository.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.POST,
            value = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Employe createEmploye(@RequestBody Employe employe){
        return employeRepository.save(employe);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Employe updateEmploye(@PathVariable Long id, @RequestBody Employe employe){
        if(!employeRepository.existsById(id)){
            throw new EntityNotFoundException("l'employé " + id + "not found");
        }
        return employeRepository.save(employe);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmploye(@PathVariable Long id){
        employeRepository.deleteById(id);
    }

}
