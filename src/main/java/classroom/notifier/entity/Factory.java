package classroom.notifier.entity;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarFile;

public class Factory<T> {
	
	private Set<T> elementos;
	private String configuratorPath;
	public Factory(String path){
		this.configuratorPath = path;
	}
	

	public Set<T> ListarImplementaciones(Class<T> interfaceClass) {
		Set<T> listaImplementaciones = new HashSet<T>();

        try {
            Path path = Paths.get(this.configuratorPath);  // Obtener el path de configurator
            File directory = new File(path.toString());
            File[] files = directory.listFiles((dir, name) -> name.endsWith(".jar"));
            if (files != null) {
                for (File file : files) {
                    try (JarFile jarFile = new JarFile(file)) {
						URL[] urls = { new URL("jar:file:" + file.getAbsolutePath() + "!/") };
                        URLClassLoader cl = URLClassLoader.newInstance(urls);

                        jarFile.stream()
                                .filter(e -> e.getName().endsWith(".class"))
                                .forEach(entry -> {
                                    String className = entry.getName().replace("/", ".").replace(".class", "");
                                    try {
                                        Class<?> clazz = cl.loadClass(className);

                                        if (interfaceClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
                                            // Crea una instancia de la clase y la agrega a la lista
                                            @SuppressWarnings("unchecked")
                                            T instance = (T) clazz.getDeclaredConstructor().newInstance();
                                            listaImplementaciones.add(instance);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaImplementaciones;
    }

	
	
}
