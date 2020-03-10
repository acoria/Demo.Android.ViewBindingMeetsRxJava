package com.example.viewbindingmeetsrxjava;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.viewbindingmeetsrxjava.databinding.ActivityMainBinding;
import com.example.viewbindingmeetsrxjava.pojo.Person;
import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.subjects.BehaviorSubject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;
    private BehaviorSubject<Person> personStream;
    private Person jack = new Person("Jack", 27);
    private Person john = new Person("John", 36);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        super.onCreate(savedInstanceState);

        personStream = BehaviorSubject.createDefault(jack);

        viewModel = new MainActivityViewModel(personStream, RxView.clicks(findViewById(R.id.btn_age_up)));
        binding.setViewModel(viewModel);

        RxView.clicks(findViewById(R.id.btn_new_person))
                .subscribe(click -> {
                    if(personStream.getValue() == john) {
                        personStream.onNext(jack);
                    }else{
                        personStream.onNext(john);
                    }});
    }
}
