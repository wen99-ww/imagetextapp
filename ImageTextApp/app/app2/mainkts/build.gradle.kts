//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    alias(libs.plugins.android.application) apply false
//    alias(libs.plugins.kotlin.android) apply false
////    alias(libs.plugins.kotlin.compose) apply false
//}
// 项目根目录的 build.gradle.kts
//plugins {
//    id("com.android.application") version "8.1.2" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
//    // 移除有问题的插件引用
//}

// 项目根目录的 build.gradle.kts
//plugins {
//    id("com.android.application") version "8.1.2" apply false
//    id("org.jetbrains.kotlin.android") version "1.9.10" apply false
//    // 移除有问题的插件引用
//}
////plugins {
////    id("com.android.application") version "8.1.2" apply false
////    id("org.jetbrains.kotlin.android") version "1.9.20" apply false  // 升级到 1.9.20
////}

// 项目根目录的 build.gradle.kts
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.10" apply false  // 使用 1.9.10
}