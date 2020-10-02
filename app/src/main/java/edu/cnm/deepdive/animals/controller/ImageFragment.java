package edu.cnm.deepdive.animals.controller;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.animals.R;
import edu.cnm.deepdive.animals.model.Animal;
import edu.cnm.deepdive.animals.model.Apikey;
import edu.cnm.deepdive.animals.service.AnimalService;
import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.util.List;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ImageFragment extends Fragment {

  private WebView contentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_image, container, false);
    setupWebView(root);
    return root;
  }

  private void setupWebView(View root) {
    contentView = root.findViewById(R.id.content_view);
    contentView.setWebViewClient(new WebViewClient() {
      @Override
      public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return false;
      }
    });
    WebSettings settings = contentView.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    new Retriever().start();
  }


  private class Retriever extends Thread {

    @Override
    public void run() {
      Gson gson = new GsonBuilder()

          .create();
      //

      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl("https://us-central1-apis-4674e.cloudfunctions.net/")
          .addConverterFactory(GsonConverterFactory.create(gson))
          .build();

      AnimalService animalService = retrofit.create(AnimalService.class);

      try {
        Response<Apikey> keyResponse = animalService.getApiKey().execute();
        Apikey key = keyResponse.body();
        assert key != null;
        final String clientKey = key.getKey();

        Response<List<Animal>> listResponse = animalService.getAnimals(clientKey).execute();
        List<Animal> animalList = listResponse.body();
        assert animalList != null;
        final String imageUrl = animalList.get(0).getImageUrl();

        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
           contentView.loadUrl(imageUrl);
          }
        });


      } catch (IOException e) {
        Log.e("AnimalsService", e.getMessage(), e);
      }


    }


  }

}