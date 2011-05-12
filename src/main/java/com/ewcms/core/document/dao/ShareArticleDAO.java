/**
 * 
 */
package com.ewcms.core.document.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.ewcms.comm.jpa.dao.JpaDAO;
import com.ewcms.core.document.model.ShareArticle;

/**
 * @author 周冬初
 *
 */
@Repository
public class ShareArticleDAO extends JpaDAO<Integer, ShareArticle> {
    @SuppressWarnings("unchecked")
    public boolean shared(Integer siteId,Integer refId){
    	String hql = "FROM ShareArticle AS o  WHERE o.articleRmc.id=? and o.siteId=?";
    	List<ShareArticle> list = this.getJpaTemplate().find(hql,refId,siteId);
    	if (list==null || list.isEmpty()) return false;
    	return true;    	
    }
}
