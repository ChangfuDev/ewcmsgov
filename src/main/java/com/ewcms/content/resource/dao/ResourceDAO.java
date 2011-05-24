/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ewcms.content.resource.dao;

import com.ewcms.common.jpa.dao.JpaDAO;
import com.ewcms.content.resource.model.Resource;

import org.springframework.stereotype.Repository;

/**
 *
 * @author wangwei
 */
@Repository
public class ResourceDAO extends JpaDAO<Integer, Resource> {

    @Override
    public void remove(Resource resource) {
        resource.setDeleteFlag(true);
        this.persist(resource);
    }

    @Override
    public void removeByPK(Integer pk) {
        Resource resource = this.get(pk);
        remove(resource);
    }

    public void clear(Integer pk){
        Resource resource = this.get(pk);
        if(resource.isDeleteFlag()){
            this.getJpaTemplate().remove(resource);
        }
    }

    public void revert(Integer pk) {
        Resource resource = this.get(pk);
        resource.setDeleteFlag(false);
        this.persist(resource);
    }
}
