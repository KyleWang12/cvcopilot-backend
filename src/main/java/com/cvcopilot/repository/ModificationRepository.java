package com.cvcopilot.repository;

import com.cvcopilot.models.Modification;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModificationRepository extends JpaRepository<Modification, String> {
  Optional<Modification> findByModificationId(String modificationId);

  List<Modification> findByUserId(Long userId);

  Boolean existsByModificationId(String modificationId);
}
