#base
-allowaccessmodification
-keepattributes InnerClasses,Signature,EnclosingMethod
-keepattributes SourceFile,LineNumberTable
-renamesourcefileattribute SourceFile
-ignorewarnings
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keep public class * implements java.io.Serializable {*;}

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

# Android x
-keep class com.google.android.material.** {*;}

-keep class androidx.** {*;}

-keep public class * extends androidx.**

-keep interface androidx.** {*;}

-dontwarn com.google.android.material.**

-dontnote com.google.android.material.**

-dontwarn androidx.*

# retrofot gson  okhttp
-dontwarn retrofit2.**

-dontwarn org.codehaus.mojo.**

-keep class retrofit2.** { *; }

-keepattributes Signature

-keepattributes Exceptions

-keepattributes *Annotation*

-keepattributes RuntimeVisibleAnnotations

-keepattributes RuntimeInvisibleAnnotations

-keepattributes RuntimeVisibleParameterAnnotations

-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod

-keepclasseswithmembers class * {

@retrofit2.* <methods>;

}

-keepclasseswithmembers interface * {

@retrofit2.* <methods>;

}

#greendao

### greenDAO 3
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties { *; }

# If you DO use SQLCipher:
-keep class org.greenrobot.greendao.database.SqlCipherEncryptedHelper { *; }

# If you do NOT use SQLCipher:
-dontwarn net.sqlcipher.database.**
# If you do NOT use RxJava:
-dontwarn rx.**

#GSON
-keep public class  com.google.gson.** {*;}
-keep public class  com.seatrend.routinginspection.entity.** {*;}  #自定义

