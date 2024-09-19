package classroom.notifier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import classroom.notifier.entity.Comision;
import classroom.notifier.entity.Factory;
import classroom.notifier.entity.MedioNotificacion;

class Comparador {

	private Map<String, String> Asignacion;
	private Set<String> AsignacionNotificar;
	private Factory<MedioNotificacion> Factory;
	
	Comparador(){
		this.Asignacion = new HashMap<String, String>();
		this.AsignacionNotificar = new HashSet<String>();
		this.Factory = new Factory<MedioNotificacion>(System.getProperty("user.dir"));
	}
	
    public Boolean ComprobarNovedades(Map<String, String> AsignacionNueva)
    {
        Boolean resultado = true;
        Asignacion.put("001", "7000");
		AsignacionNotificar.add("001");
        
        Map<String, String> diferencia = AsignacionNueva.entrySet().stream().filter(nuevo -> this.AsignacionNotificar.contains(nuevo.getKey()))
        		.filter( nuevo -> this.Asignacion.getOrDefault(nuevo.getKey(),"") != nuevo.getValue())
        		.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        

        if (!diferencia.isEmpty())
        {
            try
            {
                Set<MedioNotificacion> Notificadores = this.Factory.ListarImplementaciones(MedioNotificacion.class);
                if (Notificadores.size() > 0)
                {
                	Notificadores.forEach(notificar -> 
                    {
                        //notificar.Subscribe(observer);
                        notificar.Notificar(diferencia);
                    });

                }
            }
            catch (Exception ex)
            {
                resultado = false;
            }
        }

        return resultado;
    }
}
