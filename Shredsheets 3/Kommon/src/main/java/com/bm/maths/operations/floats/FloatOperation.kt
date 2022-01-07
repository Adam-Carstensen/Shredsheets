package com.bm.maths.operations.floats

import com.bm.maths.operations.Operation

abstract class FloatOperation : Operation<Float>, IFloatOperation {
    constructor(b: Float) : super(b)
}
