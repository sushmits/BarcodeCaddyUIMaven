/* 
 * @author Sushmita subramani
 *
 * This is a short example of MVC.
 * The welcome-file in the deployment descriptor (web.xml) points
 * to this servlet.  So it is also the starting place for the web
 * application.
 *
 * The servlet is acting as the controller.
 * There are two views - prompt.jsp and result.jsp.
 * It decides between the two by determining if there is a search parameter or
 * not. If there is no parameter, then it uses the prompt.jsp view, as a 
 * starting place. If there is a search parameter, then it searches for a 
 * picture and uses the result.jsp view.
 * The model is provided by BarcodeCaddyModel.
 */
package com.sample.barcodecaddyuimaven;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "BarcodeCaddyServlet",
        urlPatterns = {"/BarcodeCaddyServlet"})
public class BarcodeCaddyServlet extends HttpServlet {
    // This servlet will reply to HTTP GET requests via this doGet method
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        // get the search parameter if it exists
        StringBuilder sb = new StringBuilder();
        String search;
        for (int i = 1; i < 11; i++) {
            search = request.getParameter("sliderAmount" + i);
            sb.append(search).append(";");
        }
        search = request.getParameter("Preselectedbarcodes");
        sb.append(search).append(";");
        
        String eachSetValue[] = sb.toString().split(";");
      
        JSONObject json = new JSONObject();
        json.put("Truseq", eachSetValue[0]);
        json.put("Nextera", eachSetValue[1]);
        json.put("NEB", eachSetValue[2]);
        json.put("Bioo-6mer", eachSetValue[3]);
        json.put("Bioo-8mer", eachSetValue[4]);
        json.put("HumanTCRa", eachSetValue[5]);
        json.put("HumanTCRb", eachSetValue[6]);
        json.put("MouseTCRa", eachSetValue[7]);
        json.put("MouseTCRb", eachSetValue[8]);
        json.put("Amaryllis", eachSetValue[9]);
        json.put("barcodes", eachSetValue[10]);
        
        // Things went well so set the HTTP response code to 200 OK
        response.setStatus(200);
        // tell the client the type of the response
        response.setContentType("text/plain;charset=UTF-8");

        // sending the string to be returned 
        PrintWriter out = response.getWriter(); 
        // to send the json string to the android application.
        out.println(json);
    }
}
