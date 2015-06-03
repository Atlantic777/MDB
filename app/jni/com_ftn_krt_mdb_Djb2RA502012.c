#include <jni.h>
#include <stdlib.h>
#include "com_ftn_krt_mdb_Djb2RA502012.h"


JNIEXPORT jlong JNICALL Java_com_ftn_krt_mdb_Djb2RA502012_get_1hash
  (JNIEnv *env, jobject obj, jstring s)
{
    long hash = 5381;
    int c;
    int a = 0;

    const char *str = (*env)->GetStringUTFChars(env, s, NULL);
    while(c = *str++)
        hash = ((hash << 5) + hash) + c; /* hash * 33 + c */

    return hash;
}
