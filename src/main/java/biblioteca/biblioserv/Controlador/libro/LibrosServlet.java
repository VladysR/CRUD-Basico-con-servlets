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
import java.util.List;

@WebServlet(name = "librosServlet", value = "/libros-servlet")
public class LibrosServlet extends HttpServlet {

        DAOLibro daolibro;

    public void init(){
            daolibro = new DAOLibro();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("titulo").isEmpty()){

        String titulo = request.getParameter("titulo");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorjson = new ObjectMapper();
        conversorjson.registerModule(new JavaTimeModule());

        Libro libro = daolibro.getLibroByTitulo(titulo);
        if(libro!=null){
        String json_responce = conversorjson.writeValueAsString(libro);
        System.out.println(json_responce);
        impresora.println(json_responce);
        }else{
            String json_responce = conversorjson.writeValueAsString("No existe el libro con ese titulo");
            System.out.println(json_responce);
            impresora.println(json_responce);
        }
        }else{
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            String json_responce = conversorjson.writeValueAsString("No puedes dejar campos vacios");
            System.out.println(json_responce);
            impresora.println(json_responce);
        }


    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorJson = new ObjectMapper();
            conversorJson.registerModule(new JavaTimeModule());

            List<Libro> listaLibros  = daolibro.getLibros();
            System.out.println("En java" + listaLibros);

            String json_response = conversorJson.writeValueAsString(listaLibros);
            System.out.println("En java json" + json_response);
            impresora.println(json_response);

    }

    public void destroy(){

    }
}
