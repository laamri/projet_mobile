#include <jni.h>
#include <string>
#include <cstring>
#include <cstdio>
#include <cstdlib>
#include <android/log.h>
#include <sys/ptrace.h>
#include <unistd.h>
#include <errno.h>

#define LOG_TAG "ANTI_DEBUG_LAB"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

// 1. Vérification du TracerPid (Le plus fiable)
static bool isTracerAttached() {
    FILE* status = fopen("/proc/self/status", "r");
    if (!status) return false;
    char line[256];
    bool detected = false;
    while (fgets(line, sizeof(line), status)) {
        if (strncmp(line, "TracerPid:", 10) == 0) {
            int pid = atoi(&line[10]);
            if (pid != 0) {
                LOGE("VRAI DEBUGGER DÉTECTÉ : TracerPid = %d", pid);
                detected = true;
            }
            break;
        }
    }
    fclose(status);
    return detected;
}

// 2. Vérification des outils de Hack (Frida/Xposed)
static bool isHackToolPresent() {
    FILE* maps = fopen("/proc/self/maps", "r");
    if (!maps) return false;
    char line[512];
    bool detected = false;
    while (fgets(line, sizeof(line), maps)) {
        // On ne détecte que les outils malveillants, on ignore "agent.so" d'Android Studio
        if (strstr(line, "frida") || strstr(line, "xposed") || strstr(line, "magisk")) {
            LOGE("OUTIL DE HACK DÉTECTÉ : %s", line);
            detected = true;
            break;
        }
    }
    fclose(maps);
    return detected;
}

// 3. Ptrace (Optionnel sur émulateur car souvent bloqué par SELinux)
static bool isPtraceWorking() {
    errno = 0;
    if (ptrace(PTRACE_TRACEME, 0, 0, 0) == -1) {
        if (errno == EACCES || errno == EPERM) {
            LOGW("Ptrace bloqué par SELinux (Normal sur Android récent)");
            return false;
        }
        return true; // Déjà tracé par un vrai debugger
    }
    return false;
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_lab23_1jni_1et_1protection_1anti_1debug_1native_MainActivity_isDebugDetected(
        JNIEnv* env, jobject /* this */) {

    // Priorité au TracerPid qui est la preuve absolue
    if (isTracerAttached()) return JNI_TRUE;

    // Puis les outils de hack
    if (isHackToolPresent()) return JNI_TRUE;

    // Ptrace en dernier recours
    if (isPtraceWorking()) return JNI_TRUE;

    LOGI("Sécurité : OK (Aucun debugger réel détecté)");
    return JNI_FALSE;
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_lab23_1jni_1et_1protection_1anti_1debug_1native_MainActivity_helloFromJNI(
        JNIEnv* env, jobject) {
    return env->NewStringUTF("Hello from JNI (Protection Active)");
}

extern "C" JNIEXPORT jint JNICALL
Java_com_example_lab23_1jni_1et_1protection_1anti_1debug_1native_MainActivity_factorial(
        JNIEnv* env, jobject, jint n) {
    if (n < 0) return -1;
    int fact = 1;
    for (int i = 1; i <= n; i++) fact *= i;
    return fact;
}
