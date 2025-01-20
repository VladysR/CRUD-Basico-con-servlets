package biblioteca.biblioserv.Controlador.usuario;

import biblioteca.biblioserv.Modelo.DAOUsuario;
import biblioteca.biblioserv.Modelo.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "delUsuarioServlet", value = "/delUsuario-servlet")
public class delUsuario_Servlet extends HttpServlet {

    DAOUsuario daoUsuario ;

    public void init(){   daoUsuario= new DAOUsuario(); }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("dni").isEmpty()){

            String dni = request.getParameter("dni");

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            Usuario usuario = daoUsuario.getUsuarioByDni(dni);
            if(usuario!=null){
                String json_responce = conversorjson.writeValueAsString(usuario);
                daoUsuario.deleteUsuario(usuario);
                System.out.println(json_responce);
                impresora.println(json_responce);
            }else{
                String json_responce = conversorjson.writeValueAsString("No existe el usuario con ese dni");
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
