package modelo.vo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.vo.Avistamientos;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-12-12T17:03:03", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Usuarios.class)
public class Usuarios_ { 

    public static volatile SingularAttribute<Usuarios, String> password;
    public static volatile SingularAttribute<Usuarios, Boolean> admin;
    public static volatile SingularAttribute<Usuarios, Integer> id;
    public static volatile ListAttribute<Usuarios, Avistamientos> avistamientosList;
    public static volatile SingularAttribute<Usuarios, String> nombre;

}