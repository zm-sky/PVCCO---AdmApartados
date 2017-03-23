
package negocios.admApartados;

import java.util.ArrayList;
import java.util.List;
import negocios.admInventario.FacAdmInventario;
import objetosNegocio.Apartado;
import objetosNegocio.MovimientoEnApartado;
import objetosNegocio.Talla;
import objetosNegocio.TallaApartado;
import persistencia.Persistencia;
import pvcco.interfaces.IntAdmInventario;
import pvcco.interfaces.IntPersistencia;

/**
 *
 * @author Roberto Pedraza Coello
 */
public class AdmApartados {

    /**
     * Sirve para que no se pueda instanciar esta clase fuera de
     * el paquete al que pertenece.
     */
    protected AdmApartados(){
        
    }
    
     /**
     * Agrega un nuevo apartado a la base de datos. La asignacion de ID's
     * se hace de manera automatica al pasar el objeto de Apartado, al igual 
     * que sus subsequentes tallas apartadas.
     * 
     * @param apartado El apartado a realizarse.
     * @throws Exception 
     */
    public void realizarApartado(Apartado apartado) throws Exception{
        IntPersistencia persistencia = new Persistencia();
        IntAdmInventario admInventario = new FacAdmInventario();
        
        //Tenemos que crear una instancia vacia del apartado en la base de datos sin la lista
        //de tallas que se van a apartar. Esto es porque nos tirara error, ya que no existen
        //registros en la tabla de TallaApartado y en la lista del apartado si.
        List<TallaApartado> tallasApartadas = new ArrayList(apartado.getTallaApartadoCollection());
        
        apartado.setTallaApartadoCollection(new ArrayList());
        
        //Agregamos el apartado a la base de datos, ya que las tallas apartadas
        //no podran agregarse si este no existe dentro de las tablas.
        persistencia.agregar(apartado);
        
        //Vamos a obtener el ID del ultimo registro encontrado.
        //De aqui en adelante nos vamos a encargar de asignar un ID automatico
        //a las tallas que estan siendo apartadas.
        int idTallaApartadaNueva = persistencia.obtenTallasApartadas().size()+1;
        
        for(TallaApartado tallaApartada : tallasApartadas){
            //Asignamos el ID automatico nuevo a la talla apartada..
            tallaApartada.setIdTallaApartado(String.valueOf(idTallaApartadaNueva));
            //Y la agregamos a la base de datos.
            persistencia.agregar(tallaApartada);
            
            //Le sumamos uno al ID que se va a asignar para la siguiente talla.
            idTallaApartadaNueva++;
        }
        
        //Le asignamos de nuevo la lista al apartado en caso de que se ocupe.
        apartado.setTallaApartadoCollection(tallasApartadas);
        
        //Le resta uno al inventario regular y le suma uno al inventario apartado.
        admInventario.modificarPorApartadoAgregado(apartado);
    }
    
    public void cancelarApartado(Apartado apartado) throws Exception {
        
    }

    public void modificarApartado(Apartado apartado) throws Exception {
        
    }
    
    public void abonarApartado(Apartado apartado, float cantidadAbonada) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        //Vamos a verificar cuanto se ha abonado a este apartado.
        List<MovimientoEnApartado> listaMovimientos = persistencia.obtenAbonosRegistrados();
        
        //Un contador de cuanto se ha pagado.
        float cantidadPagada = cantidadAbonada;
        
        //Filtraremos los movimientos para los que sean de este apartado.
        for(MovimientoEnApartado pago : listaMovimientos)
            cantidadPagada+=pago.getCantidadAbonada();
        
        //Ahora vamos a verificar si se ocupa liquidar el apartado.
        //En todo caso, pasaremos a liquidarlo.
        if(cantidadPagada >= apartado.getPrecioTotal())
            liquidarApartado(apartado);
        
        //Tenemos que registrar este movimiento en la base de datos.
        MovimientoEnApartado mov = new MovimientoEnApartado();
        mov.setCantidadAbonada(cantidadAbonada);
        
        //Agregamos lo que se abono a la base de datos.
        persistencia.agregar(mov);
    }
    
    /**
     * Cambia el estado de un apartado dentro de la base de datos.
     * Esto significa que el apartado ya no estara activo y no se podra
     * abonar a este. 
     * 
     * Tambien se modifica el inventario de apartados de cada una de las
     * tallas que fueron apartadas.
     * 
     * @param apartado El apartado a liquidarse.
     */
    private void liquidarApartado(Apartado apartado) throws Exception{
        IntPersistencia persistencia = new Persistencia();
        IntAdmInventario inv = new FacAdmInventario();
        apartado.setEstado('I');
        
        //Mandamos a modificar el inventario por liquidacion.
        inv.modificarPorApartadoLiquidado(apartado);
    }
    
    public Apartado obten(Apartado apartado) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obten(apartado);
    }

    public List<Apartado> obtenApartadosRegistrados() throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obtenApartados();
    }
    
}
