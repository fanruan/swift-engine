package com.finebi.cube.structure.column.date;

import com.finebi.cube.structure.column.BIColumnKey;
import com.fr.bi.stable.constant.DBConstant;
import com.fr.bi.stable.data.db.ICubeFieldSource;
import com.fr.bi.stable.utils.program.BINonValueUtils;

/**
 * This class created on 2016/4/1.
 *
 * @author Connery
 * @since 4.0
 */
public class BIDateColumnTool {
    public static final BIColumnKey generateYear(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }

    public static final BIColumnKey generateMonth(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_MONTH);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }

    public static final BIColumnKey generateDay(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_DAY);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }

    public static final BIColumnKey generateSeason(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_SEASON);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }

    public static final BIColumnKey generateWeek(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_WEEK);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }
    public static final BIColumnKey generateYearMonth(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_MONTH);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }
    public static final BIColumnKey generateYearMonthDay(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_MONTH_DAY);
        } else {
            throw BINonValueUtils.beyondControl();
        }

    }

    public static BIColumnKey generateYearSeason(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_SEASON);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateYearWeekNumber(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_WEEK_NUMBER);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateHour(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_HOUR);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateSecond(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_SECOND);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateMinute(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_MINUTE);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateYearMonthDayHour(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_MONTH_DAY_HOUR);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateYearMonthDayHourMinute(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_MONTH_DAY_HOUR_MINUTE);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateWeekNumber(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_WEEKNUMBER);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }

    public static BIColumnKey generateYearMonthDayHourMinuteSecond(ICubeFieldSource field) {
        if (field.getFieldType() == DBConstant.COLUMN.DATE) {
            return new BIColumnKey(field.getFieldName(), BIColumnKey.DATA_COLUMN_TYPE, BIColumnKey.DATA_SUB_TYPE_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND);
        } else {
            throw BINonValueUtils.beyondControl();
        }
    }
}
