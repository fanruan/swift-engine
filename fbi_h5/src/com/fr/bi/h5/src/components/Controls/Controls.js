import mixin from 'react-mixin'
import ReactDOM from 'react-dom'

import {
    ReactComponentWithImmutableRenderMixin,
    cn,
    sc,
    isNil,
    requestAnimationFrame,
    emptyFunction,
    shallowEqual,
    isEqual,
    each,
    map
} from 'core'
import React, {
    Component,
    StyleSheet,
    Text,
    Dimensions,
    PixelRatio,
    ListView,
    View,
    ScrollView,
    Fetch,
    Promise
} from 'lib'

import {Colors, Template, Widget} from 'data'

import {Icon, Table, AutoSizer, VtapeLayout, HtapeLayout, VerticalCenterLayout, CenterLayout} from 'base'

import {MultiSelectorWidget} from 'widgets'


import MultiSelectorComponent from '../MultiSelector/MultiSelectorComponent.js'
import MultiTreeSelectorComponent from '../MultiTreeSelector/MultiTreeSelectorComponent.js'

import Item from './Item'

class Controls extends Component {

    constructor(props, context) {
        super(props, context);
    }

    static propTypes = {};

    state = {};

    componentWillMount() {

    }

    componentDidMount() {

    }

    componentWillReceiveProps() {

    }

    componentWillUpdate() {

    }

    render() {
        const {...props} = this.props;
        this.template = new Template(props.$template);
        return <ScrollView style={styles.wrapper}>
            {map(this.template.getAllControlWidgetIds(), (wId)=> {
                const $widget = this.template.get$$WidgetById(wId);
                const widget = new Widget($widget);
                return <Item key={wId} id={wId} $widget={$widget} onPress={()=> {
                    let Component = null;
                    switch (widget.getType()) {
                        case BICst.WIDGET.STRING:
                            Component = MultiSelectorComponent;
                            break;
                        case BICst.WIDGET.NUMBER:
                        case BICst.WIDGET.TREE:
                            Component = MultiTreeSelectorComponent;
                            break;
                        case BICst.WIDGET.DATE:
                        case BICst.WIDGET.YEAR:
                        case BICst.WIDGET.QUARTER:
                        case BICst.WIDGET.MONTH:
                        case BICst.WIDGET.YMD:
                    }
                    props.navigator.push({
                        ...props,
                        name: 'widget',
                        wId,
                        Component: Component,
                        title: widget.getName()
                    });
                }}/>
            })}
        </ScrollView>
    }

    // render() {
    //     const {...props} = this.props;
    //     return <VtapeLayout style={styles.wrapper}>
    //         <HtapeLayout style={styles.title} height={44}>
    //             <VerticalCenterLayout width={'auto'}><Text onPress={props.onReturn}>返回</Text></VerticalCenterLayout>
    //             <CenterLayout><Text>参数查询</Text></CenterLayout>
    //             <VerticalCenterLayout width={'auto'}><Text>查询</Text></VerticalCenterLayout>
    //         </HtapeLayout>
    //         <ScrollView>
    //             {map(props.template.getAllControlWidgetIds(), (id)=> {
    //                 return <Item id={id} widget={props.template.getWidgetById(id)}/>
    //             })}
    //         </ScrollView>
    //     </VtapeLayout>
    // }
}
mixin.onClass(Controls, ReactComponentWithImmutableRenderMixin);
const styles = StyleSheet.create({
    wrapper: {
        flex: 1
    },
    title: {
        justifyContent: 'space-between',
        padding: '0 4px',
        flexDirection: 'row',
        borderBottom: '1px solid ' + Colors.BORDER
    },
    back: {}
});
export default Controls
