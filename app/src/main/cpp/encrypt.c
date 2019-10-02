#include "encrypt.h"

char encode(char c) {
    if ((c >= 'A') && (c <= 'Z')) {
        return (char)('Z' - c + 'a');
    } else if ((c >= 'a') && (c <= 'z')) {
        return (char)('z' - c + 'A');
    }
    return c;
}

void encrypt(char* c, unsigned int len) {
    if (len > 0) {
        for (int i = 0; i < len >> 1; i++) {
            char tmp = c[i];
            char c1 = c[len - i - 1];
            c[i] = (encode(c1));
            c[len - i - 1] = encode(tmp);
        }
        if (len & 1) {
            c[len >> 1] = encode(c[len >> 1]);
        }
    }
}