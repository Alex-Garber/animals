package edu.cnm.deepdive.animals.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animals.BuildConfig;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.model.Apikey;
import edu.cnm.deepdive.animals.service.AnimalService;
import io.reactivex.schedulers.Schedulers;
import java.io.IOException;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimalViewModel extends AndroidViewModel {

  private final MutableLiveData<List<Animal>> animals;
  private final MutableLiveData<Throwable> throwable;
  private final AnimalService animalService;

  public AnimalViewModel(
      @NonNull Application application) {
    super(application);
    animals = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    animalService = AnimalService.getInstance();
    loadAnimals();
  }

  public LiveData<List<Animal>> getAnimals() {
    return animals;
  }

  public MutableLiveData<Throwable> getThrowable() {
    return throwable;
  }

  @SuppressLint("StaticFieldLeak")
  private void loadAnimals() {

    animalService.getApiKey()
        .subscribeOn(Schedulers.io())
        .flatMap((key) -> animalService.getAnimals(key.getKey()))
        .subscribe(
            animals::postValue,
            throwable::postValue
        );
  }
}