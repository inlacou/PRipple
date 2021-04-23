package com.inlacou.pripple

interface BatchEditable {

    var batchEditing: Boolean
    /**
     * Start batch.
     * For example, set batchEditing to true.
     */
    fun beginBatch() {
        batchEditing = true
    }
    /**
     * Alias for @see BatchEditable.beginBatch()
     */
    fun startBatch() = beginBatch()

    /**
     * End batch and apply it.
     * For example, update view and set batchEditing to false.
     */
    fun applyBatch()

    /**
     * End batch editing and do not apply it.
     * For example, discard modified transitive data and set batchEditing to false.
     */
    fun dropBatch() {
        batchEditing = false
    }
    /**
     * Alias for @see BatchEditable.dropBatch()
     */
    fun endBatch() = dropBatch()
}