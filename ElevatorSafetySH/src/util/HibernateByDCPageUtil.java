package util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class HibernateByDCPageUtil extends HibernateDaoSupport {
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findPageByDcQuery(DetachedCriteria dc, int PageSize,
			HttpServletRequest request) {
		MyHibernateCallback my = new MyHibernateCallback(this, dc, PageSize,
				request);
//		getHibernateTemplate().setSessionFactory(sessionFactory);
		return ((List) getHibernateTemplate().execute(my));
	}

	@SuppressWarnings("rawtypes")
	class MyHibernateCallback implements HibernateCallback {
		HibernateByDCPageUtil hbdpu;
		DetachedCriteria dc;
		int PageSize;
		HttpServletRequest request;

		public MyHibernateCallback(
				HibernateByDCPageUtil paramHibernateByDCPageUtil2,
				DetachedCriteria paramDetachedCriteria, int paramInt,
				HttpServletRequest paramHttpServletRequest) {
			this.hbdpu = paramHibernateByDCPageUtil2;
			this.dc = paramDetachedCriteria;
			this.PageSize = paramInt;
			this.request = paramHttpServletRequest;
		}

		public Object doInHibernate(Session session) throws HibernateException {
			List list = new ArrayList();

			PageService page = new PageService();

			Criteria criteria = this.dc.getExecutableCriteria(session);
			CriteriaImpl criteriaImpl = (CriteriaImpl) criteria;

			Projection projection = criteriaImpl.getProjection();
			ResultTransformer transformer = criteriaImpl.getResultTransformer();

			List orderEntitys = null;
			try {
				orderEntitys = (List) BeanUtils.forceGetProperty(criteriaImpl,
						"orderEntries");
				BeanUtils.forceSetProperty(criteriaImpl, "orderEntries",
						new ArrayList());
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}

			Integer totalCount = Integer.parseInt(criteria.setProjection(
					Projections.rowCount()).uniqueResult().toString());

			criteria.setProjection(projection);
			if (projection == null)
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

			if (transformer != null)
				criteria.setResultTransformer(transformer);

			try {
				BeanUtils.forceSetProperty(criteriaImpl, "orderEntries",
						orderEntitys);
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}

			if (totalCount.intValue() == 0) {
				page.notLog(this.request);
			} else {
				Integer intPage = Integer.valueOf(page.jlist(this.PageSize,
						totalCount.intValue(), this.request));

				list = criteria
						.setFirstResult(
								(intPage.intValue() - 1) * this.PageSize)
						.setMaxResults(this.PageSize).list();
			}
			return list;
		}
	}
}