package biblioteca.biblioserv.Controlador.libro;


import biblioteca.biblioserv.Modelo.DAOLibro;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import biblioteca.biblioserv.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "updatelibro", value = "/update-libro-servlet")
public class UpdateLibroServlet extends HttpServlet {

    DAOLibro daolibro;

    public void init(){
        daolibro = new DAOLibro();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (!request.getParameter("titulo").isEmpty()
                &&!request.getParameter("autor").isEmpty()
                &&!request.getParameter("isbn").isEmpty()){

        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        String isbn = request.getParameter("isbn");

        Libro libro = new Libro();
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIsbn(isbn);

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorjson = new ObjectMapper();
        conversorjson.registerModule(new JavaTimeModule());
        if(daolibro.getLibroByIsbn(isbn) != null){

        daolibro.updateLibro(libro);

        String json_responce = conversorjson.writeValueAsString(libro);
        System.out.println(json_responce);
        impresora.println(json_responce);
        }else{
            String json_responce = conversorjson.writeValueAsString("El libro indicado no existe");
            System.out.println(json_responce);
            impresora.println(json_responce);
        }
        }else{

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            String json_responce = conversorjson.writeValueAsString("No se pueden dejar campos vacios");
            System.out.println(json_responce);
            impresora.println(json_responce);

        }


    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public void destroy(){

    }
}