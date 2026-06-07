-obfuscationdictionary proguard-dictionary.txt
-classobfuscationdictionary proguard-dictionary.txt
-packageobfuscationdictionary proguard-dictionary.txt

-overloadaggressively

-repackageclasses 'com.security.obfuscated'
-allowaccessmodification

-flattenpackagehierarchy ''

-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

-keepclassmembers class * {
    *** Companion;
}
-keepclasseswithmembers class * {
    @kotlinx.serialization.Serializable *** writeSelf(...);
}
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *** Companion;
}
-keepclassmembers class * {
    @kotlinx.serialization.Serializable *** $serializer;
}

-keepclassmembers class * {
    @androidx.compose.runtime.Composable *;
    @androidx.compose.runtime.ReadOnlyComposable *;
}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.content.BroadcastReceiver
