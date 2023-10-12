package com.example;

import com.example.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LazyLoadingDemo2 {

    public static SessionFactory sessionFactory = getSessionFactory();

    public static void main(String[] args) {
        Address address = saveAddress();
        try {
            caseLazyLoadError(address.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=============== Case lazy load not error with enable_lazy_load_no_trans is true or false ===============");
        caseLazyLoadNotError(address.getId());
    }

    /**
     * Case lazy load, enable_lazy_load_no_trans = false
     *
     */
    public static void caseLazyLoadError(long addressId) {
        Session session = sessionFactory.openSession();
        Address prod = session.find(Address.class,  addressId);
        System.out.println("====== Error when get Address, but if use debug more it will load in debug time -> not exception =====");
        session.close();

        List<Person> person = prod.getPersons();
        System.out.println("==============");
        System.out.println(person.get(0).getHobbies());
    }

    /**
     * Case lazy load, enable_lazy_load_no_trans = false
     **/
    public static void caseLazyLoadNotError(long addressId) {
        Session session = sessionFactory.openSession();
        Address prod = session.find(Address.class,  addressId);

        System.out.println("====== No error when get variant =====");
        for (Object obj : prod.getPersons()) {
            Person v = (Person) obj;
        }
        session.close();
    }

    public static Address saveAddress() {
        Address address = new Address();
        address.setCity("Ha Noi");

        Person person = new Person();
        person.setName("name");
        person.setAddress(address);
        address.setPersons(new ArrayList<>(Arrays.asList(person)));

        Hobby hobby = new Hobby();
        hobby.setName("Listen to music");
        person.setHobbies(new ArrayList<>(Arrays.asList(hobby)));
        hobby.setPerson(person);
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        session.save(address);
        transaction.commit();
        session.close();

        return address;
    }

    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Address.class);
        config.addAnnotatedClass(Person.class);
        config.addAnnotatedClass(Hobby.class);

        SessionFactory sf = config.buildSessionFactory();
        return sf;
    }

}
