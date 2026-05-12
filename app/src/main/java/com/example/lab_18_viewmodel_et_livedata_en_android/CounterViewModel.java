package com.example.lab_18_viewmodel_et_livedata_en_android;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * CounterViewModel : Gère la logique métier et les données du compteur.
 * Pourquoi utiliser un ViewModel ?
 * 1. Survie à la rotation : Android détruit et recrée l'Activity lors d'un changement de configuration.
 *    Le ViewModel reste en mémoire, attaché au cycle de vie de l'application, pas de l'instance d'Activity.
 * 2. Séparation des préoccupations : L'Activity gère l'UI, le ViewModel gère les données.
 */
public class CounterViewModel extends ViewModel {

    // MutableLiveData : Version modifiable de LiveData. 
    // On la garde privée pour que l'Activity ne puisse pas la modifier directement (encapsulation).
    private final MutableLiveData<Integer> countLiveData = new MutableLiveData<>();

    public CounterViewModel() {
        // setValue() est utilisé pour définir une valeur depuis le thread principal (Main Thread).
        // postValue() serait utilisé si on était dans un thread secondaire.
        countLiveData.setValue(0);
    }

    // Méthode pour incrémenter
    public void increment() {
        Integer current = countLiveData.getValue();
        if (current != null) {
            countLiveData.setValue(current + 1);
        }
    }

    // Méthode pour décrémenter
    public void decrement() {
        Integer current = countLiveData.getValue();
        if (current != null) {
            countLiveData.setValue(current - 1);
        }
    }

    // Méthode pour réinitialiser
    public void reset() {
        countLiveData.setValue(0);
    }

    /**
     * Getter exposé à l'Activity.
     * On retourne un LiveData (lecture seule) au lieu d'un MutableLiveData.
     * C'est une bonne pratique pour empêcher l'Activity de modifier les données par erreur.
     */
    public LiveData<Integer> getCount() {
        return countLiveData;
    }
}
