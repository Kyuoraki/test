/**
 * Created by Maxim on 15.03.2016.
 */
public interface Subject {
     void registerObserver(Observer o);
     void notifyObservers();
     void setCity(String city);

}
