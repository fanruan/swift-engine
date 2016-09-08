/** @flow */
import React from 'react'
import cn from 'classnames'
import {View} from 'lib'
import {translateDOMPositionXY} from 'core'

/**
 * Default implementation of cellRangeRenderer used by Grid.
 * This renderer supports cell-caching while the user is scrolling.
 */
export default function defaultCellRangeRenderer({
    cellCache,
    cellClassName,
    cellRenderer,
    cellStyle,
    columnSizeAndPositionManager,
    columnStartIndex,
    columnStopIndex,
    horizontalOffsetAdjustment,
    isScrolling,
    rowSizeAndPositionManager,
    rowStartIndex,
    rowStopIndex,
    scrollLeft,
    scrollTop,
    verticalOffsetAdjustment
}) {
    const renderedCells = []

    for (let rowIndex = rowStartIndex; rowIndex <= rowStopIndex; rowIndex++) {
        let rowDatum = rowSizeAndPositionManager.getSizeAndPositionOfCell(rowIndex)

        for (let columnIndex = columnStartIndex; columnIndex <= columnStopIndex; columnIndex++) {
            let columnDatum = columnSizeAndPositionManager.getSizeAndPositionOfCell(columnIndex)
            let key = `${rowIndex}-${columnIndex}`
            let cellStyleObject = cellStyle({rowIndex, columnIndex})
            let renderedCell

            // Avoid re-creating cells while scrolling.
            // This can lead to the same cell being created many times and can cause performance issues for "heavy" cells.
            // If a scroll is in progress- cache and reuse cells.
            // This cache will be thrown away once scrolling complets.
            if (isScrolling) {
                if (!cellCache[key]) {
                    cellCache[key] = cellRenderer({
                        columnIndex,
                        isScrolling,
                        rowIndex
                    })
                }
                renderedCell = cellCache[key]
                // If the user is no longer scrolling, don't cache cells.
                // This makes dynamic cell content difficult for users and would also lead to a heavier memory footprint.
            } else {
                renderedCell = cellRenderer({
                    columnIndex,
                    isScrolling,
                    rowIndex
                })
            }

            if (renderedCell == null || renderedCell === false) {
                continue
            }

            const className = cellClassName({columnIndex, rowIndex})

            const pos = {};
            translateDOMPositionXY(pos, columnDatum.offset + horizontalOffsetAdjustment, rowDatum.offset + verticalOffsetAdjustment);
            let child = (
                <View
                    key={key}
                    className={cn('Grid-cell', className)}
                    style={{
                        position: 'absolute',
                        height: rowDatum.size,
                        width: columnDatum.size,
                        ...pos,
                        ...cellStyleObject
                    }}
                >
                    {renderedCell}
                </View>
            )

            renderedCells.push(child)
        }
    }

    return renderedCells
}
