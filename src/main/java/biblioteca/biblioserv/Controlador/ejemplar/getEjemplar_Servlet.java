package biblioteca.biblioserv.Controlador.ejemplar;

import biblioteca.biblioserv.Modelo.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ejemplaresServlet", value = "/ejemplares-servlet")
public class getEjemplar_Servlet extends HttpServlet {

    DAOEjemplar ejemplarDAO ;

    public void init(){  ejemplarDAO = new DAOEjemplar();  }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("isbn").isEmpty()){

            String isbn = request.getParameter("isbn");

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            Ejemplar ejemplar = ejemplarDAO.getEjemplarByISBN(isbn);
            if(ejemplar!=null){
                String json_responce = conversorjson.writeValueAsString(ejemplar);
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

        List<Ejemplar> listaEjemplares  =ejemplarDAO.getAll();
        System.out.println("En java" + listaEjemplares);

        String json_response = conversorJson.writeValueAsString(listaEjemplares);
        System.out.println("En java json" + json_response);
        impresora.println(json_response);

    }

    public void destroy(){

    }
}
