package org.ebuy.repository;

import org.ebuy.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Burak Köken on 2.5.2020.
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
