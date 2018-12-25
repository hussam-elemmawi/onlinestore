package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Tag;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByNameAndValue(String tagName, String tagValue);
}
