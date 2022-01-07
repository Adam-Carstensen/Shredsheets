package com.bm.automik.hsl

import com.bm.automik.MatrixModel
import com.bm.common.Size

class HSLMatrixModel(size: Size, matrixName: String, val lightnessAlphaThreshold: Float = 0.5f) : MatrixModel(size, matrixName)