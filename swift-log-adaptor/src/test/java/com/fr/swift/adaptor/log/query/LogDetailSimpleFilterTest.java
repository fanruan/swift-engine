package com.fr.swift.adaptor.log.query;

import com.fr.annotation.Test;
import com.fr.general.ComparatorUtils;
import com.fr.stable.query.QueryFactory;
import com.fr.stable.query.condition.QueryCondition;
import com.fr.stable.query.restriction.RestrictionFactory;
import com.fr.swift.adaptor.log.QueryConditionAdaptor;
import com.fr.swift.cal.QueryInfo;
import com.fr.swift.db.Table;
import com.fr.swift.db.impl.SwiftDatabase;
import com.fr.swift.service.SwiftAnalyseService;
import com.fr.swift.source.DataSource;
import com.fr.swift.source.Row;
import com.fr.swift.source.SourceKey;
import com.fr.swift.source.SwiftResultSet;
import com.fr.swift.source.db.QueryDBSource;

import java.util.HashSet;
import java.util.Set;

/**
 * This class created on 2018/4/27
 *
 * @author Lucifer
 * @description
 * @since Advanced FineBI 5.0
 */
public class LogDetailSimpleFilterTest extends LogBaseTest {

    @Test
    public void testEQ() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestEQ");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testEQ"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testEQ"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testEQ"));
            transportAndIndex(dataSource, table);
            //eq
            QueryCondition eqQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.eq("合同类型", "购买合同"));
            QueryInfo eqQueryInfo = QueryConditionAdaptor.adaptCondition(eqQueryCondition, table);
            SwiftResultSet eqResultSet = SwiftAnalyseService.getInstance().executeQuery(eqQueryInfo);
            int eqindex = table.getMeta().getColumnIndex("合同类型");
            while (eqResultSet.next()) {
                Row row = eqResultSet.getRowData();
                assertEquals(row.getValue(eqindex), "购买合同");
            }


        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testNEQ() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestNEQ");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testNEQ"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testNEQ"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testNEQ"));

            transportAndIndex(dataSource, table);
            QueryCondition neqQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.neq("合同类型", "购买合同"));
            QueryInfo neqQueryInfo = QueryConditionAdaptor.adaptCondition(neqQueryCondition, table);
            SwiftResultSet neqResultSet = SwiftAnalyseService.getInstance().executeQuery(neqQueryInfo);
            int neqindex = table.getMeta().getColumnIndex("合同类型");
            while (neqResultSet.next()) {
                Row row = neqResultSet.getRowData();
                assertNotSame(row.getValue(neqindex), "购买合同");
            }
        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    @Test
    public void testGT() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestGT");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testGT"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testGT"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testGT"));
            transportAndIndex(dataSource, table);

            QueryCondition gtQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.gt("总金额", 1000000d));
            QueryInfo gtQueryInfo = QueryConditionAdaptor.adaptCondition(gtQueryCondition, table);
            SwiftResultSet gtResultSet = SwiftAnalyseService.getInstance().executeQuery(gtQueryInfo);
            int gtindex = table.getMeta().getColumnIndex("总金额");
            int count = 0;
            while (gtResultSet.next()) {
                Row row = gtResultSet.getRowData();
                assertTrue(((Long) row.getValue(gtindex)).doubleValue() > 1000000d);
                if (((Long) row.getValue(gtindex)).doubleValue() == 1000000d) {
                    count++;
                }
            }
            assertTrue(count == 0);

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testGTE() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestGTE");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testGTE"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testGTE"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testGTE"));
            transportAndIndex(dataSource, table);

            QueryCondition gteQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.gte("总金额", 1000000d));
            QueryInfo gteQueryInfo = QueryConditionAdaptor.adaptCondition(gteQueryCondition, table);
            SwiftResultSet gteResultSet = SwiftAnalyseService.getInstance().executeQuery(gteQueryInfo);
            int gteindex = table.getMeta().getColumnIndex("总金额");
            int count = 0;
            while (gteResultSet.next()) {
                Row row = gteResultSet.getRowData();
                assertTrue(((Long) row.getValue(gteindex)).doubleValue() >= 1000000d);
                if (((Long) row.getValue(gteindex)).doubleValue() == 1000000d) {
                    count++;
                }
            }
            assertTrue(count == 13);

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    @Test
    public void testLT() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestLT");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testLT"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testLT"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testLT"));
            transportAndIndex(dataSource, table);

            QueryCondition ltQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.lt("总金额", 1000000d));
            QueryInfo ltQueryInfo = QueryConditionAdaptor.adaptCondition(ltQueryCondition, table);
            SwiftResultSet ltResultSet = SwiftAnalyseService.getInstance().executeQuery(ltQueryInfo);
            int ltindex = table.getMeta().getColumnIndex("总金额");
            int count = 0;
            while (ltResultSet.next()) {
                Row row = ltResultSet.getRowData();
                assertTrue(((Long) row.getValue(ltindex)).doubleValue() < 1000000d);
                if (((Long) row.getValue(ltindex)).doubleValue() == 1000000d) {
                    count++;
                }
            }
            assertTrue(count == 0);

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testLTE() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestLTE");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testLTE"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testLTE"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testLTE"));
            transportAndIndex(dataSource, table);

            QueryCondition lteQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.lte("总金额", 1000000d));
            QueryInfo lteQueryInfo = QueryConditionAdaptor.adaptCondition(lteQueryCondition, table);
            SwiftResultSet lteResultSet = SwiftAnalyseService.getInstance().executeQuery(lteQueryInfo);
            int lteindex = table.getMeta().getColumnIndex("总金额");
            int count = 0;
            while (lteResultSet.next()) {
                Row row = lteResultSet.getRowData();
                assertTrue(((Long) row.getValue(lteindex)).doubleValue() <= 1000000d);
                if (((Long) row.getValue(lteindex)).doubleValue() == 1000000d) {
                    count++;
                }
            }
            assertTrue(count == 13);

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testIn() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestIn");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testIn"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testIn"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testIn"));
            transportAndIndex(dataSource, table);

            Set<String> set = new HashSet<String>();
            set.add("长期协议");
            set.add("长期协议订单");
            QueryCondition inQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.in("合同类型", set));
            QueryInfo inQueryInfo = QueryConditionAdaptor.adaptCondition(inQueryCondition, table);
            SwiftResultSet inResultSet = SwiftAnalyseService.getInstance().executeQuery(inQueryInfo);
            int inindex = table.getMeta().getColumnIndex("合同类型");
            while (inResultSet.next()) {
                Row row = inResultSet.getRowData();
                assertTrue(ComparatorUtils.equals(row.getValue(inindex), "长期协议")
                        || ComparatorUtils.equals(row.getValue(inindex), "长期协议订单"));
            }

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testNotIn() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestNotIn");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testNotIn"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testNotIn"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testNotIn"));
            transportAndIndex(dataSource, table);

            Set<String> set = new HashSet<String>();
            set.add("长期协议");
            set.add("长期协议订单");
            QueryCondition notinQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.notIn("合同类型", set));
            QueryInfo notinQueryInfo = QueryConditionAdaptor.adaptCondition(notinQueryCondition, table);
            SwiftResultSet notinResultSet = SwiftAnalyseService.getInstance().executeQuery(notinQueryInfo);
            int notinindex = table.getMeta().getColumnIndex("合同类型");
            while (notinResultSet.next()) {
                Row row = notinResultSet.getRowData();
                assertTrue(!ComparatorUtils.equals(row.getValue(notinindex), "长期协议")
                        && !ComparatorUtils.equals(row.getValue(notinindex), "长期协议订单"));
            }

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testLike() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestLike");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testLike"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testLike"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testLike"));
            transportAndIndex(dataSource, table);

            QueryCondition likeQueryCondition = QueryFactory.create().addRestriction(RestrictionFactory.like("合同类型", "协议"));
            QueryInfo likeQueryInfo = QueryConditionAdaptor.adaptCondition(likeQueryCondition, table);
            SwiftResultSet likeResultSet = SwiftAnalyseService.getInstance().executeQuery(likeQueryInfo);
            int likeindex = table.getMeta().getColumnIndex("合同类型");
            while (likeResultSet.next()) {
                Row row = likeResultSet.getRowData();
                assertTrue(row.getValue(likeindex).toString().contains("协议"));
            }

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testStartWith() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestStartWith");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testStartWith"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testStartWith"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testStartWith"));
            transportAndIndex(dataSource, table);

            QueryCondition startwithCondition = QueryFactory.create().addRestriction(RestrictionFactory.startWith("合同类型", "长期"));
            QueryInfo startwithQueryInfo = QueryConditionAdaptor.adaptCondition(startwithCondition, table);
            SwiftResultSet startwithResultSet = SwiftAnalyseService.getInstance().executeQuery(startwithQueryInfo);
            int startwithindex = table.getMeta().getColumnIndex("合同类型");
            while (startwithResultSet.next()) {
                Row row = startwithResultSet.getRowData();
                assertTrue(row.getValue(startwithindex).toString().startsWith("长期"));
            }

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }

    public void testEndWith() {
        try {
            DataSource dataSource = new QueryDBSource("select * from DEMO_CONTRACT", "DBtestEndWith");
            if (!SwiftDatabase.getInstance().existsTable(new SourceKey("testEndWith"))) {
                SwiftDatabase.getInstance().createTable(new SourceKey("testEndWith"), dataSource.getMetadata());
            }
            Table table = SwiftDatabase.getInstance().getTable(new SourceKey("testEndWith"));
            transportAndIndex(dataSource, table);

            QueryCondition endwithCondition = QueryFactory.create().addRestriction(RestrictionFactory.startWith("合同类型", "合同"));
            QueryInfo endwithQueryInfo = QueryConditionAdaptor.adaptCondition(endwithCondition, table);
            SwiftResultSet endwithResultSet = SwiftAnalyseService.getInstance().executeQuery(endwithQueryInfo);
            int endwithindex = table.getMeta().getColumnIndex("合同类型");
            while (endwithResultSet.next()) {
                Row row = endwithResultSet.getRowData();
                assertTrue(row.getValue(endwithindex).toString().endsWith("合同"));
            }

        } catch (Exception e) {
            LOGGER.error(e);
            assertTrue(false);
        }
    }
}
