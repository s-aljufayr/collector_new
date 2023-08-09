package tech.wakeb.collector.controller.api;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "api/jsontest")
public class JsonController {

    public static String getJSONFromFile(String filename) {
        String jsonText = "";
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new FileReader(filename));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText += line + "\n";
            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonText;
    }

    public static String fileWriteer(String filePath, String filename, String jsonFile){
        try {
//
            String jsonBody = getJSONFromFile(jsonFile);
            FileWriter fileWriter = new FileWriter(filePath+"/"+filename);
            fileWriter.write(jsonBody);
//
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static String getJSONFromURL(String strUrl) {
        String jsonText = "";

        try {
            URL url = new URL(strUrl);
            InputStream is = url.openStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                jsonText += line + "\n";
            }

            is.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return jsonText;
    }

    @RequestMapping("getJsonBody")
//    @RequestParam String jsonFilePath
    public static Map<String, Object> getJsonBody(@RequestParam String jsonFilePath){

        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            User user = objectMapper.readValue(new File(jsonFilePath), User.class);

//            String strJson = getJSONFromURL(jsonFilePath);
            String strJson = getJSONFromFile(jsonFilePath);
//            String strJson = getJSONFromURL("https://raw.githubusercontent.com/BoostMyTool/JsonFile/master/example.json");
//            String strJson = getJSONFromFile("C://Users//ROG STRIX//Downloads//sample2.json");

            JSONParser parser = new JSONParser();
            Object object = parser.parse(strJson);
            JSONObject mainJsonObject = (JSONObject) object;
//            ArrayList arrayList = new ArrayList();
            Map<String, Object> responce = new HashMap<>();

            /*************** First Name ****************/
            String firstName = (String) mainJsonObject.get("firstName");
            responce.put("firstName",firstName);
//            arrayList.add(firstName);
//            System.out.println("First Name : " + firstName);

            /*************** Last Name ****************/
            String lastName = (String) mainJsonObject.get("lastName");
            responce.put("lastName",lastName);
//            arrayList.add(lastName);
//            System.out.println("Last Name : " + lastName);

            /*************** gender ****************/
            String gender = (String) mainJsonObject.get("gender");
            responce.put("gender",gender);
//            arrayList.add(gender);
//            System.out.println("gender : " + gender);

            /*************** Age ****************/
            Integer age = (Integer) mainJsonObject.get("age");
            responce.put("age",age);
//            arrayList.add(age);
//            System.out.println("Age : " + age);


            /*************** Address ****************/
            JSONObject jsonObjectAddress = (JSONObject) mainJsonObject.get("address");
            responce.put("address",jsonObjectAddress);
//            arrayList.add(jsonObjectAddress);
//            System.out.println("Address : ");

            String streetAddress = (String) jsonObjectAddress.get("streetAddress");
//            System.out.println("      Street Address : " + streetAddress);

            String city = (String) jsonObjectAddress.get("city");
//            System.out.println("      City : " + city);

            String state = (String) jsonObjectAddress.get("state");
//            System.out.println("      State : " + state);

//            Integer postalCode = (Integer) jsonObjectAddress.get("postalCode");
//            System.out.println("      Postal Code : " + postalCode);



            /*************** Phone Numbers ****************/
            JSONArray jsonArrayPhoneNumbers = (JSONArray) mainJsonObject.get("phoneNumbers");
            responce.put("phoneNumbers", jsonArrayPhoneNumbers);
//            arrayList.add(jsonArrayPhoneNumbers);
//            System.out.println("Phone Numbers : ");

            for (int i = 0; i < jsonArrayPhoneNumbers.size(); i++) {

                JSONObject jsonPhoneNumber = (JSONObject) jsonArrayPhoneNumbers.get(i);
//                System.out.println("      Phone Number " + (i));

                String type = (String) jsonPhoneNumber.get("type");
//                System.out.println("      Type : " + type);

                String number = (String) jsonPhoneNumber.get("number");
//                System.out.println("      Number : " + number);
            }
//
            return responce;

        }
        catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
