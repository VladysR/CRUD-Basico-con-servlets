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

@WebServlet(name = "delPrestamoServlet", value = "/delPrestamo-servlet")
public class delPrestamo_Servlet extends HttpServlet {

    DAOPrestamo prestamodao;

    public void init() { prestamodao = new DAOPrestamo(); }


    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        if (!request.getParameter("id").isEmpty()) {

            int id = Integer.parseInt(request.getParameter("id"));

            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            Prestamo prestamo = (Prestamo) prestamodao.getById(id);
            if (prestamo != null) {
                String json = conversorjson.writeValueAsString(prestamo);
                prestamodao.deleteUsuario(prestamo);
                impresora.println(json);
            } else {
                impresora.println("Este prestamo no existe");
            }
        } else {
            PrintWriter impresora = response.getWriter();
            ObjectMapper conversorjson = new ObjectMapper();
            conversorjson.registerModule(new JavaTimeModule());

            impresora.println("No puedes dejar campos vacios");
        }


    }


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    public void destroy() {

    }
}