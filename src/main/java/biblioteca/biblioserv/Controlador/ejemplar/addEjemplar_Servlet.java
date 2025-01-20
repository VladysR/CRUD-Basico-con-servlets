package biblioteca.biblioserv.Controlador.ejemplar;

import biblioteca.biblioserv.Modelo.DAOEjemplar;
import biblioteca.biblioserv.Modelo.DAOLibro;
import biblioteca.biblioserv.Modelo.Ejemplar;
import biblioteca.biblioserv.Modelo.Libro;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "addEjemplarServlet", value = "/addEjemplar-servlet")
public class addEjemplar_Servlet extends HttpServlet {

    DAOEjemplar ejemplarDAO;
    DAOLibro libroDAO;

    public void init(){  ejemplarDAO=new DAOEjemplar();
                            libroDAO=new DAOLibro();}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("isbn").isEmpty()
                ||!request.getParameter("estado").isEmpty()){
            if(request.getParameter("estado").equalsIgnoreCase("Disponible")
                    ||request.getParameter("estado").equalsIgnoreCase("Prestado")
                    ||request.getParameter("estado").equalsIgnoreCase("Dañado")){


            String isbn = request.getParameter("isbn");
            String estado = request.getParameter("estado");

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            Ejemplar ejemplar = new Ejemplar();
            Libro libro = libroDAO.getLibroByIsbn(isbn);
            ejemplar.setLibro(libro);
            ejemplar.setEstado(estado);
            if(libro!=null){
                String json_responce = conversorjson.writeValueAsString(ejemplar);
                System.out.println(json_responce);
                impresora.println(json_responce);
                ejemplarDAO.add(ejemplar);
            }else{
                String json_responce = conversorjson.writeValueAsString("El libro con el isbn"+isbn+" no existe");
                System.out.println(json_responce);
                impresora.println(json_responce);
            }
            }else{
                PrintWriter impresora = response.getWriter();
                ObjectMapper conversorjson = new ObjectMapper();
                conversorjson.registerModule(new JavaTimeModule());

                String json_responce = conversorjson.writeValueAsString("Es estado introducido no existe");
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
    }

    public void destroy(){

    }
}