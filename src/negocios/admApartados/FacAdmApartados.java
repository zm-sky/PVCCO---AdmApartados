
package negocios.admApartados;

import java.util.List;
import objetosNegocio.Apartado;
import objetosNegocio.MovimientoEnApartado;
import objetosNegocio.TallaApartado;
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

     /**
     * Busca una talla apartada existente con el mismo ID dado por el parametro. En caso
     * de que no se encuentre se regresara nulo.
     *
     * @param talla La talla apartada con el ID a buscar.
     * @return La talla apartada en la base de datos si es que existe.
     *
     * @throws Exception
     */
    @Override
    public TallaApartado obten(TallaApartado talla) throws Exception {
        return admApartados.obten(talla);
    }
    
    /**
     * Busca un movimiento en apartado existente con el mismo ID dado por el parametro. En caso
     * de que no se encuentre se regresara nulo.
     *
     * @param talla El movimiento en apartado con el ID a buscar.
     * @return El movimiento en apartado en la base de datos si es que existe.
     *
     * @throws Exception
     */
    @Override
    public MovimientoEnApartado obten(MovimientoEnApartado mov) throws Exception {
        return admApartados.obten(mov);
    }
    
     /* Regresa una lista de todos los registros en TallasApartado
     *
     * @return La lista de todos las tallas apartadas existentes.
     * @throws Exception
     */
    @Override
    public List<TallaApartado> obtenTallasApartadas() throws Exception {
        return admApartados.obtenTallasApartadas();
    }
    
     /* Regresa una lista de todos los registros en MovimientoEnApartado.
     *
     * @return La lista de todos los abonos  existentes.
     * @throws Exception
     */
    @Override
    public List<MovimientoEnApartado> obtenAbonosRegistrados() throws Exception {
        return admApartados.obtenAbonosRegistrados();
    }

}
