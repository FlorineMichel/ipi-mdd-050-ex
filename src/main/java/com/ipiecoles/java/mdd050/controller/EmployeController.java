package com.ipiecoles.java.mdd050.controller;

import com.ipiecoles.java.mdd050.exception.EmployeException;
import com.ipiecoles.java.mdd050.model.Commercial;
import com.ipiecoles.java.mdd050.model.Employe;
import com.ipiecoles.java.mdd050.model.Manager;
import com.ipiecoles.java.mdd050.model.Technicien;
import com.ipiecoles.java.mdd050.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Employe findById(@PathVariable Long id){
        return employeRepository.findById(id).get();
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json", params = {"matricule"})
    public Employe findByMatricule(@RequestParam String matricule){
        return employeRepository.findByMatricule(matricule);
    }

    @RequestMapping(method = RequestMethod.GET,
            produces = "application/json",
            params = {"page", "size", "sortProperty", "sortDirection"})
    public Page<Employe> listEmployes(@RequestParam (value = "page", defaultValue = "0") Integer page,
                                      @RequestParam (value = "size", defaultValue = "10") Integer size,
                                      @RequestParam (value = "sortProperty") String sortProperty,
                                      @RequestParam (value = "sortDirection") String sortDirection){
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(sortDirection), sortProperty);
        return employeRepository.findAll(pageRequest);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void CreateManager(@RequestBody Manager manager){
        employeRepository.save(manager);
    }

    /*CUD for Manager*/
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
     public void UpdateManager(@RequestBody Manager manager){
        employeRepository.save(manager);
     }

     @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json")
     @ResponseStatus(value = HttpStatus.NO_CONTENT)
     public void DeleteManager(Manager manager){
        employeRepository.delete(manager);
     }

    /*CUD for Commercial*/
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void CreateCommercial(@RequestBody Commercial commercial){
        employeRepository.save(commercial);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void UpdateCommercial(@RequestBody Commercial commercial){
        employeRepository.save(commercial);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void DeleteCommercial(Commercial commercial){
        employeRepository.delete(commercial);
    }

    /*CUD for Technicien*/
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void CreateTechnicien(@RequestBody Technicien technicien){
        employeRepository.save(technicien);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json", produces = "application/json")
    public void UpdateTechnicien(@RequestBody Technicien technicien){
        employeRepository.save(technicien);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void DeleteTechnicien(Technicien technicien){
        employeRepository.delete(technicien);
    }

}
