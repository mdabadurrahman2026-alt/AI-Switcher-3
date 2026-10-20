# My AI Switcher — Android starter project

এটি ভিডিও-অনুপ্রাণিত একটি Android AI Model Switcher-এর প্রাথমিক সোর্স প্রজেক্ট।

## কী আছে
- ডার্ক ড্যাশবোর্ড UI
- মডেল নির্বাচন তালিকা
- ডেমো চ্যাট
- API endpoint ও API key সেটিংসের প্রাথমিক UI
- Android Studio দিয়ে APK build করার উপযোগী Gradle প্রজেক্ট

## APK বানানো
1. কম্পিউটারে Android Studio ইনস্টল করো।
2. এই ZIP extract করে Android Studio-তে `MyAIModelSwitcher` ফোল্ডার খোলো।
3. Gradle sync শেষ হওয়া পর্যন্ত অপেক্ষা করো।
4. `Build > Build APK(s)` নির্বাচন করো।
5. সফল হলে APK সাধারণত `app/build/outputs/apk/debug/app-debug.apk`-এ থাকবে।

## গুরুত্বপূর্ণ
বর্তমান সংস্করণটি UI demo; আসল AI API request এখনো যুক্ত নয়। মডেলের নাম দেখানো মানেই সেই মডেল API-তে উপলভ্য—এমন নয়। API provider অনুযায়ী model ID এবং request format আলাদা হতে পারে।

অন্যদের কাছে APK বিতরণের আগে API key APK-তে অন্তর্ভুক্ত করো না। নিরাপদ backend ব্যবহার করো, যাতে API key সার্ভারে থাকে। API ব্যবহার করলে খরচ হতে পারে এবং provider-এর শর্ত প্রযোজ্য হবে।

## প্রযুক্তি
Kotlin, Jetpack Compose, Android Gradle Plugin.
