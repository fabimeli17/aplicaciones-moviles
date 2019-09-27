package com.example.demo.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@RestController
public class Servicios {
	@RequestMapping(value="/prueba", method=RequestMethod.GET)
    public String getPrueba(){
		System.out.println("Prueba");
        return "Prueba";
    }
	
	@RequestMapping(value="/grabarFoto", method=RequestMethod.POST)
    public String grabarFoto(@RequestParam(name = "foto") String foto){
		System.out.println(foto);
        return foto;
    }
	
	 
	@RequestMapping(value="/listarImagen", method=RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public String listarImagen(@RequestParam(name = "usuario") String usuario) throws IOException{

		String html = "";
		try {

		    html = html + "<title>ExpertTire</title> <body bgcolor=#FFFFFF><table>";
			
			URL url = new URL("http://experttire.atwebpages.com/fotoPedidoService.php/listar/"+usuario);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			String outputF = "";
			while ((output = br.readLine()) != null) {
				outputF = outputF + output;
			}
			outputF = outputF.substring(outputF.indexOf("["));
			Gson gson = new Gson();
            Type stringStringMap = new TypeToken<ArrayList<Map<String, String>>>() { }.getType();

            final ArrayList<Map<String, Object>> retorno = gson.fromJson(outputF, stringStringMap);
            for (Map<String, Object> x : retorno) {
            	String foto = (String) x.get("foto");
            	byte[] array = Base64.getDecoder().decode(foto);
            	
            	ByteArrayOutputStream baos = new ByteArrayOutputStream(array.length);
            	baos.write(array, 0, array.length);
            	html = html + "<TR><TD><img src='data:image/png;base64," + DatatypeConverter.printBase64Binary(array) + "'></TR></TD>";

            }

    		System.out.println("Fin");
    		html = html + "</table></body>";
			

			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }

		


	   
        return html;
    }


}
