/**
 * Created by Maxim on 05.04.2016.
 */
import org.swixml.SwingEngine;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Presenter implements Observer, Display {
    private int temperature;
    private int humidity;
    private int pressure;
    private Subject subject;
    public JButton quit;
    public JButton update;
    public JLabel temp;
    public JLabel hum;
    public JLabel pres;
    public JTextField city;

    public Presenter(final Subject subject) throws Exception {
        new SwingEngine(this).render("HelloWorld.xml").setVisible(true);
        quit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        update.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                subject.setCity(city.getText());
            }
        });
        this.subject=subject;
        subject.registerObserver(this);
    }



    public void display() {
        temp.setText(String.valueOf(temperature));
        hum.setText(String.valueOf(humidity));
        pres.setText(String.valueOf(pressure));

    }

    public void update(int temperature, int humidity, int pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.pressure=pressure;
        display();

    }
}