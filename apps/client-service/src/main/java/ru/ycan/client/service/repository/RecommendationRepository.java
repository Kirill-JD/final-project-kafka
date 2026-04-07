package ru.ycan.client.service.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import ru.ycan.client.service.model.Recommendation;

import java.util.Optional;

@Repository
public interface RecommendationRepository extends ElasticsearchRepository<Recommendation, String> {
    Optional<Recommendation> findByUserId(String userId);
}
