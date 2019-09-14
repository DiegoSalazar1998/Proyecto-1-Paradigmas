package cr.ac.una.beans;


import cr.ac.una.entities.Mocion;
import cr.ac.una.entities.TipoMocion;
import cr.ac.una.services.MocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import java.util.List;

@Component
@ManagedBean
public class MocionBean {

    @Autowired
    MocionService mocionService;
    private Mocion mocion = new Mocion();
    private TipoMocion tipoMocion = new TipoMocion();
    private List<Mocion> mociones;

    @PostConstruct
    public void init() {
        mociones = mocionService.getAllMociones();
    }

    public Mocion getMocion() {
        return mocion;
    }

    public void setMocion(Mocion mocion) {
        this.mocion = mocion;
    }

    public List<Mocion> getMociones() {
        return mociones;
    }

    public void setMociones(List<Mocion> mociones) {
        this.mociones = mociones;
    }

    public TipoMocion getTipoMocion() { return tipoMocion; }

    public void setTipoMocion(TipoMocion tipoMocion) {
        this.tipoMocion = tipoMocion; }

    public void create() {
        mocionService.createMocion(mocion);
        mociones = mocionService.getAllMociones();
    }

    public void delete(){
        Integer id = new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("MocionId"));
        mocionService.deleteMocion(id);
        mociones = mocionService.getAllMociones();
    }

    public void update(){
        mocionService.updateMocion(mocion);
        mociones = mocionService.getAllMociones();
    }
    //@RequestMapping(value = "/personaUpdate.xhtml", method = RequestMethod.GET)
    public String carga(){//Aca se carga la persona y se redirecciona a la ventana update
        Integer id = new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("MocionId"));
        mocion=mocionService.findMocion(id);
        return "mocionUpdate.xhtml";
    }

    public String vaciar(){//Aca se carga la persona y se redirecciona a la ventana update
        mocion=null;
        return "mocionCreate.xhtml";
    }
}
