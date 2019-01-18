package io.work.onlinestore.data.repository;

import io.work.onlinestore.data.model.Tag;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TagRepository extends CrudRepository<Tag, Integer> {
    Tag findByTagNameAndValue(String tagName, String value);
    List<Tag> findAllByTagName(String tagName);
    List<Tag> findAllByTagId(Integer tagId);
    List<Tag> findAllByValue(String value);
}
