package com.kylecorry.trail_sense.tools.backtrack.domain.pathsort

import com.kylecorry.sol.units.Coordinate
import com.kylecorry.trail_sense.shared.paths.Path

class ClosestPathSortStrategy(private val location: Coordinate) : IPathSortStrategy {
    override fun sort(paths: List<Path>): List<Path> {
        return paths.sortedBy { it.metadata.bounds.center.distanceTo(location) }
    }
}