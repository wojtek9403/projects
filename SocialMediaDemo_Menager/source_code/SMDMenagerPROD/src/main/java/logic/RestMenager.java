package logic;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import soap.Authorities;
import soap.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ConnectException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Service
public class RestMenager {

    @Autowired
    Session session;

    public boolean conectionTest(String uri) {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET()
                .uri(URI.create(uri)).build();
        HttpResponse<String> response = null;

        try {
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            } catch (IOException | InterruptedException  e ) {
              e.printStackTrace();
            }

            if (response.statusCode() >= 200 && response.statusCode() <= 299) {
                return true;
            }


        return false;
    }


    public boolean login(String login, String password){

        String authStr = login+":"+password;

        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("Authorization", "Basic " + base64Creds)
                .uri(URI.create("http://localhost:8080/SocialMediaDemo/out")).build();

        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response.statusCode() >= 200 && response.statusCode() <= 299) {
            session.saveObject("login", login);
            session.saveObject("password", password);

             return true;
        }


        return false;
    }

    public List<Object> fetch_data(Content en){

        System.out.println("fetch_data: "+Thread.currentThread().getName());
        //wysyłka postem tego jsona
        String authStr = (String) session.getSavedObjects("login")+":"+(String) session.getSavedObjects("password"); // dostepy (najlepiej w pliku zewnetrzym przez env)
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes()); // kodujemy na base 64 jak do html to jest z util !

        System.out.println(authStr);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().GET()
                .header("Content-Type", "application/json") // nagłówków moze być wiele!!! tu sa wymagane 2 - format json i dostepy
                .header("Authorization", "Basic " + base64Creds)
                .header("Login", (String) session.getSavedObjects("login"))
                .header("WhatToFetch", en.toString())
                .uri(URI.create("http://localhost:8080/Dappi_Handler/1119a646-1ce0"))
                .build();

        //odbieramy odp
        HttpResponse<byte[]> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

            System.out.println(response.body().length);

            try (var baris = new ByteArrayInputStream(response.body())) {

                System.out.println("odebralem odp");

                System.out.println("odebralem odp0");

                var ois = new ObjectInputStream(baris);
                System.out.println("odebralem odp1");

                List<byte[]> contentx = (List<byte[]>) ois.readObject();

                System.out.println("odebralem odp2");

                Tika tika=new Tika();
                String whatty = tika.detect(contentx.get(0));
                System.out.println(whatty);

                List<Object> listOfBuffs = new ArrayList<>();

                if(whatty.endsWith("jpeg")) {
                    System.out.println("jpeg");

                    for (byte[] p : contentx) {
                        listOfBuffs.add(new Image(new ByteArrayInputStream(p)));
                    }

                    return listOfBuffs;

                    //zpaisz to do jakiegos beana ktory bedzie przechowywal i servowal te fotki
                }else if(whatty.endsWith("mp4")){

                    File userRepository = new File("userImg/");
                    if (!userRepository.exists()) {
                        userRepository.mkdir();
                    }

                    for(int i = 0; i<contentx.size();i++) {

                        File f = new File("userImg/temp_cont_file" + i + ".mp4");
                        f.createNewFile();

                        var xpq = new FileOutputStream(f);
                        xpq.write(contentx.get(i));
                        xpq.flush();
                        xpq.close();
                        ois.close();
                        System.out.println("mp4");

                        // i tez do tego menagera co by to servowal i przechowywal
                    }
                }


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }









}
