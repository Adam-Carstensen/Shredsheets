package com.bm.automik.alpha

import com.bm.automik.MatrixModel
import com.bm.common.Size

class AlphaMatrixModel(size: Size, var alphaThreshold: Float, var map: Array<FloatArray>) : MatrixModel(size, "Alpha")