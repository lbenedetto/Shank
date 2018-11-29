package com.lbenedetto.shank


import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import android.util.SparseArray
import java.io.*

object UniversalBundle {
    fun fromBundle(bundle: Bundle?): Array<Any?> {
        val mBundle = bundle ?: Bundle()
        val numParameters = mBundle.getInt("UniversalBundleNumArgs")
        val weirdIndexes = mBundle.getIntegerArrayList("UniversalBundleWeirdArgs")!!
        val out = arrayOfNulls<Any>(numParameters)
        for (i in 0..numParameters) {
            if (weirdIndexes.contains(i)) {
                out[i] = mBundle.getOther(i.key())
            } else {
                out[i] = mBundle.get(i.key())
            }
        }
        return out
    }

    @Suppress("UNCHECKED_CAST")
    fun makeBundle(vararg any: Any?): Bundle {
        val bundle = Bundle()
        var i = 0
        val weirdIndexes = ArrayList<Int>()
        any.forEach { obj ->
            if(obj == null){
                i++
                return@forEach
            }
            val key = i.key()
            when (obj) {
                is Byte -> bundle.putByte(key, obj)
                is Boolean -> bundle.putBoolean(key, obj)
                is Short -> bundle.putShort(key, obj)
                is Int -> bundle.putInt(key, obj)
                is Float -> bundle.putFloat(key, obj)
                is Long -> bundle.putLong(key, obj)
                is Char -> bundle.putChar(key, obj)
                is String -> bundle.putString(key, obj)
                is ByteArray -> bundle.putByteArray(key, obj)
                is BooleanArray -> bundle.putBooleanArray(key, obj)
                is ShortArray -> bundle.putShortArray(key, obj)
                is IntArray -> bundle.putIntArray(key, obj)
                is FloatArray -> bundle.putFloatArray(key, obj)
                is LongArray -> bundle.putLongArray(key, obj)
                is CharArray -> bundle.putCharArray(key, obj)
                is CharSequence -> bundle.putCharSequence(key, obj)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && obj is IBinder -> bundle.putBinder(key, obj as IBinder)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && obj is Size -> bundle.putSize(key, obj as Size)
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && obj is SizeF -> bundle.putSizeF(key, obj as SizeF)
                is Array<*> -> when (obj) {
                    obj.isArrayOf<CharSequence>() -> bundle.putCharSequenceArray(key, obj as Array<CharSequence>)
                    obj.isArrayOf<String>() -> bundle.putStringArray(key, obj as Array<String>)
                    obj.isArrayOf<Parcelable>() -> bundle.putParcelableArray(key, obj as Array<Parcelable>)
                    else -> {
                        bundle.putOther(key, obj)
                        weirdIndexes.add(i)
                    }
                }
                is ArrayList<*> -> {
                    if (obj.isEmpty()) {
                        //Its empty, so its type doesn't matter
                        bundle.putParcelableArrayList(key, obj as ArrayList<Parcelable>)
                    } else {
                        when (obj[0]) {
                            is CharSequence -> bundle.putCharSequenceArrayList(key, obj as ArrayList<CharSequence>)
                            is String -> bundle.putStringArrayList(key, obj as ArrayList<String>)
                            is Int -> bundle.putIntegerArrayList(key, obj as ArrayList<Int>)
                            is Parcelable -> bundle.putParcelableArrayList(key, obj as ArrayList<Parcelable>)
                            else -> {
                                bundle.putOther(key, obj)
                                weirdIndexes.add(i)
                            }
                        }
                    }
                }
                is SparseArray<*> -> {
                    if (obj.size() == 0) {
                        //Its empty, so its type doesn't matter
                        bundle.putSparseParcelableArray(key, obj as SparseArray<Parcelable>)
                    } else {
                        when (obj[0]) {
                            is Parcelable -> bundle.putSparseParcelableArray(key, obj as SparseArray<Parcelable>)
                            else -> {
                                bundle.putOther(key, obj)
                                weirdIndexes.add(i)
                            }
                        }
                    }
                }

                is Bundle -> bundle.putBundle(key, obj)
                is Parcelable -> bundle.putParcelable(key, obj)
                is Serializable -> bundle.putSerializable(key, obj)
                else -> {
                    bundle.putOther(key, obj)
                    weirdIndexes.add(i)
                }
            }
            i++
        }
        bundle.putInt("UniversalBundleNumArgs", i)
        bundle.putIntegerArrayList("UniversalBundleWeirdArgs", weirdIndexes)
        return bundle
    }

    private fun Int.key(): String {
        return "UniversalBundleValue" + this
    }

    private fun Bundle.putOther(key: String, any: Any) {
        this.putByteArray(key, any.toByteArray())
    }

    private fun Bundle.getOther(key: String): Any? {
        return this.getByteArray(key)?.toAny()
    }

    private fun Any.toByteArray(): ByteArray {
        val baos = ByteArrayOutputStream()
        val oos = ObjectOutputStream(baos)
        oos.writeObject(this)
        oos.close()
        return baos.toByteArray()
    }

    private fun ByteArray.toAny(): Any {
        val ois = ObjectInputStream(ByteArrayInputStream(this))
        val any = ois.readObject()
        ois.close()
        return any
    }
}