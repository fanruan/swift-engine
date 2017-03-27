/**
 * Created by 小灰灰 on 2016/3/31.
 */
BI.AnalysisETLOperatorAddColumnValueSingleController = BI.inherit(BI.MVCController, {
    _checkCanSave : function (widget, model) {
        if (BI.isNull(model.get('v'))){
            widget.fireEvent(BI.TopPointerSavePane.EVENT_CHECK_SAVE_STATUS, false, BI.i18nText('BI-Value_Cannot_Be_Null'));
        } else {
            widget.fireEvent(BI.TopPointerSavePane.EVENT_CHECK_SAVE_STATUS,  this.valid);
        }
    },

    setEditorValid : function (v) {
        this.valid =  v;
    },

    checkValid : function (widget, model) {
        this._checkCanSave(widget, model)
    },

    setValue : function (value, widget, model) {
        model.set('v', value);
        this._checkCanSave(widget, model);
    },

    populate : function (widget, model) {
        var value = model.get('v') || "";
        model.set('v', value);
        widget.createEditor(this.options.fieldType, value)
        this._checkCanSave(widget, model)
    },

    changeFieldType : function (fieldType, widget, model) {
        this.options.fieldType = fieldType;
        this.populate(widget, model)
    }
})