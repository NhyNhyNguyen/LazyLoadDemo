package com.example;

import com.example.entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class BatchInsertDemo {
    public static void main(String[] args) {
        saveProduct();
    }

    public static void saveProduct() {
        // Create a list of products to update
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Product product = new Product();
            product.setpName("Updated Product " + i);
            products.add(product);
        }

        // Open a session
        Session session = LazyLoadingDemo.getSessionFactory().openSession();

        // Start a transaction
        Transaction transaction = session.beginTransaction();

        // Iterate over the products and update them in a batch
        for (int i = 0; i < 20; i++) {
            session.persist(products.get(i));
            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
        }

        // Commit the transaction
        transaction.commit();

        // Close the session
        session.close();
    }
}
