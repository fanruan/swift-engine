import {isNil} from 'core'
class Dimension {
    constructor($dimension, dId, widget) {
        this.$dimension = $dimension;
        this.dId = dId;
        this.widget = widget;
    }

    $get() {
        return this.$dimension;
    }

    getType() {
        return this.$dimension.get('type');
    }

    getName() {
        return this.$dimension.get('name');
    }

    isUsed() {
        return this.$dimension.get('used');
    }

    getSortTarget() {
        return this.$dimension.getIn(['sort', 'sort_target']);
    }

    getSortTargetName() {
        const $sort = this.$dimension.get('sort');
        if ($sort) {
            const sort_target = $sort.get('sort_target');
            if (sort_target) {
                return this.widget.getDimensionOrTargetById(sort_target).getName();
            }
        }
        return this.getName();
    }

    getSortType() {
        return this.$dimension.getIn(['sort', 'type']) || BICst.SORT.ASC;
    }

    setUsed(b) {
        this.$dimension = this.$dimension.set('used', !!b);
        return this;
    }

    setSortType(type) {
        this.$dimension = this.$dimension.setIn(['sort', 'type'], type);
        return this;
    }

    setSortTarget(dId) {
        this.$dimension = this.$dimension.setIn(['sort', 'sort_target'], dId);
        return this;
    }

    getGroupType() {
        return this.$dimension.getIn(['group', 'type']);
    }

    getGroup() {
        const $group = this.$dimension.get('group');
        return isNil($group) ? {} : $group.toJS();
    }

    setFilterValue(filterValue) {
        this.$dimension = this.$dimension.setIn(['filter_value'], filterValue);
        return this;
    }

    getFilterValue() {
        const $filter = this.$dimension.get('filter_value');
        return isNil($filter) ? {} : $filter.toJS();
    }

    getFieldId() {
        return this.$dimension.getIn(['_src', 'field_id']);
    }

    getDimensionSrc() {
        const $src = this.$dimension.get('_src');
        return isNil($src) ? {} : $src.toJS();
    }
}
export default Dimension