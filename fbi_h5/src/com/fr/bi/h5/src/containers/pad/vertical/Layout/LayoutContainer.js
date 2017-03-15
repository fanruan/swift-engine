import mixin from 'react-mixin'
import ReactDOM from 'react-dom'
import {requestAnimationFrame, ReactComponentWithImmutableRenderMixin} from 'core'
import React, {
    Component,
    StyleSheet,
    Text,
    Dimensions,
    ListView,
    ViewPagerAndroid,
    View,
    Fetch
} from 'lib'
import {Layout} from 'layout'

import {AutoSizer} from 'base'
import {Colors, TemplateFactory, WidgetFactory} from 'data'

import ChartPaneComponent from '../../common/Chart/ChartContainer.js'
import TablePaneComponent from '../../common/Table/TableContainer.js'
import DetailTablePaneComponent from '../../common/DetailTable/DetailTableContainer.js'
import MultiSelectorComponent from '../../../../components/MultiSelector/MultiSelectorComponent.js'
import MultiTreeSelectorComponent from '../../../../components/MultiTreeSelector/MultiTreeSelectorComponent.js'
import ContentComponent from '../../../../components/Content/ContentComponent'
import ImageComponent from '../../../../components/Image/ImageComponent'
import YearMonthComponent from '../../../../components/YearMonth/YearMonthComponent'
import YearComponent from '../../../../components/Year/YearComponent'
import YearQuarterComponent from '../../../../components/YearQuarter/YearQuarterComponent'
import WebComponent from '../../../../components/Web/WebCompontent'

import LayoutContainerHelper from './LayoutContainerHelper'

class LayoutContainer extends Component {
    static contextTypes = {
        actions: React.PropTypes.object,
        $template: React.PropTypes.object
    };
    static propTypes = {};

    constructor(props, context) {
        super(props, context);
    }

    _onPageScroll() {

    }

    _onPageSelected() {

    }

    render() {
        const {...props} = this.props;
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this._helper = new LayoutContainerHelper(props);

        const rows = this._helper.getAllSortedStatisticWidgetIds();
        return <ListView
            contentContainerStyle={styles.list}
            {...props}
            pageSize={2}
            initialListSize={(Math.floor(props.height / 310) + 1) * 2}
            dataSource={ds.cloneWithRows(rows)}
            renderRow={this._renderRow.bind(this)}
        />;
    }

    _renderRow(wId, sectionID, rowID) {
        const $widget = this._helper.get$WidgetByWidgetId(wId);
        const type = this._helper.getWidgetTypeByWidgetId(wId);
        const width = this.props.width / 2 - 20, height = 270;
        const props = {
            $widget,
            $template: this._helper.get$Template(),
            wId,
            width: width,
            height: height,
            onValueChange: ($template)=> {
                this.context.actions.query($template)
            }
        };
        let component = null;
        switch (type) {
            case BICst.WIDGET.TABLE:
                component = <TablePaneComponent {...props} />;
                break;
            //case BICst.WIDGET.CROSS_TABLE:
            //case BICst.WIDGET.COMPLEX_TABLE:
            //
            case BICst.WIDGET.DETAIL:
                component = <DetailTablePaneComponent {...props} />;
                break;

            case BICst.WIDGET.AXIS:
            case BICst.WIDGET.ACCUMULATE_COLUMN:
            case BICst.WIDGET.PERCENT_ACCUMULATE_COLUMN:
            case BICst.WIDGET.COMPARE_COLUMN:
            case BICst.WIDGET.FALL_COLUMN:
            case BICst.WIDGET.BAR:
            case BICst.WIDGET.ACCUMULATE_BAR:
            case BICst.WIDGET.COMPARE_BAR:
            case BICst.WIDGET.LINE:
            case BICst.WIDGET.AREA:
            case BICst.WIDGET.ACCUMULATE_AREA:
            case BICst.WIDGET.COMPARE_AREA:
            case BICst.WIDGET.RANGE_AREA:
            case BICst.WIDGET.COMBINE_CHART:
            case BICst.WIDGET.MULTI_AXIS_COMBINE_CHART:
            case BICst.WIDGET.PIE :
            case BICst.WIDGET.DONUT :
            case BICst.WIDGET.MAP:
            case BICst.WIDGET.GIS_MAP:
            case BICst.WIDGET.GAUGE:
            case BICst.WIDGET.BUBBLE:
            case BICst.WIDGET.FORCE_BUBBLE:
            case BICst.WIDGET.SCATTER:
            case BICst.WIDGET.RADAR:
            case BICst.WIDGET.ACCUMULATE_RADAR:
            case BICst.WIDGET.FUNNEL:
                component = <ChartPaneComponent {...props} />;
                break;
            case BICst.WIDGET.NUMBER:
            case BICst.WIDGET.DATE:
            case BICst.WIDGET.YEAR:
            case BICst.WIDGET.QUARTER:
            case BICst.WIDGET.MONTH:
                component = <YearMonthComponent {...props} />;
                break;
            case BICst.WIDGET.YMD:
            case BICst.WIDGET.QUERY:
            case BICst.WIDGET.RESET:
                break;
            case BICst.WIDGET.CONTENT:
                component = <ContentComponent {...props} />;
                break;
            case BICst.WIDGET.IMAGE:
                component = <ImageComponent {...props}/>;
                break;
            case BICst.WIDGET.WEB:
                component = <WebComponent {...props}/>;
                break;
            case BICst.WIDGET.STRING:
                component = <MultiSelectorComponent {...props} />;
                break;
            case BICst.WIDGET.TREE:
                component = <MultiTreeSelectorComponent {...props} />;
                break;
            default:
                break;
        }
        return <Layout flex box='mean' style={[styles.wrapper, {width: this.props.width / 2, height}]}>
            {component}
        </Layout>
    }
}
mixin.onClass(LayoutContainer, ReactComponentWithImmutableRenderMixin);
const styles = StyleSheet.create({
    wrapper: {
        padding: 10
    },
    list: {
        justifyContent: 'space-around',
        flexDirection: 'row',
        flexWrap: 'wrap'
    },
    viewPager: {
        flex: 1
    }
});
export default LayoutContainer
