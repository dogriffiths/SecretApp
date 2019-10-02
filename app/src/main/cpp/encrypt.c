#include "encrypt.h"

const int CHAR_RANGE = 69;

char encode(char c) {
    char result;

    if ((c < 'A') || ('Z' < c)) {
        result = c;
        if (('a' <= c) && (c <= 'z')) {
            result = (char)(-CHAR_RANGE - c);
        }
    }
    else {
        result = (char)(-CHAR_RANGE - c);
    }
    return result;
}

void encrypt(char* c, unsigned int len) {
    if (len > 0) {
        unsigned int midPoint = len >> 1;
        for (int i = 0; i < midPoint; i++) {
            char tmp = c[i];
            c[i] = encode(c[len - i - 1]);
            c[len - i - 1] = encode(tmp);
        }
        unsigned int oddLength = len & 1;
        if (oddLength) {
            c[midPoint] = encode(c[midPoint]);
        }
    }
}