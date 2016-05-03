/**
 * 处理汇总表的数据
 */
BI.SummaryTableModel = BI.inherit(FR.OB, {
    _init: function(){
        BI.SummaryTableModel.superclass._init.apply(this, arguments);
        this.wId = this.options.wId;
        this.EMPTY_VALUE = BI.UUID();
        this._refreshDimsInfo();

        //展开的节点的树结构，需要保存
        this.tree = new BI.Tree();
        this.crossTree = new BI.Tree();

        this.page = [0, 0, 0, 0, 0];
        this.eTree = new BI.Tree();         //展开节点——维度
        this.crossETree = new BI.Tree();    //展开节点——系列，用于交叉表

        this.clickValue = "";               //点击的值
        this.pageOperator = BICst.TABLE_PAGE_OPERATOR.REFRESH;  //翻页操作

    },

    getWidgetId: function(){
        return this.wId;
    },

    isNeed2Freeze: function(){
        if(this.targetIds.length === 0 || (this.dimIds.length + this.crossDimIds.length) === 0){
            return false;
        }
        return this.freezeDim;
    },

    getFreezeCols: function() {
        return this.freezeCols;
    },

    getMergeCols: function() {
        return this.mergeCols;
    },

    getColumnSize: function() {
        return this.columnSize;
    },

    getHeader: function() {
        return this.header;
    },

    getCrossHeader: function() {
        return this.crossHeader;
    },

    getItems: function() {
        return this.items;
    },

    getCrossItems: function() {
        return this.crossItems;
    },

    getPage: function() {
        return this.page;
    },

    getData: function() {
        return this.data;
    },

    getPageOperator: function() {
        return this.pageOperator;
    },

    isShowNumber: function() {
        return this.showNumber;
    },

    setPageOperator: function(pageOperator) {
        this.pageOperator = pageOperator;
    },

    getExtraInfo: function(){
        var settings = BI.Utils.getWidgetSettingsByID(this.wId) || BICst.DEFAULT_CHART_SETTING;
        var op = {};
        op.expander = {
            x: {
                type: settings.open_col_node,
                value: [this._formatExpanderTree(this.crossETree.toJSONWithNode())]
            },
            y: {
                type: settings.open_row_node,
                value: [this._formatExpanderTree(this.eTree.toJSONWithNode())]
            }
        };
        op.clickvalue = this.clickValue;
        op.page = this.pageOperator;
        return op;
    },

    setDataAndPage: function(data){
        this.data = data.data;
        this.page = data.page;
    },

    _refreshDimsInfo: function(){
        //使用中的行表头——考虑钻取
        var self = this;
        this.dimIds = [];
        var view = BI.Utils.getWidgetViewByID(this.wId);
        var drill = BI.Utils.getDrillByID(this.wId);
        BI.each(view[BICst.REGION.DIMENSION1], function(i, dId){
            if(BI.isNotNull(drill)) {
                //钻取状态时是否展示  {1: [{dId: 2, value: []}, {dId: 3, value: []}]}
                var drilled = false, used = false;
                BI.each(drill, function(drId, drArray){
                    if(BI.isNotEmptyArray(drArray)){
                        if(drId === dId) {
                            drilled = true;
                        }
                        BI.each(drArray, function(i, dr){
                            if(dr.dId === dId){
                                drilled = true;
                                used = (i === drArray.length - 1);
                            }
                        });
                    }
                });
                if(drilled === true) {
                    used === true && (self.dimIds.push(dId));
                } else {
                    BI.Utils.isDimensionUsable(dId) && (self.dimIds.push(dId));
                }
            } else {
                BI.Utils.isDimensionUsable(dId) && (self.dimIds.push(dId));
            }
        });

        //使用中的列表头
        this.crossDimIds = [];
        BI.each(view[BICst.REGION.DIMENSION2], function(i, dId){
            BI.Utils.isDimensionUsable(dId) && (self.crossDimIds.push(dId));
        });

        //使用中的指标
        this.targetIds = [];
        BI.each(view[BICst.REGION.TARGET1], function(i, dId){
            BI.Utils.isDimensionUsable(dId) && (self.targetIds.push(dId));
        });
    },

    /**
     * 重置部分数据，用于无后台请求
     */
    _resetPartAttrs: function(){
        var widgetId = this.options.wId;
        var settings = BI.Utils.getWidgetSettingsByID(widgetId);
        this.showNumber = settings.show_number;         //显示行号
        this.showRowTotal = settings.show_row_total;    //显示行汇总
        this.showColTotal = settings.show_col_total;    //显示列汇总
        this.openRowNode = settings.open_row_node;      //展开所有行表头节点
        this.openColNode = settings.open_col_node;      //展开所有列表头节点
        this.freezeDim = settings.freeze_dim;
        this.header = [];
        this.items = [];
        this.crossHeader = [];
        this.crossItems = [];
        this.mergeCols = [];
        this.columnSize = settings.column_size || [];

    },

    resetETree: function(){
        this.eTree = new BI.Tree();
        this.crossETree = new BI.Tree();
    },

    /**
     * format展开节点树
     */
    _formatExpanderTree: function(eTree){
        var self = this, result = [];
        BI.each(eTree, function(i, t){
            var item = {};
            item.name = t.node.name;
            if(BI.isNotNull(t.children)){
                item.children = self._formatExpanderTree(t.children);
            }
            result.push(item);
        });
        return result;
    },

    /**
     * 表items
     */
    _createTableItems: function(c, currentLayer, parent){
        var self = this, items = [];
        currentLayer++;
        BI.each(c, function(i, child) {
            //可以直接使用每一层中的树节点的parent.id + child.n作为id，第一层无需考虑，因为第一层不可能有相同值
            //考虑到空字符串问题
            var cId = BI.isEmptyString(child.n) ? self.EMPTY_VALUE : child.n;
            var nodeId = BI.isNotNull(parent) ? parent.get("id") + cId : cId;
            var node = new BI.Node(nodeId);
            var currDid = self.dimIds[currentLayer - 1], currValue = child.n;
            node.set("name", currValue);
            self.tree.addNode(parent, node);
            var pValues = [];
            var tempLayer = currentLayer, tempNodeId = nodeId;
            while (tempLayer > 0) {
                pValues.push({
                    value: [self.tree.search(tempNodeId).get("name")],
                    dId: self.dimIds[tempLayer - 1]
                });
                tempNodeId = self.tree.search(tempNodeId).getParent().get("id");
                tempLayer--;
            }
            var item = {
                type: "bi.normal_expander_cell",
                text: child.n,
                dId: currDid,
                expandCallback: function () {
                    //全部展开再收起——横向
                    var clickNode = self.eTree.search(nodeId);
                    if(self.openRowNode === true){
                        self._addNode2eTree4OpenRowNode(nodeId);
                    } else {
                        if (BI.isNull(clickNode)) {
                            self.eTree.addNode(self.eTree.search(BI.isNull(parent) ? self.tree.getRoot().get("id") : parent.get("id")), BI.deepClone(node));
                        } else {
                            clickNode.getParent().removeChild(nodeId);
                        }
                    }

                    self.pageOperator = BICst.TABLE_PAGE_OPERATOR.EXPAND;
                    self.clickValue = child.n;
                    self.expanderCallback();
                },
                drillCallback: function(drillId) {
                    var drillMap = BI.Utils.getDrillByID(self.wId);
                    //value 存当前的过滤条件——因为每一次钻取都要带上所有父节点的值
                    //当前钻取的根节点
                    var rootId = currDid;
                    BI.each(drillMap, function(drId, ds){
                        if(currDid === drId || (ds.length > 0 && ds[ds.length - 1].dId === currDid)) {
                            rootId = drId;
                        }
                    });

                    var drillOperators = drillMap[rootId] || [];
                    //上钻
                    if(drillId === BI.NormalExpanderCell.UP_DRILL) {
                        drillOperators.pop();
                    } else {
                        drillOperators.push({
                            dId: drillId,
                            values: pValues
                        });
                    }
                    drillMap[rootId] = drillOperators;
                    self.clickedCallback(BI.extend(BI.Utils.getLinkageValuesByID(self.wId), drillMap));
                }
            };
            //展开情况——最后一层没有这个展开按钮
            if (currentLayer < self.dimIds.length) {
                item.needExpand = true;
                item.isExpanded = false;
            }
            //有c->说明有children，构造children，并且需要在children中加入汇总情况（如果有并且需要）
            if (BI.isNotNull(child.c)) {
                item.children = self._createTableItems(child.c, currentLayer, node) || [];
                if (BI.isNotEmptyArray(child.s) && self.showRowTotal === true) {
                    var vs = [];
                    BI.each(child.s, function (k, cs) {
                        var tId = self.targetIds[k];
                        vs.push({
                            type: "bi.target_body_normal_cell",
                            text: cs,
                            dId: tId,
                            clicked: pValues
                        });
                    });
                    item.children.push({
                        type: "bi.page_table_cell",
                        text: BI.i18nText("BI-Summary_Values"),
                        tag: BI.UUID(),
                        isSum: true,
                        values: vs
                    })
                }
                item.isExpanded = true;
            } else if (BI.isNotNull(child.s)) {
                var values = [];
                if (BI.isNotNull(child.s.c) || BI.isNotNull(child.s.s)) {
                    self._createTableSumItems(child.s.c, values);
                    self.showColTotal === true && self._createTableSumItems(child.s.s, values);
                } else {
                    BI.each(child.s, function (j, sum) {
                        var tId = self.targetIds[j];
                        values.push({
                            type: "bi.target_body_normal_cell",
                            text: sum,
                            dId: tId,
                            clicked: pValues
                        })
                    });
                }
                item.values = values;
            }
            items.push(item);
        });
        return items;
    },

    /**
     * 展开所有节点的情况下的收起    横向
     */
    _addNode2eTree4OpenRowNode: function(nodeId){
        var self = this;
        var clickNode = self.eTree.search(nodeId);
        if(BI.isNull(clickNode)) {
            //找到原始tree的这个节点的所有父节点，遍历一遍是否存在于eTree中
            //a、存在，向eTree直接添加；b、不存在，把这些父级节点都添加进去
            var pNodes = [];
            while(true) {
                if(BI.isNull(this.eTree.search(nodeId))) {
                    var node = this.tree.search(nodeId);
                    pNodes.push(node);
                    if(node.getParent().get("id") === this.tree.getRoot().get("id")) {
                        break;
                    }
                } else {
                    break;
                }
                nodeId = this.tree.search(nodeId).getParent().get("id");
            }
            pNodes.reverse();
            BI.each(pNodes, function(i, pNode){
                var epNode = self.eTree.search(pNode.getParent().get("id"));
                pNode.removeAllChilds();
                self.eTree.addNode(BI.isNotNull(epNode) ? epNode : self.eTree.getRoot(), BI.deepClone(pNode));
            });
        } else {
            //如果已经在这个eTree中，看其是否存在兄弟节点，如果没有应该删除当前节点所在的树，有的话， 只删除自身
            function getFinalParent(nodeId){
                var node = self.eTree.search(nodeId);
                if(node.getParent().get("id") === self.eTree.getRoot().get("id")) {
                    return nodeId;
                } else {
                    return getFinalParent(node.getParent().get("id"));
                }
            }
            if(this.eTree.search(nodeId).getParent().getChildrenLength() > 1){
                this.eTree.search(nodeId).getParent().removeChild(nodeId);
            } else if(this.eTree.search(nodeId).getChildrenLength() > 0) {
                //此时应该是做收起，把所有的children都remove掉
                this.eTree.search(nodeId).removeAllChilds();
            } else {
                this.eTree.getRoot().removeChild(getFinalParent(nodeId));
            }
        }
    },

    /**
     * 展开所有节点下的收起   纵向
     */
    _addNode2crossETree4OpenColNode: function(nodeId) {
        var self = this;
        var clickNode = self.crossETree.search(nodeId);
        if(BI.isNull(clickNode)) {
            //找到原始tree的这个节点的所有父节点，遍历一遍是否存在于eTree中
            //a、存在，向eTree直接添加；b、不存在，把这些父级节点都添加进去
            var pNodes = [];
            while(true) {
                if(BI.isNull(this.crossETree.search(nodeId))) {
                    var node = this.crossTree.search(nodeId);
                    pNodes.push(node);
                    if(node.getParent().get("id") === this.crossTree.getRoot().get("id")) {
                        break;
                    }
                } else {
                    break;
                }
                nodeId = this.crossTree.search(nodeId).getParent().get("id");
            }
            pNodes.reverse();
            BI.each(pNodes, function(i, pNode){
                var epNode = self.crossETree.search(pNode.getParent().get("id"));
                pNode.removeAllChilds();
                self.crossETree.addNode(BI.isNotNull(epNode) ? epNode : self.crossETree.getRoot(), BI.deepClone(pNode));
            });
        } else {
            //如果已经在这个eTree中，应该删除当前节点所在的树
            function getFinalParent(nodeId){
                var node = self.crossETree.search(nodeId);
                if(node.getParent().get("id") === self.crossETree.getRoot().get("id")) {
                    return nodeId;
                } else {
                    return getFinalParent(node.getParent().get("id"));
                }
            }
            if(this.crossETree.search(nodeId).getParent().getChildrenLength() > 1){
                this.crossETree.search(nodeId).getParent().removeChild(nodeId);
            } else if(this.crossETree.search(nodeId).getChildrenLength() > 0) {
                //此时应该是做收起，把所有的children都remove掉
                this.crossETree.search(nodeId).removeAllChilds();
            }  else {
                this.crossETree.getRoot().removeChild(getFinalParent(nodeId));
            }
        }
    },

    _createGroupTableHeader: function(){
        var self = this;
        BI.each(this.dimIds.concat(this.targetIds), function(i, dId){
            BI.isNotNull(dId) &&
            self.header.push({
                type: "bi.normal_header_cell",
                dId: dId,
                text: BI.Utils.getDimensionNameByID(dId),
                sortFilterChange: function(v){
                    self.resetETree();
                    self.pageOperator = BICst.TABLE_PAGE_OPERATOR.REFRESH;
                    self.headerOperatorCallback(v, dId);
                }
            });
        });
    },

    /**
     * 交叉表——header and crossHeader
     */
    _createCrossTableHeader: function(){
        var self = this;
        BI.each(this.dimIds, function(i, dId){
            if(BI.isNotNull(dId)) {
                self.header.push({
                    type: "bi.normal_header_cell",
                    dId: dId,
                    text: BI.Utils.getDimensionNameByID(dId),
                    sortFilterChange: function(v){
                        self.resetETree();
                        self.pageOperator = BICst.TABLE_PAGE_OPERATOR.REFRESH;
                        self.headerOperatorCallback(v, dId);
                    }
                });
            }
        });
        BI.each(this.crossDimIds, function(i, dId){
            if(BI.isNotNull(dId)) {
                self.crossHeader.push({
                    type: "bi.normal_header_cell",
                    dId: dId,
                    text: BI.Utils.getDimensionNameByID(dId),
                    sortFilterChange: function(v){
                        self.resetETree();
                        self.pageOperator = BICst.TABLE_PAGE_OPERATOR.REFRESH;
                        self.headerOperatorCallback(v, dId);
                    }
                });
            }
        });

        var targetsArray = [];
        BI.each(this.targetIds, function(i, tId){
            if(BI.isNotNull(tId)) {
                targetsArray.push({
                    type: "bi.page_table_cell",
                    text: BI.Utils.getDimensionNameByID(tId)
                });
            }
        });
        //根据crossItems创建部分header
        this._createCrossPartHeader(this.data.t.c);
        //创建合计部分的header
        if(this.showColTotal === true){
            BI.each(this.crossItemsSums[this.crossItemsSums.length - 1], function(i ,v){
                BI.each(targetsArray, function(j, targetCell){
                    self.header.push(BI.extend(targetCell, {tag: BI.UUID()}));
                });
            });
        }
    },

    /**
     * 交叉表——crossHeader
     */
    _createCrossPartHeader: function(c){
        var self = this;
        var dId = null;
        BI.each(c, function(i, child){
            if(BI.isNotNull(child.n) && BI.isNotNull(child.c)){
                self._createCrossPartHeader(child.c);
            } else {
                dId = child.n;
                //可能出现无指标的情况
                self.header.push({
                    type: "bi.page_table_cell",
                    text: self.targetIds.contains(child.n) ? BI.Utils.getDimensionNameByID(child.n) : "",
                    tag: BI.UUID()
                })
            }
        });
    },

    /**
     * 交叉表的(指标)汇总值
     */
    _createTableSumItems: function(s, sum){
        var self = this;
        BI.each(s, function(i, v){
            if(BI.isNotNull(v) && (BI.isNotNull(v.c) || BI.isNotNull(v.s))){
                self._createTableSumItems(v.c, sum);
                self._createTableSumItems(v.s, sum);
            } else {
                var tId = self.targetIds[i];
                sum.push({
                    type: "bi.target_body_normal_cell",
                    text: v,
                    dId: tId,
                    clicked: []
                });
            }
        });
    },

    _createGroupTableItems: function(){
        var self = this;
        var currentLayer = 0;
        var item = {
            children: this._createTableItems(this.data.c, currentLayer) || []
        };
        //汇总
        if(this.showRowTotal === true && BI.isNotEmptyArray(this.data.s)){
            var outerValues = [];
            if(this.dimIds.length > 0) {
                BI.each(this.data.s, function(i, v){
                    var tId = self.targetIds[i];
                    outerValues.push({
                        type: "bi.target_body_normal_cell",
                        text: v,
                        dId: tId
                    });
                });
                item.children.push({
                    type: "bi.page_table_cell",
                    text: BI.i18nText("BI-Summary_Values"),
                    tag: BI.UUID(),
                    isSum: true,
                    values: outerValues
                })
            } else {
                //使用第一个值作为一个维度
                BI.each(this.data.s, function(i, v){
                    if(i === 0) {
                        return;
                    }
                    var tId = self.targetIds[i];
                    outerValues.push({
                        type: "bi.target_body_normal_cell",
                        text: v,
                        dId: tId
                    });
                });
                item.children.push({
                    type: "bi.page_table_cell",
                    text: this.data.s[0],
                    tag: BI.UUID(),
                    isSum: true,
                    values: outerValues
                })
            }
        }
        this.items = [item];
    },

    /**
     * 交叉表 items and crossItems
     */
    _createCrossTableItems: function(){
        var self = this;
        var top = this.data.t, left = this.data.l;
        var item = {
            children: this._createTableItems(left.c, 0)
        };
        if(this.showRowTotal === true){
            //汇总值
            var sums = [];
            if(BI.isNotNull(left.s.c) && BI.isNotNull(left.s.s)){
                this._createTableSumItems(left.s.c, sums);
            } else {
                this._createTableSumItems(left.s, sums);
            }
            if(this.showColTotal === true) {
                var outerValues = [];
                BI.each(left.s.s, function(i, v){
                    var tId = self.targetIds[i];
                    outerValues.push({
                        type: "bi.target_body_normal_cell",
                        text: v,
                        dId: tId
                    });
                });
                sums = sums.concat(outerValues);
            }
            item.children.push({
                type: "bi.page_table_cell",
                text: BI.i18nText("BI-Summary_Values"),
                tag: BI.UUID(),
                isSum: true,
                values: sums
            })
        }

        this.items = [item];

        //根据所在的层，汇总情况——是否含有汇总
        this.crossItemsSums = [];
        this.crossItemsSums[0] = [];
        if(BI.isNotNull(left.s)){
            this.crossItemsSums[0].push(true);
        }
        this._initCrossItemsSum(0, left.c);

        //交叉表items
        var crossItem = {
            children: this._createCrossPartItems(top.c, 0)
        };
        if(this.showColTotal === true){
            BI.each(this.targetIds, function(i, tId){
                crossItem.children.push({
                    type: "bi.page_table_cell",
                    text: BI.i18nText("BI-Summary_Values"),
                    tag: BI.UUID(),
                    isSum: true
                });
            });
        }
        this.crossItems = [crossItem];
    },

    /**
     * 初始化 crossItemsSum
     */
    _initCrossItemsSum: function(currentLayer, sums){
        var self = this;
        currentLayer++;
        BI.each(sums, function(i, v){
            if(BI.isNotNull(v) && BI.isNotNull(v.c)){
                self._initCrossItemsSum(currentLayer, v.c);
            }
            BI.isNull(self.crossItemsSums[currentLayer]) && (self.crossItemsSums[currentLayer] = []);
            self.crossItemsSums[currentLayer].push(BI.isNotNull(v.s) ? true : false);
        });
    },

    /**
     * 交叉表——crossItems
     */
    _createCrossPartItems: function(c, currentLayer, parent){
        var self = this, crossHeaderItems = [];
        currentLayer++;
        BI.each(c, function(i, child){
            if(BI.isNull(child.c)){
                return;
            }
            var cId = BI.isEmptyString(child.n) ? self.EMPTY_VALUE : child.n;
            var currDid = self.dimIds[currentLayer - 1], currValue = child.n;
            var nodeId = BI.isNotNull(parent) ? parent.get("id") + cId : cId;
            var node = new BI.Node(nodeId);
            node.set("name", child.n);
            self.crossTree.addNode(parent, node);
            var item = {
                type: "bi.normal_expander_cell",
                text: currValue,
                dId: currDid,
                expandCallback: function(){
                    var clickNode = self.crossETree.search(nodeId);
                    //全部展开再收起——纵向
                    if(self.openColNode === true) {
                        self._addNode2crossETree4OpenColNode(nodeId);
                    } else {
                        if(BI.isNull(clickNode)){
                            self.crossETree.addNode(self.crossETree.search(BI.isNull(parent) ? self.crossTree.getRoot().get("id") : parent.get("id")), BI.deepClone(node));
                        } else {
                            clickNode.getParent().removeChild(nodeId);
                        }
                    }
                    self.pageOperator = BICst.TABLE_PAGE_OPERATOR.EXPAND;
                    self.clickValue = child.n;
                    self.expanderCallback();
                },
                drillCallback: function() {

                }
            };
            if(currentLayer < self.crossDimIds.length) {
                item.needExpand = true;
                item.isExpanded = false;
            }
            if(BI.isNotNull(child.c)){
                var children = self._createCrossPartItems(child.c, currentLayer, node);
                if(BI.isNotEmptyArray(children)){
                    item.children = self._createCrossPartItems(child.c, currentLayer, node);
                    item.isExpanded = true;
                }
            }
            var hasSum = false;
            if(BI.isNotNull(self.crossItemsSums[currentLayer]) && self.crossItemsSums[currentLayer][i] === true){
                hasSum = true;
            }
            if(hasSum === true && self.showColTotal === true && BI.isNotEmptyArray(item.children)){
                BI.each(self.targetIds, function(k, tId){
                    item.children.push({
                        type: "bi.page_table_cell",
                        text: BI.i18nText("BI-Summary_Values"),
                        tag: BI.UUID(),
                        isSum: true
                    });
                });
            }
            if(currentLayer < self.crossDimIds.length || BI.isEmptyArray(self.targetIds)){
                crossHeaderItems.push(item);
            } else {
                //需要push多次(存在没有指标的情况)
                BI.each(self.targetIds, function(i, tId){
                    crossHeaderItems.push(item);
                })
            }
        });
        return crossHeaderItems;
    },

    _setOtherGroupAttrs: function(){
        var self = this;
        //冻结列
        this.freezeCols = [];
        //合并列，列大小
        this.mergeCols = [];
        BI.each(this.dimIds, function(i, id){
            self.mergeCols.push(i);
            self.freezeCols.push(i);
        });
        this.showNumber === true && this.freezeCols.push(this.freezeCols.length);
        var dtIds = this.dimIds.concat(this.targetIds);
        if(this.columnSize.length !== dtIds.length){
            //重置列宽
            this.columnSize = [];
            BI.each(dtIds, function(i, id){
                self.columnSize.push("");
            });
        }
    },

    _setOtherCrossAttrs: function(){
        var self = this;
        //冻结列
        this.freezeCols = [];
        //合并列，列大小
        var cSize = [];
        BI.each(this.dimIds, function(i, id){
            self.mergeCols.push(i);
            self.freezeCols.push(i);
        });
        this.showNumber === true && this.freezeCols.push(this.freezeCols.length);
        BI.each(this.header, function(i, id){
            cSize.push("");
        });
        if(this.columnSize.length !== cSize.length){
            //重置列宽
            this.columnSize = [];
            BI.each(cSize, function(i, id){
                self.columnSize.push("");
            });
        }
    },

    createGroupTableAttrs: function(){
        //几个回调
        this.headerOperatorCallback = arguments[0];
        this.expanderCallback = arguments[1];
        this.clickedCallback = arguments[2];

        this._resetPartAttrs();
        this._refreshDimsInfo();

        //header
        this._createGroupTableHeader();

        //items
        this._createGroupTableItems();

        //others
        this._setOtherGroupAttrs();
    },

    createCrossTableAttrs: function(){
        this.headerOperatorCallback = arguments[0];
        this.expanderCallback = arguments[1];
        this.clickedCallback = arguments[2];

        this._resetPartAttrs();
        this._refreshDimsInfo();

        //items
        this._createCrossTableItems();

        //header
        this._createCrossTableHeader();

        this._setOtherCrossAttrs();
    }



});
