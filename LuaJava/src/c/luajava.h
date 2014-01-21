/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_keplerproject_luajava_LuaState */

#ifndef _Included_org_keplerproject_luajava_LuaState
#define _Included_org_keplerproject_luajava_LuaState
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _open
 * Signature: ()Lorg/keplerproject/luajava/CPtr;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1open
  (JNIEnv *, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _close
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1close
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _newthread
 * Signature: (Lorg/keplerproject/luajava/CPtr;)Lorg/keplerproject/luajava/CPtr;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1newthread
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getTop
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1getTop
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setTop
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setTop
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushValue
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushValue
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _remove
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1remove
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _insert
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1insert
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _replace
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1replace
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _checkStack
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1checkStack
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _xmove
 * Signature: (Lorg/keplerproject/luajava/CPtr;Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1xmove
  (JNIEnv *, jobject, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNumber
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isString
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isString
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isCFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isCFunction
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isUserdata
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isUserdata
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _type
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1type
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _typeName
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1typeName
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _equal
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1equal
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawequal
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1rawequal
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _lessthan
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1lessthan
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)D
 */
JNIEXPORT jdouble JNICALL Java_org_keplerproject_luajava_LuaState__1toNumber
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1toInteger
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toBoolean
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1toBoolean
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toString
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1toString
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _objlen
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1objlen
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _toThread
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Lorg/keplerproject/luajava/CPtr;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1toThread
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushNil
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushNil
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;D)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushNumber
  (JNIEnv *, jobject, jobject, jdouble);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushInteger
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushString
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushString__Lorg_keplerproject_luajava_CPtr_2Ljava_lang_String_2
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushString
 * Signature: (Lorg/keplerproject/luajava/CPtr;[BI)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushString__Lorg_keplerproject_luajava_CPtr_2_3BI
  (JNIEnv *, jobject, jobject, jbyteArray, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushBoolean
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushBoolean
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getField
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getField
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawGet
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawGet
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawGetI
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawGetI
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _createTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1createTable
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getMetaTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1getMetaTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getFEnv
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getFEnv
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setField
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setField
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawSet
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawSet
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _rawSetI
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1rawSetI
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setMetaTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1setMetaTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setFEnv
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1setFEnv
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _call
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1call
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pcall
 * Signature: (Lorg/keplerproject/luajava/CPtr;III)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1pcall
  (JNIEnv *, jobject, jobject, jint, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _yield
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1yield
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _resume
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1resume
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _status
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1status
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _gc
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1gc
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _error
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1error
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _next
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1next
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _concat
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1concat
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pop
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pop
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _newTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1newTable
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _strlen
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1strlen
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isFunction
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isTable
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNil
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNil
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isBoolean
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isBoolean
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isThread
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isThread
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNone
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNone
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isNoneOrNil
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1isNoneOrNil
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _setGlobal
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1setGlobal
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getGlobal
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1getGlobal
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getGcCount
 * Signature: (Lorg/keplerproject/luajava/CPtr;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1getGcCount
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LdoFile
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LdoFile
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LdoString
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LdoString
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LgetMetaField
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LgetMetaField
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcallMeta
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LcallMeta
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Ltyperror
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1Ltyperror
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LargError
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LargError
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckString
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckString
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LoptString
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1LoptString
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)D
 */
JNIEXPORT jdouble JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckNumber
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LoptNumber
 * Signature: (Lorg/keplerproject/luajava/CPtr;ID)D
 */
JNIEXPORT jdouble JNICALL Java_org_keplerproject_luajava_LuaState__1LoptNumber
  (JNIEnv *, jobject, jobject, jint, jdouble);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckInteger
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LoptInteger
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LoptInteger
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckStack
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckStack
  (JNIEnv *, jobject, jobject, jint, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckType
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckType
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LcheckAny
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LcheckAny
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LnewMetatable
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LnewMetatable
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LgetMetatable
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LgetMetatable
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Lwhere
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1Lwhere
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Lref
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1Lref
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LunRef
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LunRef
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LgetN
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LgetN
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LsetN
 * Signature: (Lorg/keplerproject/luajava/CPtr;II)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1LsetN
  (JNIEnv *, jobject, jobject, jint, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LloadFile
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LloadFile
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LloadBuffer
 * Signature: (Lorg/keplerproject/luajava/CPtr;[BJLjava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LloadBuffer
  (JNIEnv *, jobject, jobject, jbyteArray, jlong, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LloadString
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_org_keplerproject_luajava_LuaState__1LloadString
  (JNIEnv *, jobject, jobject, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _Lgsub
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1Lgsub
  (JNIEnv *, jobject, jobject, jstring, jstring, jstring);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _LfindTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;ILjava/lang/String;I)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_org_keplerproject_luajava_LuaState__1LfindTable
  (JNIEnv *, jobject, jobject, jint, jstring, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openBase
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openBase
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openTable
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openTable
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openIo
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openIo
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openOs
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openOs
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openString
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openString
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openMath
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openMath
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openDebug
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openDebug
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openPackage
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openPackage
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _openLibs
 * Signature: (Lorg/keplerproject/luajava/CPtr;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1openLibs
  (JNIEnv *, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    luajava_open
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState_luajava_1open
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _getObjectFromUserdata
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Ljava/lang/Object;
 */
JNIEXPORT jobject JNICALL Java_org_keplerproject_luajava_LuaState__1getObjectFromUserdata
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isObject
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_keplerproject_luajava_LuaState__1isObject
  (JNIEnv *, jobject, jobject, jint);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushJavaObject
 * Signature: (Lorg/keplerproject/luajava/CPtr;Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushJavaObject
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _pushJavaFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;Lorg/keplerproject/luajava/JavaFunction;)V
 */
JNIEXPORT void JNICALL Java_org_keplerproject_luajava_LuaState__1pushJavaFunction
  (JNIEnv *, jobject, jobject, jobject);

/*
 * Class:     org_keplerproject_luajava_LuaState
 * Method:    _isJavaFunction
 * Signature: (Lorg/keplerproject/luajava/CPtr;I)Z
 */
JNIEXPORT jboolean JNICALL Java_org_keplerproject_luajava_LuaState__1isJavaFunction
  (JNIEnv *, jobject, jobject, jint);

#ifdef __cplusplus
}
#endif
#endif
