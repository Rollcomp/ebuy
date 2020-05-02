package org.ebuy.repository;

import org.ebuy.model.Attribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Burak Köken on 3.5.2020.
 */
@Repository
public interface AttributeRepository extends JpaRepository<Attribute, Long> {

}
