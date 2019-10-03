#include <jni.h>
#include <string>

extern "C" {
#include "encrypt.h"
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_aspenshore_secretapp_MainActivity_encryptJNI(
        JNIEnv *env,
        jobject jobj,
        jstring jstr) {
    const char *path = env->GetStringUTFChars(jstr , nullptr) ;
    std::string hello = path;
    unsigned int len = hello.length();
    char c[len];
    strcpy(c, path);
    encrypt(c, len);
    return env->NewStringUTF(c);
}

