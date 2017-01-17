package com.finebi.cube.conf.relation;

import com.finebi.cube.common.log.BILoggerFactory;
import com.finebi.cube.conf.BICubeConfigureCenter;
import com.finebi.cube.conf.BISystemDataManager;
import com.finebi.cube.conf.BITableRelationConfigurationProvider;
import com.finebi.cube.conf.relation.path.BITableContainer;
import com.finebi.cube.conf.relation.relation.IRelationContainer;
import com.finebi.cube.conf.table.BusinessTable;
import com.finebi.cube.relation.BITableRelation;
import com.finebi.cube.relation.BITableRelationPath;
import com.finebi.cube.relation.BITableSourceRelation;
import com.fr.bi.common.factory.BIFactoryHelper;
import com.fr.bi.stable.exception.*;
import com.fr.bi.stable.utils.program.BINonValueUtils;
import com.fr.fs.control.UserControl;
import com.fr.general.ComparatorUtils;
import com.fr.json.JSONArray;
import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by Connery on 2016/1/12.
 */
public class BISystemTableRelationManager extends BISystemDataManager<BIUserTableRelationManager> implements BITableRelationConfigurationProvider {
    private static final long serialVersionUID = 7984645297624233358L;
    private static String OBJ_TAG = "UserTableRelationManager";


    @Override
    public BIUserTableRelationManager constructUserManagerValue(Long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return BIFactoryHelper.getObject(BIUserTableRelationManager.class, userId);
    }

    @Override
    public String managerTag() {
        return OBJ_TAG;
    }

    @Override
    public String persistUserDataName(long key) {
        return managerTag();
    }

    @Override
    public void persistData(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        persistUserData(userId);
    }

    @Override
    public Set<BITableRelation> getAllTableRelation(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllTableRelation();

    }

    @Override
    public Set<BITableRelation> getAnalysisAllTableRelation(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllOldTableRelation();
    }

    @Override
    public void envChanged() {

    }

    @Override
    public boolean isPathDisable(long userId, BITableRelationPath disablePath) {
        userId = UserControl.getInstance().getSuperManagerID();
        BINonValueUtils.checkNull(disablePath);
        return getUserGroupConfigManager(userId).isPathDisable(disablePath);
    }

    @Override
    public void addDisableRelations(long userId, BITableRelationPath disablePath) throws BITablePathDuplicationException {
        userId = UserControl.getInstance().getSuperManagerID();
        BINonValueUtils.checkNull(disablePath);
        getUserGroupConfigManager(userId).addDisableRelations(disablePath);
    }

    @Override
    public void removeDisableRelations(long userId, BITableRelationPath disablePath) throws BITablePathAbsentException {
        userId = UserControl.getInstance().getSuperManagerID();
        BINonValueUtils.checkNull(disablePath);
        getUserGroupConfigManager(userId).removeDisableRelations(disablePath);
    }

    @Override
    public boolean containTableRelation(long userId, BITableRelation tableRelation) {
        userId = UserControl.getInstance().getSuperManagerID();
        BINonValueUtils.checkNull(tableRelation);
        return getUserGroupConfigManager(userId).containTableRelation(tableRelation);
    }

