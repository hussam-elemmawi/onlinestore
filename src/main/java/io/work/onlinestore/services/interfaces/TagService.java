package io.work.onlinestore.services.interfaces;

import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.util.exception.ServiceException;

import java.util.List;

public interface TagService {
    List<Tag> getAllTags() throws ServiceException;
    boolean create(Tag tag) throws ServiceException;
}
