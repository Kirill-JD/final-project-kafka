package ru.ycan.client.service.repository;

import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.ycan.client.service.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends ElasticsearchRepository<Product, String> {

    @Query("""
           {
             "match": {
               "name": "?0"
             }
           }
           """)
    List<Product> searchByName(String name);
}
