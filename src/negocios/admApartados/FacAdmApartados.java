
package negocios.admApartados;

import java.util.List;
import objetosNegocio.Apartado;
import pvcco.interfaces.IntAdmApartados;

/**
 *
 * @author Raul Karim Sabag Ballesteros
 */
public class FacAdmApartados implements IntAdmApartados{

    private AdmApartados admApartados;
    
    public FacAdmApartados(){
        admApartados = new AdmApartados();
    }
    
    /**
     * Agrega un nuevo apartado a la base de datos. La asignacion de ID's
     * se hace de manera automatica al pasar el objeto de Apartado, al igual 
     * que sus subsequentes tallas apartadas.
     * 
     * @param apartado El apartado a realizarse.
     * @throws Exception 
     */
    @Override
    public void realizarApartado(Apartado apartado) throws Exception {
        admApartados.realizarApartado(apartado);
    }

    @Override
    public void cancelarApartado(Apartado apartado) throws Exception {
        admApartados.cancelarApartado(apartado);
    }

    @Override
    public void abonarApartado(Apartado apartado,float cantidadAbonada) throws Exception {
        admApartados.abonarApartado(apartado, cantidadAbonada);
    }

    @Override
    public void modificarApartado(Apartado apartado) throws Exception {
        admApartados.modificarApartado(apartado);
    }

    @Override
    public Apartado obten(Apartado apartado) throws Exception {
        return admApartados.obten(apartado);
    }

    @Override
    public List<Apartado> obtenApartadosRegistrados() throws Exception {
        return admApartados.obtenApartadosRegistrados();
    }

}
