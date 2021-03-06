package com.alexkbit.iblog.repository.impl.jpa;


import com.alexkbit.iblog.repository.impl.entities.TechnologyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Spring data repository for {@link TechnologyEntity}
 */
public interface TechnologyRepositoryJpa extends JpaRepository<TechnologyEntity, String>, JpaSpecificationExecutor {

}
