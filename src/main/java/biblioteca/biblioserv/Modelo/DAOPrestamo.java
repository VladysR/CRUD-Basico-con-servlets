package biblioteca.biblioserv.Modelo;

import jakarta.persistence.*;

public class DAOPrestamo extends DAOGenerico{
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
public DAOPrestamo() {
    super(Prestamo.class,Long.class);
}
public Prestamo getPrestamoByEjemplar(int id){
    Query consulta = em.createQuery("SELECT c from Prestamo c WHERE c.ejemplar =:ejemplar_id");
    consulta.setParameter("ejemplar_id",id);
    return (Prestamo) consulta.getSingleResult();
}
public Prestamo getPrestamoByUsuario(int id){
        Query consulta = em.createQuery("SELECT c from Prestamo c WHERE c.usuario =:usuario_id");
        consulta.setParameter("usuario_id",id);
        return (Prestamo) consulta.getSingleResult();
    }
}
