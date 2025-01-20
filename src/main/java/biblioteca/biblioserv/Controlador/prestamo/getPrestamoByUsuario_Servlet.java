package biblioteca.biblioserv.Controlador.prestamo;

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

@WebServlet(name = "prestamobyusuarioServlet", value = "/prestamobyusuario-servlet")
public class getPrestamoByUsuario_Servlet extends HttpServlet {

    DAOPrestamo prestamodao;

    public void init(){ prestamodao = new DAOPrestamo();   }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("usuario_id").isEmpty()){

            int usuario_id =Integer.parseInt(request.getParameter("usuario_id"));

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            Prestamo prestamo = prestamodao.getPrestamoByUsuario(usuario_id);
            if(prestamo!=null){
                String json_responce = conversorjson.writeValueAsString(prestamo);
                impresora.println(json_responce);
            }else{
                String json_responce = conversorjson.writeValueAsString("No existe prestamo para ese usuario");
                impresora.println(json_responce);
            }
        }else{
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            String json_responce = conversorjson.writeValueAsString("No puedes dejar campos vacios");
            impresora.println(json_responce);
        }


    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter impresora = response.getWriter();
        ObjectMapper conversorJson = new ObjectMapper();
        conversorJson.registerModule(new JavaTimeModule());

        List<Prestamo> listaPrestamo  = prestamodao.getAll();

        String json_response = conversorJson.writeValueAsString(listaPrestamo);
        impresora.println(json_response);

    }

    public void destroy(){

    }
}