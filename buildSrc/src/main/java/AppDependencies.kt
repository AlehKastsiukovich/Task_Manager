object Versions {
    const val nav_version = "2.3.5"
    const val junit_version = "4.12"
}

object AppDependencies {
    const val core_ktx = "androidx.core:core-ktx:1.7.0"
    const val app_compat = "androidx.appcompat:appcompat:1.3.1"
    const val material = "com.google.android.material:material:1.4.0"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:2.1.1"
    const val lifecycle_java_8 = "androidx.lifecycle:lifecycle-common-java8:2.3.1"
}

object NavDependencies {
    const val navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigation_ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val navigation_dynamic_features = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"

    const val navigation_test = "androidx.navigation:navigation-testing:${Versions.nav_version}"
}

object TestDependencies {
    const val junit = "junit:junit:${Versions.junit_version}"
    const val junit_ext = "androidx.test.ext:junit:1.1.3"
    const val mockk = "io.mockk:mockk:1.12.0"

    const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
}
