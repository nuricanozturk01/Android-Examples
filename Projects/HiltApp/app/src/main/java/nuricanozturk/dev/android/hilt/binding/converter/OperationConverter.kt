package nuricanozturk.dev.android.hilt.binding.converter

import androidx.databinding.InverseMethod
import java.lang.NumberFormatException

object OperationConverter {

    @InverseMethod("toStr")
    fun toInt(str : String) : Char = if (str.length != 1)  '+' else str[0]
    fun toStr(op: Char) = op.toString()
}