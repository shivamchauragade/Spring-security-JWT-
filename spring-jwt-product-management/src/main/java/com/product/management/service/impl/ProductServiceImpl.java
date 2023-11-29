package com.product.management.service.impl;

import com.product.management.entity.Product;
import com.product.management.payload.ProductRequest;
import com.product.management.repository.ProductRepository;
import com.product.management.service.ProductService;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void addProducts(ProductRequest productRequest) {
        String tableName = productRequest.getTable();
        List<Product> products = productRequest.getRecords();
        // Dynamically create table
        createTableIfNotExists(tableName);

        for (Product product : products) {
            // Save  product
            productRepository.save(product);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    private void createTableIfNotExists(String tableName) {
        // Check if the table exists
        if (!isTableExists(tableName)) {
            // Create the table dynamically based on Product entity
            StringBuilder query = new StringBuilder("CREATE TABLE " + tableName + " (");

            // Add an auto-incrementing id field as primary key
            query.append("id BIGINT AUTO_INCREMENT PRIMARY KEY, ");

            // Iterate over the fields of the Product entity and add them to the query
            Field[] fields = Product.class.getDeclaredFields();
            for (Field field : fields) {
                // Exclude the 'id' field from the dynamic column list
                if (!field.getName().equals("id")) {
                    String fieldName = field.getName();
                    // Use the @Column annotation to get the correct column name if present
                    String columnName = fieldName; // Default to field name if @Column is not present
                    if (field.isAnnotationPresent(Column.class)) {
                        columnName = field.getAnnotation(Column.class).name();
                    }
                    String dataType = getDataType(field.getType());

                    query.append(columnName).append(" ").append(dataType).append(", ");
                }
            }

            // Remove the trailing comma and add the closing parenthesis
            query.setLength(query.length() - 2); // Remove the last ", "
            query.append(")");

            // Execute the query to create the table
            entityManager.createNativeQuery(query.toString()).executeUpdate();
        }
    }



    private boolean isTableExists(String tableName) {
        List<?> result = entityManager.createNativeQuery(
                "SHOW TABLES LIKE :tableName"
        ).setParameter("tableName", tableName).getResultList();

        return !result.isEmpty();
    }

    private String getDataType(Class<?> fieldType) {
        // Map Java types to MySQL data types (you can extend this mapping as needed)
        if (fieldType == String.class) {
            return "VARCHAR(255)";
        } else if (fieldType == LocalDate.class) {
            return "DATE";
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return "INT";
        }

        // Default to VARCHAR(255) if the type is not recognized
        return "VARCHAR(255)";
    }
}
