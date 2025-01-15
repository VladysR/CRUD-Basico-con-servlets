package biblioteca.biblioserv.Modelo;

import jakarta.persistence.*;

import java.util.List;

public class DAOLibro {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("unidad-biblioteca");
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    Libro libro = new Libro();
public DAOLibro() {

}

public boolean addLibro(Libro libro){
    tx.begin();
    em.persist(libro);
    tx.commit();
    return true;
}

public Libro getLibroByTitulo(String titulo){
    Query consulta = em.createQuery("SELECT c from Libro c WHERE c.titulo =:titulo");
    consulta.setParameter("titulo",titulo);
    return (Libro) consulta.getSingleResult();
}

public Libro getLibroByIsbn(String isbn){
        return em.find(Libro.class, isbn);
    }

public List<Libro> getLibros(){
    return em.createQuery("from Libro").getResultList();
}

public Libro updateLibro(Libro libro){
    tx.begin();
    em.merge(libro);
    tx.commit();
    return libro;
}

public boolean deleteLibro(Libro libro){
    tx.begin();
    em.remove(libro);
    tx.commit();
    return true;
}

}
