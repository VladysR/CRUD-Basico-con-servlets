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
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "updatePrestamoServlet", value = "/updatePrestamo-servlet")
public class updatePrestamo_Servlet extends HttpServlet {

    DAOPrestamo prestamodao;
    DAOEjemplar ejemplardao;
    DAOUsuario usuariodao;

    public void init(){   prestamodao=new DAOPrestamo();
    ejemplardao=new DAOEjemplar();
    usuariodao=new DAOUsuario();}

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("usuario_id").isEmpty()
                ||!request.getParameter("ejemplar_id").isEmpty()
                ||!request.getParameter("fechadev").isEmpty()
                ||!request.getParameter("id").isEmpty()){

            int id = Integer.parseInt(request.getParameter("id"));
            int usuario_id =Integer.parseInt(request.getParameter("usuario_id"));
            int ejemplar_id =Integer.parseInt(request.getParameter("ejemplar_id"));
            LocalDate fechadev = LocalDate.parse(request.getParameter("fechadev"));


            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            Prestamo prestamo = prestamodao.getPrestamoByEjemplar(ejemplar_id);
            if(prestamo!=null){
                Ejemplar ejemplar = (Ejemplar) ejemplardao.getById(ejemplar_id);
                if(ejemplar!=null){
                    Usuario usuario = usuariodao.getUsuarioById(usuario_id);
                    if(usuario!=null){
                        prestamo.setUsuario(usuario);
                        prestamo.setEjemplar(ejemplar);
                        prestamo.setFechaDevolucion(fechadev);

                        prestamodao.update(prestamo);
                        String json_responce = conversorjson.writeValueAsString(prestamo);
                        impresora.println(json_responce);
                    }else {impresora.println("El usuario no existe");}
                }else {impresora.println("El ejemplar no existe");}
            }else{
                impresora.println("No hay prestamo para este ejemplar");
            }
        }else{
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            impresora.println("No puedes dejar campos vacios");
        }


    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public void destroy(){

    }
}