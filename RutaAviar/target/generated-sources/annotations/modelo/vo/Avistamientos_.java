package modelo.vo;

import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Pajaros;
import modelo.vo.Usuarios;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2026-03-08T12:18:02", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Avistamientos.class)
public class Avistamientos_ { 

    public static volatile SingularAttribute<Avistamientos, String> lugar;
    public static volatile SingularAttribute<Avistamientos, Date> fechaAvistamiento;
    public static volatile SingularAttribute<Avistamientos, Integer> id;
    public static volatile SingularAttribute<Avistamientos, Usuarios> usuarioId;
    public static volatile SingularAttribute<Avistamientos, Pajaros> pajaroId;

}