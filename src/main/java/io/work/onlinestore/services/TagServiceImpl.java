package io.work.onlinestore.services;

import io.work.onlinestore.data.model.Tag;
import io.work.onlinestore.data.repository.TagRepository;
import io.work.onlinestore.services.interfaces.TagService;
import io.work.onlinestore.util.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    private final static Logger logger = Logger.getLogger(StorageServiceImpl.class.getSimpleName());

    @Override
    public List<Tag> getAllTags() throws ServiceException {
        try {
            List<Tag> tagList = new ArrayList<>();
            tagRepository.findAll().forEach(tagList::add);
            return tagList;
        } catch (Exception e) {
            logger.info("Can't getAllTags" + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't get all tags " + e.getMessage());
        }
    }

    @Override
    public boolean create(Tag tag) throws ServiceException {
        try {
            tagRepository.save(tag);
            return true;
        } catch (Exception e) {
            logger.info("Can't create tag " + tag.toString() + " @ " + new Date(System.currentTimeMillis()));
            throw new ServiceException("Can't getByProductId all products " + e.getMessage());
        }
    }
}
