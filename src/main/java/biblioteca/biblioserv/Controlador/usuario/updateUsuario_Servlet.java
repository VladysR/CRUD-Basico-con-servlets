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
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@WebServlet(name = "updateUsuarioServlet", value = "/updateUsuario-servlet")
public class updateUsuario_Servlet extends HttpServlet {

    DAOUsuario daoUsuario;

    public void init() {daoUsuario = new DAOUsuario();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (!request.getParameter("dni").isEmpty()
                || !request.getParameter("nombre").isEmpty()
                || !request.getParameter("email").isEmpty()
                || !request.getParameter("password").isEmpty()
                || !request.getParameter("id").isEmpty()
                || !request.getParameter("tipo").isEmpty()) {

            //ALMACENAMIENTO DE DATOS

            int id = Integer.parseInt(request.getParameter("id"));
            String dni = request.getParameter("dni");
            String nombre = request.getParameter("nombre");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String tipo = request.getParameter("tipo");
            LocalDate penalizacion = LocalDate.parse(request.getParameter("penalizacion"));

            //IMPRESORA

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            //VALIDACIONES

            if (dni.matches("^\\d{8}[TRWAGMYFPDXBNJZSQVHLCKE]$")) {
                if (email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
                    if (password.matches("^(?=.[A-Z])(?=.[a-z])(?=.*\\d).{8,20}$")) {
                        if (nombre.matches("[a-zA-Z]+")) {
                            if (tipo.matches("^[AN]$")) {
                                if (penalizacion == null || penalizacion.isAfter(LocalDate.now())) {

                                    Usuario usuario = daoUsuario.getUsuarioById(id);
                                    if (usuario != null) {

                                        usuario.setDni(dni);
                                        usuario.setNombre(nombre);
                                        usuario.setEmail(email);
                                        usuario.setPassword(password);
                                        usuario.setTipo(tipo);

                                        if (penalizacion != null) {
                                            usuario.setPenalizacionHasta(penalizacion);
                                        }

                                        daoUsuario.updateUsuario(usuario);

                                        String json_responce = conversorjson.writeValueAsString(usuario);
                                        System.out.println(json_responce);
                                        impresora.println(json_responce);

                                    } else {

                                        String json_responce = conversorjson.writeValueAsString("No existe el usuario con ese dni");
                                        System.out.println(json_responce);
                                        impresora.println(json_responce);

                                    }

                                }
                            }
                        }
                    }
                }
            }

        } else {
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            String json_responce = conversorjson.writeValueAsString("No puedes dejar campos vacios");
            System.out.println(json_responce);
            impresora.println(json_responce);
        }
    }

        public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException {
        }

        public void destroy () {
        }

}