package modelo.vo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Avistamientos;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-12-12T13:40:38", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Pajaros.class)
public class Pajaros_ { 

    public static volatile SingularAttribute<Pajaros, String> raza;
    public static volatile SingularAttribute<Pajaros, Integer> id;
    public static volatile ListAttribute<Pajaros, Avistamientos> avistamientosList;
    public static volatile SingularAttribute<Pajaros, String> nombre;

}