    @Override
    public boolean containTableRelationship(long userId, BITableRelation tableRelation) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).containTableRelation(tableRelation);
    }

    @Override
    public boolean containTablePrimaryRelation(long userId, BusinessTable table) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).containTablePrimaryRelation(table);
    }

    @Override
    public boolean containTableForeignRelation(long userId, BusinessTable table) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).containTableForeignRelation(table);
    }


    @Override
    public void registerTableRelation(long userId, BITableRelation tableRelation) throws BIRelationDuplicateException {
        userId = UserControl.getInstance().getSuperManagerID();
        BINonValueUtils.checkNull(tableRelation);
        getUserGroupConfigManager(userId).registerTableRelation(tableRelation);
    }

    @Override
    public void registerTableRelationSet(long userId, Set<BITableRelation> tableRelations) {
        userId = UserControl.getInstance().getSuperManagerID();
        BIUserTableRelationManager manager = getUserGroupConfigManager(userId);
        Iterator<BITableRelation> it = tableRelations.iterator();
        while (it.hasNext()) {
            try {
                manager.registerTableRelation(it.next());
            } catch (BIRelationDuplicateException e) {
                BILoggerFactory.getLogger().error(e.getMessage(), e);
                continue;
            }
        }
    }

    @Override
    public void removeTableRelation(long userId, BITableRelation tableRelation) throws BIRelationAbsentException, BITableAbsentException {
        userId = UserControl.getInstance().getSuperManagerID();
        getUserGroupConfigManager(userId).removeTableRelation(tableRelation);
    }

    @Override
    public boolean isChanged(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).isChanged();
    }

    @Override
    public void finishGenerateCubes(long userId, Set<BITableSourceRelation> absentRelation) {
        userId = UserControl.getInstance().getSuperManagerID();
        getUserGroupConfigManager(userId).finishGenerateCubes(absentRelation);
    }

    @Override
    public Map<BusinessTable, IRelationContainer> getAllTable2PrimaryRelation(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllTable2PrimaryRelation();
    }

    @Override
    public Map<BusinessTable, IRelationContainer> getAllTable2ForeignRelation(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllTable2ForeignRelation();

    }

    @Override
    public JSONObject createBasicRelationsJSON(long userId) {
        return null;
    }

    @Override
    public void clear(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        getUserGroupConfigManager(userId).clear();
    }

    @Override
    public Set<BITableRelationPath> getAllPath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws
            BITableUnreachableException, BITableAbsentException, BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllPath(juniorTable, primaryTable);
    }

    @Override
    public Set<BITableRelationPath> getAnalysisAllPath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws BITableUnreachableException, BITableAbsentException, BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAnalysisAllPath(juniorTable, primaryTable);
    }

    @Override
    public Set<BITableRelationPath> getAllAvailablePath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws BITableUnreachableException,
            BITableAbsentException, BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllAvailablePath(juniorTable, primaryTable);
    }

    @Override
    public Set<BITableRelationPath> getAnalysisAllAvailablePath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws BITableUnreachableException, BITableAbsentException, BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAnalysisAllAvailablePath(juniorTable, primaryTable);
    }

    @Override
    public Set<BITableRelationPath> getAllUnavailablePath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws BITableUnreachableException,
            BITableAbsentException, BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAllUnavailablePath(juniorTable, primaryTable);
    }

    @Override
    public Set<BITableRelationPath> getAnalysisAllUnavailablePath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws BITableUnreachableException, BITableAbsentException, BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getAnalysisAllUnavailablePath(juniorTable, primaryTable);
    }

    /**
     * 是不是关联只是减少了。
     *
     * @return
     */
    public boolean isRelationReduced(long userId) {
        return getUserGroupConfigManager(userId).isRelationReduced();
    }

    public boolean isRelationIncreased(long userId) {
        return getUserGroupConfigManager(userId).isRelationIncreased();

    }

    public boolean isRelationNoChange(long userId) {
        return getUserGroupConfigManager(userId).isRelationNoChange();

    }

    @Override
    public Set<BITableRelationPath> getAllTablePath(long userId) throws BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        Set<BusinessTable> allTables = getAllTables(userId);
        Iterator<BusinessTable> superTableIt = allTables.iterator();
        Set<BITableRelationPath> resultPaths = new HashSet<BITableRelationPath>();
        while (superTableIt.hasNext()) {
            BusinessTable superTable = superTableIt.next();
            Iterator<BusinessTable> juniorTableIt = allTables.iterator();
            while (juniorTableIt.hasNext()) {
                BusinessTable juniorTable = juniorTableIt.next();
                try {
                    Set<BITableRelationPath> paths = getAllPath(userId, juniorTable, superTable);
                    if (!paths.isEmpty()) {
                        resultPaths.addAll(paths);
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return resultPaths;
    }

    @Override
    public Set<BITableRelationPath> getAnalysisAllTablePath(long userId) throws BITableRelationConfusionException, BITablePathConfusionException {
        userId = UserControl.getInstance().getSuperManagerID();
        Set<BusinessTable> allTables = getAnalysisAllTables(userId);
        Iterator<BusinessTable> superTableIt = allTables.iterator();
        Set<BITableRelationPath> resultPaths = new HashSet<BITableRelationPath>();
        while (superTableIt.hasNext()) {
            BusinessTable superTable = superTableIt.next();
            Iterator<BusinessTable> juniorTableIt = allTables.iterator();
            while (juniorTableIt.hasNext()) {
                BusinessTable juniorTable = juniorTableIt.next();
                if (!ComparatorUtils.equals(superTable, juniorTable)) {
                    try {
                        resultPaths.addAll(getAnalysisAllPath(userId, juniorTable, superTable));
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
        }
        return resultPaths;
    }

    protected Set<BusinessTable> getAllTables(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return BICubeConfigureCenter.getPackageManager().getAllTables(userId);
    }

    protected Set<BusinessTable> getAnalysisAllTables(long userId) {
        userId = UserControl.getInstance().getSuperManagerID();
        return BICubeConfigureCenter.getPackageManager().getAnalysisAllTables(userId);
    }

    @Override
    public BITableRelationPath getFirstPath(long userId, BusinessTable juniorTable, BusinessTable primaryTable) throws BITableUnreachableException {
        userId = UserControl.getInstance().getSuperManagerID();
        try {
            Set<BITableRelationPath> allPath = this.getAllPath(userId, juniorTable, primaryTable);
            Iterator<BITableRelationPath> pathIterator = allPath.iterator();
            while (pathIterator.hasNext()) {
                return pathIterator.next();
            }
        } catch (Exception e) {
            BILoggerFactory.getLogger().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public BITableRelationPath getFirstAvailablePath(long userId, BusinessTable primaryTable, BusinessTable juniorTable) throws BITableUnreachableException {
        userId = UserControl.getInstance().getSuperManagerID();
        try {
            Set<BITableRelationPath> availablePath = this.getAllAvailablePath(userId, primaryTable, juniorTable);
            Iterator<BITableRelationPath> pathIterator = availablePath.iterator();
            while (pathIterator.hasNext()) {
                return pathIterator.next();
            }
        } catch (Exception e) {
            BILoggerFactory.getLogger().error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public BITableContainer getCommonSonTables(long userId, Set<BusinessTable> tables) {
        return null;
    }

    @Override
    public JSONObject createRelationsPathJSON(long userId) throws JSONException {
        userId = UserControl.getInstance().getSuperManagerID();
        JSONObject jo = new JSONObject();
        Set<BusinessTable> primaryTables = new HashSet<BusinessTable>();
        Set<BusinessTable> foreignTables = new HashSet<BusinessTable>();
        for (BITableRelation relation : getAnalysisAllTableRelation(userId)) {
            primaryTables.add(relation.getPrimaryTable());
            foreignTables.add(relation.getForeignTable());
        }
        for (BusinessTable p : primaryTables) {
            JSONObject relation = new JSONObject();
            jo.put(p.getID().getIdentity(), relation);
            for (BusinessTable f : foreignTables) {
                try {
                    Set<BITableRelationPath> path = getAnalysisAllAvailablePath(userId, f, p);
                    if (path != null || !path.isEmpty()) {
                        relation.put(f.getID().getIdentity(), createPathJSON(path));
                    }
                } catch (Exception e) {
                    BILoggerFactory.getLogger().error(e.getMessage(), e);
                }
            }
        }
        return jo;
    }

    private JSONArray createPathJSON(Set<BITableRelationPath> pathSet) throws JSONException {
        JSONArray ja = new JSONArray();
        for (BITableRelationPath p : pathSet) {
            JSONArray path = new JSONArray();
            ja.put(path);
            for (BITableRelation r : p.getAllRelations()) {
                JSONObject jo = new JSONObject();
                JSONObject primaryJo = new JSONObject();
                JSONObject foreignJo = new JSONObject();
                primaryJo.put("field_id", r.getPrimaryField().getFieldID().getIdentityValue());
                foreignJo.put("field_id", r.getForeignField().getFieldID().getIdentityValue());
                jo.put("primaryKey", primaryJo);
                jo.put("foreignKey", foreignJo);
                path.put(jo);
            }
        }
        return ja;
    }

    @Override
    public boolean isReachable(long userId, BusinessTable juniorTable, BusinessTable primaryTable) {
        return false;
    }

    @Override
    public IRelationContainer getPrimaryRelation(long userId, BusinessTable table) throws BITableAbsentException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getPrimaryRelation(table);
    }

    @Override
    public IRelationContainer getForeignRelation(long userId, BusinessTable table) throws BITableAbsentException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).getForeignRelation(table);
    }

    public boolean isRelationGenerated(long userId, BITableRelation tableRelation) throws BITableAbsentException, BIRelationAbsentException {
        userId = UserControl.getInstance().getSuperManagerID();
        return getUserGroupConfigManager(userId).isRelationGenerated(tableRelation);
    }

}