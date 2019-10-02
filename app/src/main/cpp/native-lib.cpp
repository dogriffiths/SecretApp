#include <jni.h>
#include <string>

char encode(char c) {
    if ((c >= 'A') && (c <= 'Z')) {
        return 'Z' - c + 'a';
    } else if ((c >= 'a') && (c <= 'z')) {
        return 'z' - c + 'A';
    }
    return c;
}

void encrypt(char* c, unsigned int len) {
    if (len > 0) {
        for (int i = 0; i <= len >> 1; i++) {
            char tmp = c[i];
            c[i] = (encode(c[len - i - 1]));
            c[len - i - 1] = encode(tmp);
        }
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_aspenshore_secretapp_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject jobj,
        jstring jstr) {
    const char *path = env->GetStringUTFChars(jstr , NULL ) ;
    std::string hello = path;
    unsigned int len = hello.length();
    char c[len];
    strcpy(c, path);
    encrypt(c, len);
    return env->NewStringUTF(c);
}

