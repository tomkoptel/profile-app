@file:JvmName("MotionLayoutExt")

package androidx.constraintlayout.motion.widget

import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintSet

inline fun MotionLayout.updateStateForConstraintInSet(
    @IdRes stateId: Int,
    @IdRes constraintId: Int,
    configure: (ConstraintSet.Constraint) -> Unit
) {
    val constraintSet = getConstraintSet(stateId)
    val newConstraint = constraintSet.getConstraint(constraintId)
    configure(newConstraint)
    updateState(stateId, constraintSet)
}

fun MotionLayout.transitionListener(
    onTransitionTrigger: (MotionLayout, Int, Boolean, Float) -> Unit = { _, _, _, _ -> },
    onTransitionStarted: (MotionLayout, Int, Int) -> Unit = { _, _, _ -> },
    onTransitionChange: (MotionLayout, Int, Int, Float) -> Unit = { _, _, _, _ -> },
    onTransitionCompleted: (MotionLayout, Int) -> Unit = { _, _ -> }
) {
    this.setTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionTrigger(layout: MotionLayout, startId: Int, something: Boolean, progress: Float) {
            onTransitionTrigger(layout, startId, something, progress)
        }

        override fun onTransitionStarted(layout: MotionLayout, startId: Int, endId: Int) {
            onTransitionStarted(layout, startId, endId)
        }

        override fun onTransitionChange(layout: MotionLayout, startId: Int, endId: Int, progress: Float) {
            onTransitionChange(layout, startId, endId, progress)
        }

        override fun onTransitionCompleted(layout: MotionLayout, currentId: Int) {
            onTransitionCompleted(layout, currentId)
        }
    })
}
