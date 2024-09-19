package classroom.notifier.entity;

import java.util.HashSet;
import java.util.Set;

public abstract class Observable {
    Set<Observer> observers;

    public Observable(){
        this.observers = new HashSet<Observer>();
    }

    public synchronized void addObserver(Observer o) {
        if (o == null)
            throw new NullPointerException();
        if (!observers.contains(o)) {
            observers.add(o);
        }
    }

    /**
     * Deletes an observer from the set of observers of this object.
     * Passing {@code null} to this method will have no effect.
     * @param   o   the observer to be deleted.
     */
    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    public synchronized  void notifyObservers(Object arg){
        Object[] arrLocal = observers.toArray();

        for (int i = arrLocal.length-1; i>=0; i--)
            ((Observer)arrLocal[i]).update(this, arg);
    }

}
