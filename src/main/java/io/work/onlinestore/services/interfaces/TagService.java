package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.util.exception.ServiceException;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags() throws ServiceException;
    List<Tag> getAllTagsByTagName(String tagName) throws ServiceException;
    List<Tag> getAllTagsByTagId(Integer tagId) throws ServiceException;
    List<Tag> getAllTagsByValue(String value) throws ServiceException;
    void create(Tag tag) throws ServiceException;
}
