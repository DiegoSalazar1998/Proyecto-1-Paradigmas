package cr.ac.una.beans;

import cr.ac.una.entities.Persona;
import cr.ac.una.entities.PersonaMocion;
import cr.ac.una.services.PersonaMocionService;
import cr.ac.una.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.sql.SQLException;
import java.util.List;
/*
import cr.ac.una.entities.Persona;
import cr.ac.una.services.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.List;
import java.util.Optional;
*/
@Component
@ManagedBean
public class PersonaBean {
    @Autowired
    PersonaService personaService;
    @Autowired
    PersonaMocionService personaMocionService;

    private Persona persona = new Persona();
    private List<Persona> personas;

    @PostConstruct
    public String init() {
        personas = personaService.getAllPersonas();
        return "personaList.xhtml";
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public List<Persona> getPersonas() {
        return personas;
    }

    public void setPersonas(List<Persona> personas) {
        this.personas = personas;
    }

    public Persona obtienePersona(Integer id)
    {
        if(id == null){
            throw new IllegalArgumentException("no se provee el id");
        }
        for (Persona p : personas){
            if(id.equals(p.getID_PERSONA())){
                return p;
            }
        }
        return null;
    }

    public void create() {
        try{
            personaService.createPersona(persona);
            addMessage("Aviso", "Registro insertado correctamente.");
            personas = personaService.getAllPersonas();
        }catch (Exception e){
        } finally {
            persona = new Persona();
        }
    }

    public void delete(){
        Integer id = new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("PersonaId"));
        personaService.deletePersona(id);
        addMessage("Aviso", "Registro eliminado correctamente.");
        personas = personaService.getAllPersonas();
        /*List<PersonaMocion>lpm1 = personaMocionService.getAllPersonaMocions();
        boolean tieneAsignadas = false;
        for(PersonaMocion pm:lpm1){
            if(id == pm.getPersona().getID_PERSONA()){
                tieneAsignadas = true;
                break;
            }else {
                tieneAsignadas = false;
            }
        }
        if(tieneAsignadas == false){
            personaService.deletePersona(id);
            addMessage("Aviso", "Registro eliminado correctamente.");
            personas = personaService.getAllPersonas();
        }else if(tieneAsignadas == true){
            addMessage("Aviso", "La Persona no se puede eliminar pues tiene mociones asignadas.");
        }*/
    }

    public void update(){
        try{
            personaService.updatePersona(persona);
            addMessage("Aviso", "Registro modificado correctamente.");
            personas = personaService.getAllPersonas();
        }catch (Exception e){
        } finally {
            persona = new Persona();
        }
    }
    //@RequestMapping(value = "/personaUpdate.xhtml", method = RequestMethod.GET)
    public String carga(){//Aca se carga la persona y se redirecciona a la ventana update
        Integer id = new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("PersonaId"));
        persona=personaService.findPersona(id);
        return "personaUpdate.xhtml";
    }

    public String vaciar(){//Aca se carga la persona y se redirecciona a la ventana update
        persona=null;
        return "personaCreate.xhtml";
    }

    public void addMessage(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}
