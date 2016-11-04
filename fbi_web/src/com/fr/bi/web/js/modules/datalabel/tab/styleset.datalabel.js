/**
 * Created by fay on 2016/7/25.
 */
BI.DataLabelStyleSet = BI.inherit(BI.Widget, {
    _constant: {
        ICON_WIDTH: 30,
        ICON_HEIGHT: 30,
        BUTTON_HEIGHT: 40,
        TRIGGER_BUTTON_WIDTH: 50,
        TRIGGER_ICON_WIDTH: 12,
        TRIGGER_WIDTH: 62
    },

    _defaultConfig: function () {
        return BI.extend(BI.DataLabelStyleSet.superclass._defaultConfig.apply(this, arguments), {});
    },

    _init: function () {
        BI.DataLabelStyleSet.superclass._init.apply(this, arguments);
        var self = this, o = this.options;
        this.textTrigger = BI.createWidget({
            type: "bi.label",
            text: BI.i18nText("BI-Set_Style"),
            height: this._constant.BUTTON_HEIGHT
        });
        this.imgTrigger = BI.createWidget({
            type: "bi.image_button",
            iconWidth: this._constant.ICON_WIDTH,
            iconHeight: this._constant.ICON_HEIGHT,
            height: this._constant.BUTTON_HEIGHT
        });
        this.styleTab = BI.createWidget({
            type: "bi.data_label_tab",
            chartType: o.chartType,
            dId: o.sdId
        });
        this.styleTab.on(BI.DataLabelTab.IMG_CHANGE, function () {
            self.style.hideView();
        });
        this.imgTrigger.setVisible(false);
        this.triggerButton = BI.createWidget({
            type: "bi.center_adapt",
            items: [this.textTrigger, this.imgTrigger]
        });
        this.triggerIcon = BI.createWidget({
            type: "bi.trigger_icon_button",
            cls: "trigger-icon"
        });
        this.styleTrigger = BI.createWidget({
            type: "bi.htape",
            cls: "condition-trigger",
            items: [{
                el: this.triggerButton,
                width: this._constant.TRIGGER_BUTTON_WIDTH
            }, {
                el: this.triggerIcon,
                width: this._constant.TRIGGER_ICON_WIDTH
            }],
            width: this._constant.TRIGGER_WIDTH,
            height: "100%"
        });
        this.style = BI.createWidget({
            type: "bi.combo",
            isNeedAdjustWidth: false,
            isNeedAdjustHeight: false,
            element: this.element,
            el: this.styleTrigger,
            popup: {
                el: this.styleTab
            },
            direction: "bottom,left",
            offsetStyle: "right",
            height: "100%"
        });
        this.style.on(BI.Combo.EVENT_BEFORE_POPUPVIEW, function () {
            if(self.imgTrigger.isVisible()) {
                self.styleTab.setImageSetSelected();
            }
        });
        this.style.on(BI.Combo.EVENT_AFTER_POPUPVIEW, function () {
            self.styleTab.populate();
        });
        this.style.on(BI.Combo.EVENT_AFTER_HIDEVIEW, function () {
            var style = self.styleTab.getValue();
            switch (style.type) {
                case BICst.DATA_LABEL_STYLE_TYPE.TEXT:
                    self.textTrigger.setValue("text");
                    self.textTrigger.setStyle(style.textStyle);
                    self.imgTrigger.setVisible(false);
                    self.textTrigger.setVisible(true);
                    break;
                case BICst.DATA_LABEL_STYLE_TYPE.IMG:
                    self.imgTrigger.setSrc(BI.Func.getCompleteImageUrl(style.imgStyle.src));
                    self.imgTrigger.setVisible(true);
                    self.textTrigger.setVisible(false);
            }
        });
    },

    _checkStyle: function (v) {
        switch (v.type) {
            case BICst.DATA_LABEL_STYLE_TYPE.TEXT:
                this.textTrigger.setValue("text");
                this.textTrigger.setStyle(v.textStyle);
                break;
            case BICst.DATA_LABEL_STYLE_TYPE.IMG:
                this.imgTrigger.setSrc(BI.Func.getCompleteImageUrl(v.imgStyle.src));
                this.imgTrigger.setVisible(true);
                this.textTrigger.setVisible(false);
        }
    },

    setValue: function (v) {
        this._checkStyle(v);
        this.styleTab.setValue(v);
    },

    getValue: function () {
        return this.styleTab.getValue();
    }
});

$.shortcut("bi.data_label_style_set", BI.DataLabelStyleSet);