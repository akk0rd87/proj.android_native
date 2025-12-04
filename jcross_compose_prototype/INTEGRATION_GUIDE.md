# Руководство по интеграции с C++ кодом

Этот документ описывает, как интегрировать прототип Compose UI с существующим C++ кодом JCross.

## Текущая архитектура прототипа

```
Compose UI (Kotlin)
    ↓
MockDataProvider (заглушки)
    ↓
Models (data classes)
```

## Целевая архитектура

```
Compose UI (Kotlin)
    ↓
JNI Interface (Kotlin/Java)
    ↓
Native Bridge (C++)
    ↓
Existing C++ Code (App, Model, UI layers)
```

## Шаги интеграции

### 1. Создание JNI интерфейса

Создайте класс `NativeDataProvider.kt`:

```kotlin
package com.jcross.prototype.data

class NativeDataProvider {
    external fun getGroups(): Array<Group>
    external fun getFolders(groupId: Int): Array<Folder>
    external fun getPuzzles(groupId: Int, folderId: Int): Array<Puzzle>
    external fun getPuzzleField(puzzleId: Int): GameField
    external fun setCellState(puzzleId: Int, row: Int, col: Int, state: CellState)
    external fun checkSolution(puzzleId: Int): Boolean

    companion object {
        init {
            System.loadLibrary("jcross-native")
        }
    }
}
```

### 2. Создание C++ моста

Создайте файл `jni_bridge.cpp`:

```cpp
#include <jni.h>
#include "app/app.h"
#include "model/model.h"

extern "C" {

JNIEXPORT jobjectArray JNICALL
Java_com_jcross_prototype_data_NativeDataProvider_getGroups(
    JNIEnv* env, jobject thiz) {

    // Получаем данные из C++ кода
    auto& app = nsApp::App::getInstance();
    auto groups = app.getGroups();

    // Конвертируем в Java объекты
    jclass groupClass = env->FindClass("com/jcross/prototype/model/Group");
    // ... создание массива и заполнение данными

    return javaGroups;
}

// Аналогичные функции для getFolders, getPuzzles и т.д.

} // extern "C"
```

### 3. Настройка CMakeLists.txt

Добавьте в `build.gradle.kts`:

```kotlin
android {
    // ...
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    defaultConfig {
        // ...
        ndk {
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
    }
}
```

Создайте `src/main/cpp/CMakeLists.txt`:

```cmake
cmake_minimum_required(VERSION 3.22.1)

project("jcross-native")

# Добавьте пути к существующему C++ коду
include_directories(
    ${CMAKE_SOURCE_DIR}/../../../../jcross/src
)

# Добавьте существующие исходники
add_library(jcross-native SHARED
    jni_bridge.cpp
    # Путь к существующим файлам
    ../../../../jcross/src/app/app.cpp
    ../../../../jcross/src/model/model.cpp
    # ... остальные файлы
)

find_library(log-lib log)
target_link_libraries(jcross-native ${log-lib})
```

### 4. Замена MockDataProvider

В файлах экранов замените:

```kotlin
// Было:
val groups = remember { MockDataProvider.getGroups() }

// Станет:
val nativeProvider = remember { NativeDataProvider() }
val groups = remember { nativeProvider.getGroups() }
```

### 5. Маппинг данных

Убедитесь, что Kotlin модели соответствуют C++ структурам:

**C++ (model.h):**
```cpp
namespace nsModel {
    struct Group {
        int id;
        int totalPuzzles;
        int solvedPuzzles;
        // ...
    };
}
```

**Kotlin (Models.kt):**
```kotlin
data class Group(
    val id: Int,
    val totalPuzzles: Int,
    val solvedPuzzles: Int,
    // ...
)
```

## Пример конвертации данных в JNI

```cpp
jobject convertGroupToJava(JNIEnv* env, const nsModel::Group& group) {
    jclass groupClass = env->FindClass("com/jcross/prototype/model/Group");
    jmethodID constructor = env->GetMethodID(groupClass, "<init>", "(ILcom/jcross/prototype/model/PuzzleSize;II)V");

    // Создаем enum для размера
    jclass sizeClass = env->FindClass("com/jcross/prototype/model/PuzzleSize");
    // ... получаем нужное значение enum

    return env->NewObject(groupClass, constructor, group.id, sizeEnum, group.totalPuzzles, group.solvedPuzzles);
}
```

## Управление жизненным циклом

1. **Инициализация** - создайте singleton `NativeDataProvider` в `MainActivity`
2. **Кэширование** - используйте `remember` в Compose для кэширования данных
3. **Очистка** - освобождайте ресурсы в `onDestroy()` активности

## Тестирование

1. Создайте unit-тесты для JNI интерфейса
2. Используйте инструментальные тесты для проверки UI с реальными данными
3. Сравните поведение с существующим C++ UI

## Полезные ссылки

- [Android NDK Documentation](https://developer.android.com/ndk)
- [JNI Tips](https://developer.android.com/training/articles/perf-jni)
- [Jetpack Compose and Native Code](https://developer.android.com/jetpack/compose/interop)
