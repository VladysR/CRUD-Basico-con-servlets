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

@WebServlet(name = "addUsuarioServlet", value = "/addUsuario-servlet")
public class addUsuario_Servlet extends HttpServlet {

    DAOUsuario daoUsuario;

    public void init(){   daoUsuario = new DAOUsuario(); }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if(!request.getParameter("dni").isEmpty()
                ||!request.getParameter("nombre").isEmpty()
                ||!request.getParameter("email").isEmpty()
                ||!request.getParameter("password").isEmpty())
        {
            String dni = request.getParameter("dni");
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());
            //VALIDACIONES
            if(dni.matches("^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$")){
               if(email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")){
                   if(password.matches("^(?=.[A-Z])(?=.[a-z])(?=.*\\d).{8,20}$")){
                       if(nombre.matches("[a-zA-Z]+")){

            Usuario usuario = daoUsuario.getUsuarioByDni(dni);
            if(usuario==null){
                usuario.setDni(dni);
                usuario.setNombre(nombre);
                usuario.setEmail(email);
                usuario.setPassword(password);
                usuario.setTipo("normal");
                daoUsuario.addUsuario(usuario);
                String json_responce = conversorjson.writeValueAsString(usuario);
                System.out.println(json_responce);
                impresora.println(json_responce);
            }else {
                String json_responce = conversorjson.writeValueAsString("Ya existe un usuario con ese dni");
                System.out.println(json_responce);
                impresora.println(json_responce);
            }
                       }else{
                           String json_responce = conversorjson.writeValueAsString("El nombre es invalido");
                           System.out.println(json_responce);
                           impresora.println(json_responce);
                       }
                   } else{
                       String json_responce = conversorjson.writeValueAsString("La contraseña debe tener una minuscula,una mayuscula y entre 8 y 20 caracteres");
                       System.out.println(json_responce);
                       impresora.println(json_responce);
                   }
               } else{
                   String json_responce = conversorjson.writeValueAsString("El email es invalido");
                   System.out.println(json_responce);
                   impresora.println(json_responce);
               }
            }else{
                String json_responce = conversorjson.writeValueAsString("El dni es invalido");
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

/* HIPER ULTRA MEGA UNIVERSAR VALIDACIONES
* public static boolean nombre_valido(String nombre){
        return nombre.matches("[a-zA-Z]+");
    }

    public static boolean email_valido(String email){
        return email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$");
    }

    public static boolean dni_valido(String dni){
        return dni.matches("^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$");
    }

    public static boolean password_valido(String password){
        return password.matches("^(?=.[A-Z])(?=.[a-z])(?=.*\\d).{8,20}$");
    }

    public static boolean tipo_valido(String tipo){
        return tipo.matches("^[AN]$");
    }
* */
