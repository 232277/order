# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class android.** {
    *;
}

##---------------Begin avi  ----------
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }
##---------------End avi  ----------

##---------------Begin Retrofit  ----------
# Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
# EnclosingMethod is required to use InnerClasses.
-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Ignore annotation used for build tooling.
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement

# Ignore JSR 305 annotations for embedding nullability information.
-dontwarn javax.annotation.**

# Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
-dontwarn kotlin.Unit

# Top-level functions that can only be used by Kotlin.
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
# and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
##---------------End retrofit  ----------

##---------------Begin okhttp  ----------
# JSR 305 annotations are for embedding nullability information.
-dontwarn javax.annotation.**

# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

# OkHttp platform used only on JVM and when Conscrypt dependency is available.
-dontwarn okhttp3.internal.platform.ConscryptPlatform
##---------------End okhttp  ----------

##---------------Begin okio  ----------
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*
##---------------End okio  ----------

##---------------Begin Gson  ----------

# Gson uses generic type information stored in a class file when working with fields. Proguard

# removes such information by default, so configure it to keep all of it.

-keepattributes Signature



# For using GSON @Expose annotation

-keepattributes *Annotation*



# Gson specific classes

-dontwarn sun.misc.**

#-keep class com.google.gson.stream.** { *; }



# Application classes that will be serialized/deserialized over Gson
-keep class com.centerm.jinxiaocun.data.** { <fields>; }



# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,

# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)

-keep class * implements com.google.gson.TypeAdapter

-keep class * implements com.google.gson.TypeAdapterFactory

-keep class * implements com.google.gson.JsonSerializer

-keep class * implements com.google.gson.JsonDeserializer



# Prevent R8 from leaving Data object members always null

-keepclassmembers,allowobfuscation class * {

  @com.google.gson.annotations.SerializedName <fields>;

}
##---------------End Gson  ----------


##---------------Begin glide  ----------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
##---------------End glide  ----------

##---------------Begin 一些警告  ----------
-dontwarn okhttp3.**
-dontwarn org.apache.log4j.**
-dontwarn com.centerm.dev.**
-dontwarn com.centerm.frame.**
-dontwarn com.centerm.mid.**
-dontwarn com.centerm.smartpos.**
-dontwarn rxhttp.**
#---------------End 一些警告  ----------