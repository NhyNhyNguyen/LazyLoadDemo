package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.example.entity.Product;
import com.example.entity.Variant;

public class LazyLoadingDemo {

    public static SessionFactory sessionFactory = getSessionFactory();

    public static void main(String[] args) {
        int productId = saveProduct();
        try {
            caseLazyLoadError(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("=============== Case lazy load not error with enable_lazy_load_no_trans is true or false ===============");
        caseLazyLoadNotError(productId);
    }

    /**
     * Case lazy load, enable_lazy_load_no_trans = false
     *
     */
    public static void caseLazyLoadError(int productId) {
        Session session = sessionFactory.openSession();
        Product prod = session.find(Product.class, productId);
        System.out.println("====== Error when get variant, but if use debug more it will load in debug time -> not exception =====");
        session.close();

        System.out.println(prod.getVariant());
    }

    /**
     * Case lazy load, enable_lazy_load_no_trans = false
     **/
    public static void caseLazyLoadNotError(Integer productId) {
        Session session = sessionFactory.openSession();
        Product prod = session.find(Product.class, productId);

        System.out.println("====== No error when get variant =====");
        for (Object obj : prod.getVariant()) {
            Variant v = (Variant) obj;
            System.out.println("Variant: " + v);
        }
        session.close();
    }

    public static int saveProduct() {
        Product product = new Product();
        product.setpName("Dell");
        Variant variant = new Variant();
        variant.setPrice(200);
        variant.setvName("Dell-PRO");

        product.getVariant().add(variant);

        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();
        session.save(product);
        transaction.commit();
        session.close();

        return product.getpId();
    }

    public static SessionFactory getSessionFactory() {
        Configuration config = new Configuration().configure();
        config.addAnnotatedClass(Product.class);
        config.addAnnotatedClass(Variant.class);

        SessionFactory sf = config.buildSessionFactory();
        return sf;
    }

}
