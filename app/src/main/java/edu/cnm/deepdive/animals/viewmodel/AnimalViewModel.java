package edu.cnm.deepdive.animals.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle.Event;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.model.Apikey;
import edu.cnm.deepdive.animals.service.AnimalService;
import edu.cnm.deepdive.animals.service.AnimalsRepository;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalViewModel extends AndroidViewModel {

  private final MutableLiveData<List<Animal>> animals;
  private final MutableLiveData<Throwable> throwable;
  private final MutableLiveData<Integer> selectedItem;
  private final AnimalsRepository animalsRepository;
  private final CompositeDisposable pending;

  public AnimalViewModel(
      @NonNull Application application) {
    super(application);
    animals = new MutableLiveData<>();
    selectedItem = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    //animalService = AnimalService.getInstance();
    animalsRepository = new AnimalsRepository(application);
    pending = new CompositeDisposable();
    loadAnimals();
  }

  public LiveData<List<Animal>> getAnimals() {
    return animals;
  }

  public LiveData<Integer> getSelectedItem() {
    return selectedItem;
  }
  public void select(int index){
    selectedItem.setValue(index);
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  @SuppressLint("CheckResult")
  private void loadAnimals() {
    pending.add(
      animalsRepository.loadAnimals()
        .subscribe(
            animals::postValue,
            throwable::postValue
        )
        );
  }
  @OnLifecycleEvent(Event.ON_STOP)
  private void clearPending(){
    pending.clear();
  }
}