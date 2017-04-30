
package negocios.admApartados;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import negocios.admInventario.FacAdmInventario;
import objetosNegocio.Apartado;
import objetosNegocio.MovimientoEnApartado;
import objetosNegocio.TallaApartado;
import persistencia.Persistencia;
import pvcco.interfaces.IntAdmInventario;
import pvcco.interfaces.IntPersistencia;

/**
 *
 * @author Roberto Pedraza Coello
 * Test
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
        List<TallaApartado> tallasApartadas = new ArrayList(apartado.getTallaapartadoList());
        
        apartado.setTallaapartadoList(new ArrayList());
        
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
        apartado.setTallaapartadoList(tallasApartadas);
        
        //Le resta uno al inventario regular y le suma uno al inventario apartado.
        admInventario.modificarPorApartadoAgregado(apartado);
    }
    
    /**
     * Simplemente cambia el apartado de activo, a inactivo.
     * Tambien hace llamado al administrador de inventario para mover zapatos a donde deban.
     * 
     * @param apartado
     * @throws Exception 
     */
    public void cancelarApartado(Apartado apartado) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        IntAdmInventario adm = new FacAdmInventario();
        
        adm.modificarPorApartadoCancelado(apartado);
        
        apartado.setEstado('I');
        persistencia.modificar(apartado);
    }

    public void modificarApartado(Apartado apartado) throws Exception {
        
    }
    
    public void abonarApartado(Apartado apartado, float cantidadAbonada) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        System.out.println("Nuevo");
        //Vamos a verificar cuanto se ha abonado a este apartado.
        List<MovimientoEnApartado> listaMovimientos = persistencia.obtenAbonosRegistrados();
        
        //Un contador de cuanto se ha pagado.
        float cantidadPagada = cantidadAbonada;
        
        //Filtraremos los movimientos para los que sean de este apartado.
        
        for(MovimientoEnApartado pago : listaMovimientos){
            if(pago.getIdApartado().getIdApartado().equalsIgnoreCase(apartado.getIdApartado()))
                cantidadPagada+=pago.getCantidadAbonada();
            
        }
        
        //Ahora vamos a verificar si se ocupa liquidar el apartado.
        //En todo caso, pasaremos a liquidarlo.
        if(cantidadPagada >= apartado.getPrecioTotal())
            liquidarApartado(apartado);
        
        //Tenemos que registrar este movimiento en la base de datos.
        MovimientoEnApartado mov = new MovimientoEnApartado();
        mov.setCantidadAbonada(cantidadAbonada);
        
        //Tambien les asignaremos un ID al movimiento.
        int IDNuevo = listaMovimientos.size();
        mov.setIdMovimientoApartado(String.valueOf(IDNuevo));
        mov.setFecha(Calendar.getInstance().getTime());
        mov.setIdApartado(apartado);
        
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
        persistencia.modificar(apartado);
    }
    
    public Apartado obten(Apartado apartado) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obten(apartado);
    }

    public List<Apartado> obtenApartadosRegistrados() throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obtenApartados();
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
    public TallaApartado obten(TallaApartado talla) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obten(talla);
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
    public MovimientoEnApartado obten(MovimientoEnApartado mov) throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obten(mov);
    }
    
     /* Regresa una lista de todos los registros en TallasApartado
     *
     * @return La lista de todos las tallas apartadas existentes.
     * @throws Exception
     */
    public List<TallaApartado> obtenTallasApartadas() throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obtenTallasApartadas();
    }
    
     /* Regresa una lista de todos los registros en MovimientoEnApartado.
     *
     * @return La lista de todos los abonos  existentes.
     * @throws Exception
     */
    public List<MovimientoEnApartado> obtenAbonosRegistrados() throws Exception {
        IntPersistencia persistencia = new Persistencia();
        
        return persistencia.obtenAbonosRegistrados();
    }

}
