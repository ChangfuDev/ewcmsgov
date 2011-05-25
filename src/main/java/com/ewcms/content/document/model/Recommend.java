/**
 * Copyright (c)2010-2011 Enterprise Website Content Management System(EWCMS), All rights reserved.
 * EWCMS PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * http://www.ewcms.com
 */

package com.ewcms.content.document.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 推荐文章
 * 
 * @author 吴智俊
 */
@Entity
@Table(name = "doc_recommend")
@SequenceGenerator(name = "seq_doc_recommend", sequenceName = "seq_doc_recommend_id", allocationSize = 1)
public class Recommend implements Serializable {

	private static final long serialVersionUID = 8681780886911786864L;

	@Id
	@GeneratedValue(generator = "seq_doc_recommend", strategy = GenerationType.SEQUENCE)
	private Integer id;
	@Column(name = "sort")
	private Integer sort;
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE}, fetch = FetchType.EAGER,targetEntity = ArticleRmc.class)
	@JoinColumn(name="recommend_articlermc_id")
	private ArticleRmc articleRmc;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@JsonIgnore
	public ArticleRmc getArticleRmc() {
		return articleRmc;
	}

	public void setArticleRmc(ArticleRmc articleRmc) {
		this.articleRmc = articleRmc;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Recommend other = (Recommend) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
