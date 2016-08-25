;
!(function(){
    BI.NumberLessThanFilterValue = function(){

    };
    BI.NumberLessThanFilterValue.prototype = {
        constructor: BI.NumberLessThanFilterValue,

        getNumberAvg: function(array){
            if(array.length === 0){
                return;
            }
            var sum = 0;
            BI.each(array, function(idx, num){
                sum = BI.parseFloat(sum.add(num));
            });
            return BI.parseFloat(sum.div(array.length));
        },

        getFilterResult: function(array) {
            var avgValue = this.getNumberAvg(array);
            return BI.filter(array, function(idx, val){
                return val < avgValue;
            });
        }
    }
})();