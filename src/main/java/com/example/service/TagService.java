package com.example.service;

import com.example.config.HibernateUtil;
import com.example.dao.SpaceDao;
import com.example.dao.TagDao;
import com.example.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.dao.hibernateimpl.TagDaoHibernate;
import com.example.domain.Space;
import com.example.domain.Tag;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.w3c.dom.css.CSS2Properties;

import java.util.ArrayList;
import java.util.List;

public class TagService {
    private final SessionFactory sf;
    private final TagDao tagDao;
    private final SpaceDao spaceDao;

    public TagService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.tagDao = new TagDaoHibernate();
        this.spaceDao = new SpaceDaoHibernate();
    }

    public Long save(Tag tag){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            tagDao.save(s, tag);
            tx.commit();
            return tag.getId();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public Tag findById(Long id){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Tag tag = tagDao.findById(s, id);
            if (tag == null){
                throw new IllegalArgumentException("El tag con id " + id + " no existe");
            }
            tx.commit();
            return tag;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void update(Tag tag) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            tagDao.update(s, tag);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void delete(Tag tag) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            tagDao.delete(s, tag);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void deleteById(Long id) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            tagDao.deleteById(s, id);
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public List<Tag> findAll() {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Tag> tags = tagDao.findAll(s);
            tx.commit();
            return tags;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void addTagToSpace(Long spaceId, Long tagId){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Space space = spaceDao.findById(s, spaceId);
            Tag tag = tagDao.findById(s, tagId);

            if (space == null){
                throw new IllegalArgumentException("El space con id " + spaceId + " no existe");
            }

            if (tag == null){
                throw new IllegalArgumentException("El tag con id " + tagId + " no existe");
            }

            if (space.getTags() == null){
                space.setTags(new ArrayList<>());
            }

            if (!space.getTags().contains(tag)){
                space.getTags().add(tag);
            }

            spaceDao.update(s, space);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    public void removeTagFromSpace(Long spaceId, Long tagId) {
        Transaction tx = null;
        try {
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            Space space = spaceDao.findById(s, spaceId);
            Tag tag = tagDao.findById(s, tagId);

            if (space == null) {
                throw new IllegalArgumentException("Space con ID " + spaceId + " no existe.");
            }
            if (tag == null) {
                throw new IllegalArgumentException("Tag con ID " + tagId + " no existe.");
            }

            if (space.getTags() != null && space.getTags().contains(tag)) {
                space.getTags().remove(tag);
            }

            spaceDao.update(s, space);

            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

}
