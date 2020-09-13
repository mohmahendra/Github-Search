package mohamad.mahendra.mohamad_mahendra_github_search.listener

import android.text.Editable
import android.text.TextWatcher

abstract class EditTextListener() : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        if (s.isEmpty()) {
            clearList()
        }
        removeCallback()
    }

    override fun afterTextChanged(s: Editable) {
        if (s.isNotEmpty()) {
            queryText()
        }
        else {
            clearList()
        }
    }

    abstract fun clearList()

    abstract fun removeCallback()

    abstract fun queryText()
}