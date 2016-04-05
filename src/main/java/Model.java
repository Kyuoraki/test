/**
 * Created by Maxim on 05.04.2016.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Model extends Thread implements Subject {
    JSONObject json;
    private ArrayList observers;
    private int temperature;
    private int humidity;
    private int pressure;
    int count;

    public void setCity(String city) {
        this.city = city;

    }

    String city="Orenburg";
    Thread t;
    Model(){
        observers = new ArrayList();
        t=new Thread(this);
        t.start();
    }

    private  String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public  JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public void run()  {
        do {
            try {
                Thread.sleep(1000);
                count++;
                json = readJsonFromUrl("http://api.openweathermap.org/data/2.5/weather?q="+city+",ru&appid=ee3e015a94367e89716745224d46d87f");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            JSONObject json2 = (JSONObject) json.get("main");
            temperature =  json2.getInt("temp") - 273;
            humidity =  json2.getInt("humidity");
            pressure =  json2.getInt("pressure");
            notifyObservers();
        }while (true);


    }


    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void notifyObservers() {
        for (int i = 0; i < observers.size(); i++) {
            Observer observer = (Observer)observers.get(i);
            observer.update(temperature, humidity, pressure);
        }

    }
}