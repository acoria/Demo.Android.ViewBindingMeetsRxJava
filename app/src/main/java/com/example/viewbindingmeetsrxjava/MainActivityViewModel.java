package com.example.viewbindingmeetsrxjava;

import android.databinding.ObservableField;
import android.util.Log;

import com.example.viewbindingmeetsrxjava.pojo.Person;
import com.example.viewbindingmeetsrxjava.view_binding.FieldUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.BehaviorSubject;

public class MainActivityViewModel {

    private static final String TAG = MainActivityViewModel.class.getSimpleName();
    private final Observable<String> nameInputObservable;
    public ObservableField<String> name;
    public ObservableField<String> age;
    public ObservableField<String> nameInput = new ObservableField<>();
    private BehaviorSubject<Person> person = BehaviorSubject.create();
    private CompositeDisposable subscriptions = new CompositeDisposable();

    public MainActivityViewModel(Observable<Person> personStream, Observable<Object> buttonClick){

        subscriptions.add(personStream
                .subscribe(person::onNext));

        subscriptions.add(buttonClick
                .subscribe(click -> {
                    //theoretically update the db
                    Log.e(TAG, "click");
                    Person oldPerson = person.getValue();
                    Person newPerson = oldPerson.setAge(oldPerson.getAge()+1);
                    person.onNext(newPerson);
                }));

        name = FieldUtils.toField(person
            .map(Person::getName));

        age = FieldUtils.toField(person
            .map(person -> Integer.toString(person.getAge())));

        nameInputObservable = FieldUtils.toObservable(nameInput);
        subscriptions.add(nameInputObservable
                .subscribe(
                        newName -> {
                            Log.e(TAG, newName);
                            Person newPerson = person.getValue().setName(newName);
                            person.onNext(newPerson);
                        }));
    }



}
