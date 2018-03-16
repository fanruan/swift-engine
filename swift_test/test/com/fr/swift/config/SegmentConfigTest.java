package com.fr.swift.config;

import com.fr.config.DBEnv;
import com.fr.config.dao.DaoContext;
import com.fr.config.dao.impl.HibernateClassHelperDao;
import com.fr.config.dao.impl.HibernateEntityDao;
import com.fr.config.dao.impl.HibernateXmlEnityDao;
import com.fr.config.entity.ClassHelper;
import com.fr.config.entity.Entity;
import com.fr.config.entity.XmlEntity;
import com.fr.stable.db.DBContext;
import com.fr.stable.db.option.DBOption;
import com.fr.swift.config.conf.SegmentConfig;
import com.fr.swift.config.unique.SegmentUnique;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author yee
 * @date 2018/3/9
 */
public class SegmentConfigTest extends TestCase {


    public void setUp() throws Exception {
        super.setUp();
        DBOption dbOption = new DBOption();
        dbOption.setPassword("root");
        dbOption.setDialectClass("com.fr.third.org.hibernate.dialect.MySQL5InnoDBDialect");
        dbOption.setDriverClass("com.mysql.jdbc.Driver");
        dbOption.setUsername("root");
        dbOption.setUrl("jdbc:mysql://localhost:3306/config");
        dbOption.addRawProperty("hibernate.show_sql", true)
                .addRawProperty("hibernate.format_sql", true).addRawProperty("hibernate.connection.autocommit", true);
        DBContext dbProvider = DBContext.create();
        dbProvider.addEntityClass(Entity.class);
        dbProvider.addEntityClass(XmlEntity.class);
        dbProvider.addEntityClass(ClassHelper.class);
        dbProvider.init(dbOption);
        DBEnv.setDBContext(dbProvider);
        DaoContext.setClassHelperDao(new HibernateClassHelperDao());
        DaoContext.setXmlEntityDao(new HibernateXmlEnityDao());
        DaoContext.setEntityDao(new HibernateEntityDao());
    }

    public void testAddAndGet() {
        SegmentUnique source = SegmentCreater.getSegment();
        SegmentConfig.getInstance().addSegments(source);

        assertEquals(SegmentConfig.getInstance().getAllSegments().size(), 1);

        IConfigSegment unique = SegmentConfig.getInstance().getSegmentByKey(source.getSourceKey());
        assertEquals(unique.getSourceKey(), source.getSourceKey());
        List<ISegmentKey> keyUniques = unique.getSegments();
        List<ISegmentKey> sourceKeyUniques = source.getSegments();
        assertEquals(keyUniques.size(), sourceKeyUniques.size());
        for (int i = 0, len = sourceKeyUniques.size(); i < len; i++) {
            ISegmentKey key = sourceKeyUniques.get(i);
            ISegmentKey target = keyUniques.get(i);
            assertEquals(target.getName(), key.getName());
            assertEquals(target.getSegmentOrder(), key.getSegmentOrder());
            assertEquals(target.getSourceId(), key.getSourceId());
            assertEquals(target.getStoreType(), key.getStoreType());
            assertEquals(target.getUri(), key.getUri());
        }
//        assertEquals(metaData.getRemark(), MetaDataCreater.getMA().getRemark());
//        assertEquals(metaData.getSchema(), MetaDataCreater.getMA().getSchema());
//        assertEquals(metaData.getFieldList().size(), MetaDataCreater.getMA().getFieldList().size());
    }

    public void testAddAndRemove() {
        SegmentUnique source = SegmentCreater.getSegment();
        SegmentConfig.getInstance().addSegments(source);
        assertEquals(SegmentConfig.getInstance().getAllSegments().size(), 1);
        SegmentConfig.getInstance().removeSegment(source.getSourceKey());
        assertEquals(SegmentConfig.getInstance().getAllSegments().size(), 0);
    }

    public void testAddAndModify() {
        SegmentUnique source = SegmentCreater.getSegment();
        SegmentConfig.getInstance().addSegments(source);
        IConfigSegment target1 = SegmentConfig.getInstance().getSegmentByKey(source.getSourceKey());
        SegmentUnique modify = SegmentCreater.getModify();
        SegmentConfig.getInstance().modifySegment(modify);

        IConfigSegment target2 = SegmentConfig.getInstance().getSegmentByKey(source.getSourceKey());

        assertEquals(SegmentConfig.getInstance().getAllSegments().size(), 1);
        assertEquals(target1.getSourceKey(), target2.getSourceKey());
        List<ISegmentKey> keyUniques = target1.getSegments();
        List<ISegmentKey> sourceKeyUniques = target2.getSegments();
        assertEquals(keyUniques.size(), sourceKeyUniques.size());
        for (int i = 0, len = sourceKeyUniques.size(); i < len; i++) {
            ISegmentKey key = sourceKeyUniques.get(i);
            ISegmentKey target = keyUniques.get(i);
            assertNotSame(key.getName(), target.getName());
            assertEquals(key.getSegmentOrder(), target.getSegmentOrder());
            assertEquals(key.getSourceId(), target.getSourceId());
            assertEquals(key.getStoreType(), target.getStoreType());
            assertNotSame(key.getUri(), target.getUri());
        }
//        assertEquals(metaData1.getTableName(), metaData2.getTableName());
//        assertNotSame(metaData1.getRemark(), metaData2.getRemark());
//        assertNotSame(metaData1.getSchema(), metaData2.getSchema());
//        assertEquals(metaData1.getFieldList().size(), metaData2.getFieldList().size());
    }
}