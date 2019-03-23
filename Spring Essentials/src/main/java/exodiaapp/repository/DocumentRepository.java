package exodiaapp.repository;

import exodiaapp.domain.entities.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, String> {
Optional<Document> findByIdIsLike(String documentId);
}
