package org.ebuy.repository;

import org.ebuy.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Burak Köken on 2.5.2020.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select distinct c from Category c left join fetch c.subCategories order by c.name asc")
    List<Category> findAllCategories();

    @Query("select distinct c from Category c left join fetch c.subCategories where c.id = ?1")
    Optional<Category> findCategoryById(long id);

    Optional<Category> findByBeautifiedName(String beautifiedName);

}
