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
import java.io.* ; 
import java.util.Scanner;
import javax.servlet.RequestDispatcher;

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

//        // sending the string to be returned 
//        PrintWriter out = response.getWriter(); 
//        // to send the json string to the android application.
//        out.println(json);
          
          Process p = Runtime.getRuntime().exec("perl C:\\Users\\sushm\\Documents\\Sushmita\\Girihlet_Internship\\BarcodeCaddyUIMaven\\src\\main\\java\\com\\sample\\barcodecaddyuimaven\\barcode_diversity\\barcode_json.pl '"+json.toString()+"'");
          BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
          BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
          String s = null ;
          while((s=stdError.readLine())!=null)
          {
              System.out.println(s);
          }
          
          StringBuilder jsonPerlOut = new StringBuilder();
          while((s=stdInput.readLine())!=null)
          {
              jsonPerlOut.append(s);
          }
          String jsonOut = jsonPerlOut.toString();
          
          JSONObject jsonOutObj = null;
          
          if(jsonOut.contains("ERR")){
              request.setAttribute("ERR", jsonOut);
          }
          else{
              jsonOutObj = new JSONObject("{\"csv\":\"tmp/bc9025.csv\",\"html\":\"bc9025.html\",\"dist\":\"tmp/mat9025.html\",\"fig_lnk\":\"tmp/second0.gif\",\"fig\":\"tmp/bc9025.png\"}\n" +
"\n" +
"");
              request.setAttribute("fig_link", jsonOutObj.get("fig_lnk"));
              request.setAttribute("csv", jsonOutObj.get("csv"));
              request.setAttribute("dist", jsonOutObj.get("dist"));
              StringBuilder sbtable = new StringBuilder();
              try{
                  Scanner sc = new Scanner(new File(jsonOutObj.get("html").toString()));
                  
                  while(sc.hasNextLine()){
                  sbtable.append(sc.nextLine());
                  }
              }
              catch(FileNotFoundException f){
                  f.printStackTrace();
              }
              request.setAttribute("table_html", sbtable.toString());
          }  
          RequestDispatcher view = request.getRequestDispatcher("ui_design.jsp");
          view.forward(request, response);
    }
}
