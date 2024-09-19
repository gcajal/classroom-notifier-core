package classroom.notifier.entity;

public class Comision {

	 private String Nro;
	 private String Materia;

	 public Comision(String Nro, String Materia)
	 {
	     this.Nro = Nro;
	     this.Materia = Materia;
	 }
	 
	 public String getNro() {
		 return this.Nro;
	 }
	 
	 public String getMateria() {
		 return this.Materia;
	 }
}
