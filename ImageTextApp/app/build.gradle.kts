//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//}
//
//android {
//    namespace = "com.example.imagetextapp"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.example.imagetextapp"
//        minSdk = 21
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        // 关键修复：使用正确的Compose编译器版本
//        kotlinCompilerExtensionVersion = "1.5.3"
//    }
//}
//
//dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
//    implementation(libs.androidx.compose.material3)
//    implementation(libs.androidx.navigation.compose)
//
//    // 网络请求
//    implementation(libs.retrofit)
//    implementation(libs.retrofit.converter.gson)
//
//    // 图片加载
//    implementation(libs.coil.compose)
//
//    // JSON解析
//    implementation(libs.gson)
//
//    // 分页加载
//    implementation(libs.paging.compose)
//    implementation(libs.paging.runtime)
//
//    // 数据存储
//    implementation(libs.datastore.preferences)
//
//    implementation("androidx.navigation:navigation-compose:2.7.5")
//
//    implementation("androidx.paging:paging-compose:3.2.1")
//    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
//    implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")
//
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//}
//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.kotlin.android)
//}
//
//android {
//    namespace = "com.example.imagetextapp"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.example.imagetextapp"
//        minSdk = 24  // 建议升级到24以获得更好的Compose支持
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary = true
//        }
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
//    }
//    kotlinOptions {
//        jvmTarget = "11"
//    }
//    buildFeatures {
//        compose = true
//    }
//    composeOptions {
//        // 关键修复：使用与BOM兼容的编译器版本
//        kotlinCompilerExtensionVersion = "1.5.4"
//    }
//    packaging {
//        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
//        }
//    }
//}
//
//dependencies {
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.activity.compose)
//    implementation(platform(libs.androidx.compose.bom))
//
//    // Compose UI
//    implementation(libs.androidx.compose.ui)
//    implementation(libs.androidx.compose.ui.graphics)
//    implementation(libs.androidx.compose.ui.tooling.preview)
//
//    // Material3 和图标依赖（关键修复）
//    implementation(libs.androidx.compose.material3)
//    implementation("androidx.compose.material:material-icons-extended")  // 添加图标扩展库
//
//    // Navigation
//    implementation(libs.androidx.navigation.compose)
//
//    // 网络请求
//    implementation(libs.retrofit)
//    implementation(libs.retrofit.converter.gson)
//
//    // 图片加载
//    implementation(libs.coil.compose)
//
//    // JSON解析
//    implementation(libs.gson)
//
//    // 分页加载（移除重复声明）
//    implementation(libs.paging.compose)
//    implementation(libs.paging.runtime)
//
//    // 数据存储
//    implementation(libs.datastore.preferences)
//
//    // 下拉刷新
//    implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//    androidTestImplementation(platform(libs.androidx.compose.bom))
//    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
//    debugImplementation(libs.androidx.compose.ui.tooling)
//    debugImplementation(libs.androidx.compose.ui.test.manifest)
//}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.imagetextapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.imagetextapp"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()  // 使用 1.5.3
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Compose UI
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)

    // Material3 和图标依赖（关键修复）
    implementation(libs.androidx.compose.material3)

    implementation("androidx.compose.material:material-icons-extended")  // 添加图标扩展库

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // 网络请求
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // 图片加载
    implementation(libs.coil.compose)

    // JSON解析
    implementation(libs.gson)

    // 分页加载
    implementation(libs.paging.compose)
    implementation(libs.paging.runtime)

    // 数据存储
    implementation(libs.datastore.preferences)

    // 下拉刷新
    implementation("com.google.accompanist:accompanist-swiperefresh:0.32.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}