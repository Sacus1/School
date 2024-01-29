package org.qualitedev.tp2qualitedev;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UtilisateurRepository extends PagingAndSortingRepository<Utilisateur, Long>, CrudRepository<Utilisateur,Long> {

}
