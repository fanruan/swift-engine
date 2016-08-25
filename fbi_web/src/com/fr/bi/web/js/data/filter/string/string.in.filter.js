;
!(function(){
    BI.StringInFilterValue = function(param){
        param = param || {};
        this.type = param.type || BI.Selection.Multi;
        this.valueSet = param.value || [];
    };
    BI.StringInFilterValue.prototype = {
        constructor: BI.StringInFilterValue,

        isStringIn: function(value){
            if(BI.isNull(this.valueSet) || BI.isEmptyArray(this.valueSet)){
                return true;
            }
            return BI.contains(this.valueSet, value);
        },

        isAllSelect: function(){
            return this.type === BI.Selection.All;
        },

        getFilterResult: function(array) {
            var self = this;
            return BI.filter(array, function(idx, val){
                return self.isAllSelect() ? !self.isStringIn(val) : self.isStringIn(val);
            });
        }
    }
})();
