
package pruebas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import negocios.admApartados.FacAdmApartados;
import objetosNegocio.Apartado;
import objetosNegocio.Talla;
import objetosNegocio.TallaApartado;
import objetosNegocio.Usuario;
import persistencia.Persistencia;
import pvcco.interfaces.IntAdmApartados;
import pvcco.interfaces.IntPersistencia;

/**
 *
 * @author Raul Karim Sabag Ballesteros
 */
public class PruebaAdmApartados {
    public static void main(String[] args) throws Exception{
        IntAdmApartados adm = new FacAdmApartados();
        IntPersistencia p = new Persistencia();
        
        Apartado apartado = new Apartado();
        
        Calendar c = Calendar.getInstance();
        
        apartado.setEstado('A');
        apartado.setFechaFin(c.getTime());
        apartado.setFechaInicio(c.getTime());
        apartado.setIdApartado("01");
        apartado.setIdUsuario(new Usuario("0"));
        apartado.setTelefono("90");
        apartado.setNombreCliente("Juanito");
        
        Talla t = new Talla("0", "Test", 0, 10, "000x3");
        TallaApartado tallaApartada = new TallaApartado();
        tallaApartada.setIdApartado(apartado);
        tallaApartada.setIdTalla(t);
        tallaApartada.setIdTallaApartado("0");
        tallaApartada.setPrecio(20f);
        
        Talla t2 = new Talla("01", "Test2", 0, 20, "000x4");
        TallaApartado tallaApartada2 = new TallaApartado();
        tallaApartada2.setIdApartado(apartado);
        tallaApartada2.setIdTalla(t2);
        tallaApartada2.setIdTallaApartado("01");
        tallaApartada2.setPrecio(203f);
        
        List<TallaApartado> tallasApartadas = new ArrayList();
        tallasApartadas.add(tallaApartada);
        tallasApartadas.add(tallaApartada);
        
        apartado.setTallaApartadoCollection(tallasApartadas);
        adm.realizarApartado(apartado);
    }
}
