package starter;


import controlador.controladorPrincipal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class AviarMain {
    public static void main(String[] args) {
        //controladorPrincipal.iniciar();
        SpringApplication.run(AviarMain.class, args);
        controladorPrincipal.iniciaSession();

        //ControlPrincipal.mainj(args);        
        Runtime.getRuntime().addShutdownHook(new Thread(() -> { controladorPrincipal.cerrarSession(); }));
    }
}

