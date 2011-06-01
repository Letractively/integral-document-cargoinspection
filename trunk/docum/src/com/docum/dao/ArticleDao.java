package com.docum.dao;

import java.util.List;

import com.docum.domain.po.common.ArticleCategory;
import com.docum.domain.po.common.ArticleFeature;
import com.docum.domain.po.common.ArticleFeatureInstance;

public interface ArticleDao extends BaseDao {

	public static final String GET_ARTICLE_CATEGORIES_BY_ARTICLE_QUERY = 
		"getArticleCategoryByArticle";
	public static final String GET_ARTICLE_FEATURES_BY_ARTICLE_QUERY = 
		"getArticleFeatureByArticle";
	public static final String GET_ARTICLE_FEATURE_INSTANCE_BY_ARTICLE_QUERY = 
		"getArticleFeatureInstanceByArticle";

	public List<ArticleCategory> getArticleCategoryByArticle(Long articleId);
	public List<ArticleFeature> getArticleFeatureByArticle(Long articleId);
	public List<ArticleFeatureInstance> getArticleFeatureInstanceByArticle(Long articleFeatureId);
}