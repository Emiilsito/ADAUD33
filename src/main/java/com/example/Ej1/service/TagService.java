package com.example.Ej1.service;

import com.example.Ej1.config.HibernateUtil;
import com.example.Ej1.dao.SpaceDao;
import com.example.Ej1.dao.TagDao;
import com.example.Ej1.dao.hibernateimpl.SpaceDaoHibernate;
import com.example.Ej1.dao.hibernateimpl.TagDaoHibernate;
import com.example.Ej1.domain.Space;
import com.example.Ej1.domain.Tag;
import com.example.Ej1.dto.CityTagCountDto;
import com.example.Ej1.dto.MostUsedTagDto;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/*
HQL/JPQL --> orientado a objetos
    NamedQuery --> entidad
    -querys: space.name

SQL
Nativas --> service, dao createNativeQuery

Criteria

3 formas de devolver informacion de varias tablas:
- Object[]
- Tuple
- Dto *Recomendada* record

Metodos para recoger los resultados:
- getResultList()
- getSingleResult()
- getSingleResultOrNull()
 */

public class TagService {
    private final SessionFactory sf;
    private final TagDao tagDao;
    private final SpaceDao spaceDao;

    public TagService(){
        this.sf = HibernateUtil.getSessionFactory();
        this.tagDao = new TagDaoHibernate();
        this.spaceDao = new SpaceDaoHibernate();
    }

    /*
        8. Tags mas usados
     */

    public List<MostUsedTagDto> getMostUsedTags(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<MostUsedTagDto> mostUsedTagDtos = tagDao.findMostUsedTags(s);
            tx.commit();
            return mostUsedTagDtos;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    /*
        9. Devuelve los Tag cuyo nombre empiece por un texto dado, ignorando mayusculas/minusculas.
     */

    public List<Tag> getTagByText(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<Tag> tags = tagDao.findTagsByPrefix(s, "Fiesta");
            tx.commit();
            return tags;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }

    /*
        10. Devuelve el conteo de cuantos espacios hay por cada combinacion de ciudad y Tag
     */

    public List<CityTagCountDto> getCountSpaces(){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();
            List<CityTagCountDto> tags = tagDao.countSpacesByCityAndTag(s);
            tx.commit();
            return tags;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        }
    }



    public Long create(Tag tag){
        Transaction tx = null;
        try{
            Session s = sf.getCurrentSession();
            tx = s.beginTransaction();

            tagDao.create(s, tag);
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
