package core.web;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.ByteConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.FloatConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.ShortConverter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.context.ApplicationContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import core.extjs.ExtJSBaseParameter;
import core.service.Service;
import core.support.Group;
import core.support.Item;

/**
 * @author Yang Tian
 * @email 1298588579@qq.com
 */
public class SystemInitListener implements ServletContextListener, WebApplicationInitializer {

	private static ApplicationContext applicationContext;

	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
/**
 * 格式转换
 * 
 * 2016-1-14下午9:28:28
 * void
 */
	private void initConvertor() {
		ConvertUtils.register(new JavaUtilDateConverter(), java.util.Date.class);
		ConvertUtils.register(new ByteConverter(null), Byte.class);
		ConvertUtils.register(new ShortConverter(null), Short.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new IntegerConverter(null), Integer.class);
		ConvertUtils.register(new FloatConverter(null), Float.class);
		ConvertUtils.register(new DoubleConverter(null), Double.class);
	}

	public void contextInitialized(ServletContextEvent event) {
		initConvertor();
		loadSystemParameter(event.getServletContext());
		applicationContext = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}
/**
 * 获得容器中的service_bean
 * @param entityName
 * @param key
 * @param propName
 * @return
 * 2016-1-14下午9:26:44
 * Object
 */
	public static Object getDynamicProperty(String entityName, Serializable key, String propName) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Object cache = request.getAttribute(entityName + "_" + key);
		if (cache != null) {
			try {
				return PropertyUtils.getProperty(cache, propName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		String s = entityName.substring(0, 1).toLowerCase() + entityName.substring(1) + "ServiceImpl";
		Service service = (Service) applicationContext.getBean(s);
		Object entity = (ExtJSBaseParameter) service.get(key);
		request.setAttribute(entityName + "_" + key, entity);
		try {
			return PropertyUtils.getProperty(entity, propName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
/**
 * 加载dictionary.xml中的数据并加入SystemCache.DICTIONARY  map中
 * @param servletContext
 * 2016-2-6下午9:17:44
 * void
 */
	private void loadSystemParameter(ServletContext servletContext) {
		String strGroup = "group";
		String strItem = "item";
		String strName = "name";
		String strKey = "key";
		String strValue = "value";

		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(getClass().getClassLoader().getResource(SystemContents.DICTIONARY_PATH));

			Element root = doc.getRootElement();
			Map<String, Group> dict = new HashMap<String, Group>();
			for (Iterator<Element> itGroup = root.elementIterator(strGroup); itGroup.hasNext();) {
				Element eleGroup = itGroup.next();
				String groupName = eleGroup.attributeValue(strName);
				Map<String, Item> map = new LinkedHashMap<String, Item>();
				for (Iterator<Element> itItem = eleGroup.elementIterator(strItem); itItem.hasNext();) {
					Element eleItem = itItem.next();
					Item item = new Item();
					item.setKey(eleItem.attributeValue(strKey));
					item.setValue(eleItem.attributeValue(strValue));
					map.put(item.getKey(), item);
				}
				//把xml中的主结果放入item中，模仿xml中的属性设定
				//groupName即xml中的name，其他均一至对应
				dict.put(groupName, new Group(groupName, map));

			}
			servletContext.setAttribute(SystemContents.DICTIONARY, dict);
			SystemCache.DICTIONARY.putAll(dict);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void onStartup(ServletContext arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
