La carpeta Gestor escritorio contiene la aplicación de escritorio creada en Netbeans, llamada RutaAviar. La carpeta puede descargarse y abrirse con Netbeans. 

La carpeta ServidorRA contiene el  servidor REST creado en Netbeans con el que contacta la aplicación de teléfono. Al igual que la carpeta Ruta Aviar en el Gestor escritorio, puede descargarse y abrirse con Netbeans.

La carpeta Aplicación tiene el programa de teléfono y necesita Android Studio para abrirse.

El contenido de las tres carpetas está hecho para usarse con una base de datos SQL. La carpeta Recursos>BS tiene un ejemplo de la base de datos que usan las aplicaciones.

Para que las aplicaciones funcionen hay que editar las urls que usan las aplicaciones para comunicarse entre sí. 

>En el gestor de escritorio hay que modificar el fichero src/main/resources/hibernate.cfg.xml en la línea hibernate.connection.ur a la dirección de la máquina virtual.

>En ServidorRA hay que modificar el fichero src/main/resources/hibernate.cfg.xml en la línea hibernate.connection.url y el string URL en el fichero starter/ControlPrincipal a la dirección de la máquina virtual.

>En la aplicación de teléfono hay que modificar el string url en com.example.rutaaviar.rest.AccesoRest a la dirección del ordenador en el que se encuentra la máquina virtual.
