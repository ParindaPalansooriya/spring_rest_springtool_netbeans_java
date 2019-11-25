/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package apiControler;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Parinda
 */
public class ApiController {
    
    static String mainDomain = "http://localhost:8080";
    static String mainDownloadDomain = "http://localhost/restSpringUploadFiles/";
    
    public JSONObject POSTRequest(String apiURL, JSONObject json) throws IOException, ParseException {
        
        final String POST_PARAMS = json.toJSONString();
        System.out.println(json);
        URL obj = new URL(mainDomain+apiURL);
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("POST");
        //postConnection.setRequestProperty("userId", "a1bcdefgh");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        try (OutputStream os = postConnection.getOutputStream()) {
            os.write(POST_PARAMS.getBytes());
            os.flush();
            int responseCode = postConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuffer response;
                try (BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in .readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                
                if(response.toString().isEmpty() || response.toString() == null){
                    JSONObject returnPOST = new JSONObject();
                    returnPOST.put("name", "Http OK");
                    returnPOST.put("description", responseCode);
                    return returnPOST;
                }else{
                    JSONParser parser = new JSONParser();
                    JSONObject returnPOST = new JSONObject();

                    String temp = response.toString();
                    if( temp.charAt(0) != '['){
                        returnPOST = (JSONObject) parser.parse(response.toString());
                    }else{
                        JSONArray tempOb = (JSONArray) parser.parse(response.toString());
                        returnPOST = (JSONObject) tempOb.get(0);
                    }
                    return returnPOST;
                }
            } else {
                //System.out.println("POST NOT WORKED");
                JSONObject returnPOST = new JSONObject();
                returnPOST.put("name", "Http ERROR");
                returnPOST.put("description",responseCode);
                return returnPOST;
            }
        }
        
    }
    
    public JSONObject POSTImageRequest(String apiURL, File file) throws IOException, ParseException {
        
        try {
            MultipartUtility multipart = new MultipartUtility(mainDomain+apiURL, "UTF-8");
            multipart.addFilePart("file", file);
            List<String> response = multipart.finish();
            if(response.isEmpty() || response.toString() == null){
                JSONObject returnPOSTIMAGE = new JSONObject();
                returnPOSTIMAGE.put("name", "Http OK");
                returnPOSTIMAGE.put("description", 200);
                return returnPOSTIMAGE;
            }else{
                JSONParser parser = new JSONParser();
                JSONObject returnPOSTIMAGE = new JSONObject();
                
                Object temp = response.toString();
                if( temp instanceof JSONObject){
                    returnPOSTIMAGE = (JSONObject) parser.parse(response.toString());
                }else{
                    JSONArray tempOb = (JSONArray) parser.parse(response.toString());
                    returnPOSTIMAGE = (JSONObject) tempOb.get(0);
                }
                
                //return (JSONObject) parser.parse(response.toString());
                return returnPOSTIMAGE;
            }
            
        } catch (IOException ex) {
            System.err.println(ex);
            JSONObject returnPOSTIMAGE = new JSONObject();
            returnPOSTIMAGE.put("name", "Http ERROR");
            returnPOSTIMAGE.put("description", 650);
            returnPOSTIMAGE.put("massage", ex.getMessage());
            return returnPOSTIMAGE;
        }
    }
    
    public JSONObject PUTRequest(String apiURL, JSONObject json) throws IOException, ParseException {
        
        final String POST_PARAMS = json.toJSONString();
        System.out.println(json);
        URL obj = new URL(mainDomain+apiURL);
        HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
        postConnection.setRequestMethod("PUT");
        //postConnection.setRequestProperty("userId", "a1bcdefgh");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);
        try (OutputStream os = postConnection.getOutputStream()) {
            os.write(POST_PARAMS.getBytes());
            os.flush();
            int responseCode = postConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                StringBuffer response;
                try (BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()))) {
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in .readLine()) != null) {
                        response.append(inputLine);
                    }
                }
                // print result
                //System.out.println(response.toString());
                if(response.toString().isEmpty() || response.toString() == null){
                    JSONObject returnPOST = new JSONObject();
                    returnPOST.put("name", "Http OK");
                    returnPOST.put("description",responseCode);
                    return returnPOST;
                }else{
                    
                    JSONParser parser = new JSONParser();
                    JSONObject returnPUT = new JSONObject();

                    String temp = response.toString();
                    if( temp.charAt(0) != '['){
                        returnPUT = (JSONObject) parser.parse(response.toString());
                    }else{
                        JSONArray tempOb = (JSONArray) parser.parse(response.toString());
                        returnPUT = (JSONObject) tempOb.get(0);
                    }
                    
                    return returnPUT;
                }
            } else {
                //System.out.println("POST NOT WORKED");
                JSONObject returnPOST = new JSONObject();
                returnPOST.put("name", "Http ERROR");
                returnPOST.put("description", responseCode);
                return returnPOST;
            }
        }
        
    }
    
    public JSONArray GETRequest(String apiURL) throws IOException, ParseException {
        
        URL urlForGetRequest = new URL(mainDomain+apiURL);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        conection.setRequestProperty("Content-Type", "application/json");
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuffer response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()))) {
                response = new StringBuffer();
                while ((readLine = in .readLine()) != null) {
                    response.append(readLine);
                }
            }
            
            // print result
            System.out.println("JSON String Result " + response.toString());
            JSONParser parser = new JSONParser();
            String temp = response.toString();
            JSONArray jsonArray = new JSONArray();
            if( temp.charAt(0) == '['){
                jsonArray = (JSONArray) parser.parse(response.toString());
            }else{
                JSONObject tempOb = (JSONObject) parser.parse(response.toString());
                jsonArray.add(tempOb);
            }
            
            return jsonArray;
        } else {
            System.out.println("GET NOT WORKED");
            JSONObject json = new JSONObject();
            json.put("name", "Http error");
            json.put("description",responseCode);
            JSONArray JsonArray = new JSONArray();
            JsonArray.add(json);
            return JsonArray;
        }
    }
    
    public JSONObject DELETERequest(String apiURL) throws IOException, ParseException {
        
        URL urlForGetRequest = new URL(mainDomain+apiURL);
        String readLine = null;
        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        //conection.setRequestProperty("userId", "a1bcdef"); // set userId its a sample here
        conection.setRequestProperty("Content-Type", "application/json");
        int responseCode = conection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            StringBuffer response;
            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(conection.getInputStream()))) {
                response = new StringBuffer();
                while ((readLine = in .readLine()) != null) {
                    response.append(readLine);
                }
            }
            if(response.toString().isEmpty() || response.toString() == null){
                JSONObject returnPOST = new JSONObject();
                returnPOST.put("name", "Http OK");
                returnPOST.put("description",responseCode);
                return returnPOST;
            }else{
                JSONParser parser = new JSONParser();
                JSONObject returnDELETE = new JSONObject();

                String temp = response.toString();
                if( temp.charAt(0) != '['){
                returnDELETE = (JSONObject) parser.parse(response.toString());
                }else{
                    JSONArray tempOb = (JSONArray) parser.parse(response.toString());
                    returnDELETE = (JSONObject) tempOb.get(0);
                }
                return returnDELETE;
            }
        } else {
            System.out.println("GET NOT WORKED");
            JSONObject json = new JSONObject();
            json.put("name", "Http error");
            json.put("description",responseCode);
            /*JSONArray JsonArray = new JSONArray();
            JsonArray.add(json);*/
            return json;
        }
    }
    
    public void downLoad(String id, String fileName) throws MalformedURLException, FileNotFoundException, IOException{
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            File folderDirect = new File("Downloads/SpringDownTest/"+id);
            if(!folderDirect.exists()) {
		folderDirect.mkdirs();
            }
            String originalName = fileName;
            String folderPath = "Downloads/SpringDownTest/"+id+"/";
            //boolean massage = false;
            while(true){
                File fileDirect = new File(folderPath+fileName);
                if(!fileDirect.exists()) {
                    break;
                }else{
                    fileName = "copy_"+fileName;
                }
            }
            
            in = new BufferedInputStream(new URL(mainDownloadDomain+id+"/"+originalName).openStream());
            fout = new FileOutputStream(folderPath+fileName);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
            Desktop.getDesktop().open(folderDirect);
        } finally {
            if (in != null)
                in.close();
            if (fout != null)
                fout.close();
        }
    }
}
