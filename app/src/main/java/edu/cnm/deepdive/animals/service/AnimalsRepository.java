package edu.cnm.deepdive.animals.service;

import android.content.Context;
import edu.cnm.deepdive.animals.model.Animal;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.security.Key;
import java.util.List;

public class AnimalsRepository {
  
  private final Context context;
  
  private final AnimalService animalService;

  public AnimalsRepository(Context context) {
    this.context = context;
    this.animalService = AnimalService.getInstance();
  }
  public Single<List<Animal>>loadAnimals(){


    return animalService.getApiKey()
        .flatMap((Key)-> animalService.getAnimals(Key.getKey()))
        .subscribeOn(Schedulers.io());

  }

}
